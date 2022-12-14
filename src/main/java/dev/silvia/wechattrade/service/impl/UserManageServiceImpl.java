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
        if(request.getStatus() == 0){   // ?????????
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
        switch (decision){  // ??????????????????AuthTemp????????????????????????/??????
            case "pass":
                request.setStatus(1);   // [pass]
                user.setAuthority(0);   // ??????????????????
                user.setRealName(transferUTF8.CtoUTF8(request.getRealName()));  // ????????????????????????
                if(userDao.updateById(user) <= 0){
                    return 422; // ????????????????????????
                }
                // ????????????????????????????????????????????????
                if(moveFile.moveAuthTempToUser(request.getPhone()) != 200){
                    return 400; // ??????????????????
                }
                if(sendNotification(request.getPhone(), decision, explain) == 200){
                    System.out.println("notification send success.");
                }
                break;
            case "reject":  // ????????????????????????
                request.setStatus(2);   // [reject]
                // ???????????????????????????????????????
                if(!deleteFile.deleteAuthTempPicture(request.getPhone())){
                    return 400; // ??????????????????
                }
                if(sendNotification(request.getPhone(), decision, explain) == 200){
                    System.out.println("notification send success.");
                }
                break;
            default: return 404; // ??????????????????
        }
        if(authRequestDao.updateById(request) > 0){
            return 201; // ????????????
        }
        return 422; // ????????????(??????????????????)
    }

    @Override
    public Integer sendNotification(String target, String decision, String explain) {
        Notification note = new Notification();
        note.setTarget(target);
        note.setDate(new Date());
        note.setStatus(1);  // ??????
        note.setType(1);    // [common]
        note.setSource(transferUTF8.CtoUTF8("???????????????"));
        if("pass".equals(decision)){
            note.setTitle(transferUTF8.CtoUTF8("??????????????????????????????!"));
            note.setContent(transferUTF8.CtoUTF8(explain+""));
        }
        if("reject".equals(decision)){
            note.setTitle(transferUTF8.CtoUTF8("????????????????????????????????????"));
            note.setContent(transferUTF8.CtoUTF8(explain+""));
        }
        if(noteDao.insert(note) > 0){
            return 200; // ??????????????????
        }
        return 800;
    }

    private User getUser(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        return userDao.selectOne(wrapper);
    }
}
