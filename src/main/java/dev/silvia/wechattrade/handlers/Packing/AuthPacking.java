package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.AuthenticationRequest;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.vo.request.auth.AuthRequestDetailVo;
import dev.silvia.wechattrade.vo.request.auth.AuthRequestOutlineVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
@Component
public class AuthPacking {  // 將AuthenticationRequest包裝的工具
    private TransferUTF8 transferUTF8 = new TransferUTF8();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public AuthRequestOutlineVo AuthRequestToOutline(AuthenticationRequest request){
        AuthRequestOutlineVo outlineVo = new AuthRequestOutlineVo();
        outlineVo.setId(request.getId());
        outlineVo.setPhone(request.getPhone());
        outlineVo.setRealName(transferUTF8.UTF8toC(request.getRealName()));
        outlineVo.setDate(sdf.format(request.getDate()));
        outlineVo.setStatus(request.getStatus());
        return outlineVo;
    }

    public AuthRequestDetailVo AuthRequestToDetail(AuthenticationRequest request, String format, String picture){
        AuthRequestDetailVo detailVo = new AuthRequestDetailVo();
        detailVo.setId(request.getId());
        detailVo.setPhone(request.getPhone());
        detailVo.setDate(sdf.format(request.getDate()));
        detailVo.setRealName(transferUTF8.UTF8toC(request.getRealName()));
        detailVo.setFormat(format);
        detailVo.setIdCardPic(picture);
        return detailVo;
    }
}
