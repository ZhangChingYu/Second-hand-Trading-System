package dev.silvia.wechattrade.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
//带着 encryptedData, iv, sessionId 去获得 token
//encrytedData：包括敏感数据在内的完整用户信息的加密数据（即可以通过反解密，获取出用户数据）
// iv：加密算法的初始向量
public class WXAuth {
    private String encryptedData;
    private String iv;
    private String sessionId;
}
