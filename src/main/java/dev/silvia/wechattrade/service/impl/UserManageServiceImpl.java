package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.AuthenticationRequestDao;
import dev.silvia.wechattrade.dao.NotificationDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.entity.AuthenticationRequest;
import dev.silvia.wechattrade.entity.Notification;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.AuthPacking;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.DeleteFile;
import dev.silvia.wechattrade.handlers.fileHandler.MoveFile;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.IUserManageService;
import dev.silvia.wechattrade.vo.request.auth.AuthRequestDetailVo;
import dev.silvia.wechattrade.vo.request.auth.AuthRequestOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserManageServiceImpl extends ServiceImpl<UserDao, User> implements IUserManageService {
    @Autowired
    private AuthenticationRequestDao authRequestDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationDao noteDao;
    @Autowired
    private ReadFile readFile;
    @Autowired
    private MoveFile moveFile;
    @Autowired
    private DeleteFile deleteFile;
    @Autowired
    private AuthPacking authPacking;
    @Autowired
    private TransferUTF8 transferUTF8;

    @Override
    public List<AuthRequestOutlineVo> showAllAuthRequest() {
        QueryWrapper<AuthenticationRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        List<AuthenticationRequest> requests = authRequestDao.selectList(wrapper);
        List<AuthRequestOutlineVo> outlineVos = new ArrayList<>();
        for(AuthenticationRequest request: requests){
            AuthRequestOutlineVo outlineVo = authPacking.AuthRequestToOutline(request);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public List<AuthRequestOutlineVo> showAuthRequestByStatus(Integer status) {
        QueryWrapper<AuthenticationRequest> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        List<AuthenticationRequest> requests = authRequestDao.selectList(wrapper);
        List<AuthRequestOutlineVo> outlineVos = new ArrayList<>();
        for(AuthenticationRequest request: requests){
            AuthRequestOutlineVo outlineVo = authPacking.AuthRequestToOutline(request);
            outlineVos.add(outlineVo);
        }
        return outlineVos;
    }

    @Override
    public AuthRequestDetailVo readAuthRequest(Integer id) {
        AuthenticationRequest request = authRequestDao.selectById(id);
        AuthRequestDetailVo detailVo;
        Map<String, Object> map;
        String format, picture;
        if(request.getStatus() == 0){   // 沒處理
            map = readFile.getAuthTempPic(request.getPhone());
            format = map.get("format").toString();
            picture = map.get("picture").toString();
        } else if (request.getStatus() == 1) {  // pass
            map = readFile.readAuthPicture(request.getPhone());
            format = map.get("format").toString();
            picture = map.get("picture").toString();
        } else {
            return null;
        }
        detailVo = authPacking.AuthRequestToDetail(request, format, picture);
        return detailVo;
    }

    @Override
    public Integer processAuthRequest(Integer id, String decision, String explain) {
        AuthenticationRequest request = authRequestDao.selectById(id);
        User user = getUser(request.getPhone());
        switch (decision){  // 處理完後須將AuthTemp中對應的圖片刪除/移動
            case "pass":
                request.setStatus(1);   // [pass]
                user.setAuthority(0);   // 更新用戶權限
                user.setRealName(transferUTF8.CtoUTF8(request.getRealName()));  // 更新用戶真實姓名
                if(userDao.updateById(user) <= 0){
                    return 422; // 用戶數據更新失敗
                }
                // 將用戶證件照存到用戶專屬文件夾中
                if(moveFile.moveAuthTempToUser(request.getPhone()) != 200){
                    return 400; // 圖片移動失敗
                }
                if(sendNotification(request.getPhone(), decision, explain) == 200){
                    System.out.println("notification send success.");
                }
                break;
            case "reject":  // 不用更新用戶權限
                request.setStatus(2);   // [reject]
                // 將用戶證件照刪除，避免重複
                if(!deleteFile.deleteAuthTempPicture(request.getPhone())){
                    return 400; // 圖片刪除失敗
                }
                if(sendNotification(request.getPhone(), decision, explain) == 200){
                    System.out.println("notification send success.");
                }
                break;
            default: return 404; // 請求格式錯誤
        }
        if(authRequestDao.updateById(request) > 0){
            return 201; // 處理成功
        }
        return 422; // 處理失敗(數據庫未更新)
    }

    @Override
    public Integer sendNotification(String target, String decision, String explain) {
        Notification note = new Notification();
        note.setTarget(target);
        note.setDate(new Date());
        note.setStatus(1);  // 未讀
        note.setType(1);    // [common]
        note.setSource(transferUTF8.CtoUTF8("系统管理员"));
        if("pass".equals(decision)){
            note.setTitle(transferUTF8.CtoUTF8("恭喜您已通過實名認證!"));
            note.setContent(transferUTF8.CtoUTF8(explain+""));
        }
        if("reject".equals(decision)){
            note.setTitle(transferUTF8.CtoUTF8("很遺憾您並未通過實名認證"));
            note.setContent(transferUTF8.CtoUTF8(explain+""));
        }
        if(noteDao.insert(note) > 0){
            return 200; // 通知發送成功
        }
        return 800;
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }
}
