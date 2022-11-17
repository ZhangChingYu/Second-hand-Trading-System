# 閒置蟲蟲 IDLE BUGS
## 簡介

This is a WeChat mini program, called "Idle Bugs"

The "Idle Bugs" is a Campus Second-hand Trading System that designed for a university

The developers of this system are : 張晴渝, 楊單詞, 謝杭靜, 普文平, 卜凡凡
***
## 後端項目結構
- common token相关
- config 配置文件  

***
## token相关
    token 有效时间为一小时
    @Passtoken 
        跳过token验证
    @UserLoginToken 
        用于登录后才能操作的token:
            要求前端请求Headers中带有token    
            后端在需验证token的api前加@UserLoginToken
### LoginServiceImpl.xml
``` Java
    @Override
    public Optional<LoginResponseDto> login(LoginRequestDto request) {
        return this.verifyAccount(request)
                .map(loginDto -> {
                    LoginResponseDto user = this.modelMapper.map(loginDto, LoginResponseDto.class);
                    User u=user.getUser();
                    if(Objects.equals(user.getCode(), "666")){
                        user.setToken(Sign.generateToken(
                                u.getId(),
                                u.getUserName(),
                                u.getAuthority(),
                                1000 * 60 * 60
                                //token有效时间
                        ));
                    }
                    return user;
                });
    }
```
## 登录功能
    url: POST http://localhost:8080/login
    requestbody: LoginRequestDto
    {
        "password": "11",   //密码
        "usename": "1502391218@qq.com"   //用户名
    }
    responsebody:
    {
        "code": "666",     
        "msg": "登陆成功",
        "user": { },
        "token": 
    }
    code:
        222: 用户被禁用;   444: 密码错误;
        555: 用户不存在;    666: 登陆成功;
## 忘记密码
    获取验证码：
        url: GET http://localhost:8080/captcha?phone=15083729338
        requestparam:  String phone
        responsebody:  
            {
                "code": "438647",  
                "msg": "验证码发送成功" 
            }
        code: 
            code        msg
            555    手机号或邮箱格式错误
            444       用户不存在
            验证码    验证码发送成功
    重置密码：
        url: POST http://localhost:8080/lost
        requestbody:  LostPasswordDto
        {
            "captcha": "438647",   //验证码
            "password": "211",
            "phone": "15083729338"  //手机号
        }
        responsebody:
        {
            "code": "666",
            "msg": "修改密码成功"
        }
        code:
            000： 用户不存在；   111：手机号或邮箱格式错误；
            222：密码为旧密码；   444: 修改密码失败;
            555: 验证码错误;    666: 修改密码成功; 
### pom.xml
``` Java
        <!-- ava-jwt -->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.6.0</version>
        </dependency>

        <!-- jpa-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>org.modelmapper</groupId>
            <artifactId>modelmapper</artifactId>
            <version>2.3.3</version>
        </dependency>

        <dependency>
            <groupId>org.bouncycastle</groupId>
            <artifactId>bcprov-jdk15on</artifactId>
            <version>1.60</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>

        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```