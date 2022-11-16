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
## 登录功能
/login
输入LoginrequestDto(phone,password,authority);
返回值为LoginResponseDto(id,username,authority,msg（登陆状态：666成功；555：用户不存在；444：
密码权限错误；333权限错误）,token);
## token相关
@Passtoken跳过token验证
@UserLoginToken用于登录后才能操作的token
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
### LoginController.java
``` Java
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        LoginResponseDto user1=new LoginResponseDto();
        Optional<LoginResponseDto> user = this.service.login(request.getPhone(), request.getPassword(),request.getAuthority());
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        else{
            user1.setMsg("555");//用户不存在
            return ResponseEntity.ok(user1);
        }
    }
```

### LoginServiceImpl.java
``` Java
     @Override
    public Optional<LoginResponseDto> login(String phone, String password, Integer authority) {
        return this.verifyAccount(phone, password,authority)
                .map(loginDto -> {
                    LoginResponseDto user = this.modelMapper.map(loginDto, LoginResponseDto.class);
                    user.setToken(Sign.generateToken(
                            user.getId(),
                            user.getUserName(),
                            user.getAuthority(),
                            1000 * 60 * 60 * 24
                    ));
                    return user;
                });
    }

    private Optional<LoginDto> verifyAccount(String phone, String password,Integer authority) {
        return this.accountRepository.findByPhone(phone)
                .filter(account -> {
                    try {
                         //System.out.println(Objects.equals(account.getPassword_hash(), Hash.encode(SALT, password)));
                        return true;
                        //return Objects.equals(account.getPassword_hash(), Hash.encode(SALT, password));
                    } catch (Exception e) {
                        return false;
                    }
                }).map(account ->{
                    LoginDto user=new LoginDto();
                    user.setAuthority(account.getAuthority());
                    user.setUserName(account.getUserName());
                    user.setId(account.getId());
                    if(!Objects.equals(account.getAuthority(), authority)){
                        if(!Objects.equals(account.getPassword(), password)){
                            user.setMsg("444");//密码错误和权限错误
                        }
                        else{
                            user.setMsg("333");//权限错误
                        }
                    }
                    else {
                        user.setMsg("666");//登陆成功
                    }
                    return user;
                } );
    }
```
## 忘记密码
模拟短信验证：
先获取验证码 /captcha（每隔60秒获取一次：前端实现）   通过url查看验证码 http://localhost:8080/captcha
之后修改密码 /lost  返回值（验证码错误返回3；密码为旧密码,返回2；修改密码成功返回1；电话号码不存在,返回0）
### LoginController.java
``` Java
    @RequestMapping(value ="/lost",method = RequestMethod.GET)
    public int lostpassword(@RequestParam String phone,@RequestParam String password,@RequestParam String password1){
        if(Objects.equals(captcha, password1)){
            return service.lostPassward(phone,password);
        }
        else{
            return 3;//验证码错误
        }
    }
    
    //获取验证码
    @RequestMapping(value ="/captcha",method = RequestMethod.GET)
    public String captcha(){
        captcha=String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
        return captcha;
    }
```

### LoginServiceImpl.java
``` Java
     @Override
    public int lostPassward(String phone, String password) {
        try{
            int succeed=2;
            String sql="select * from user_info where phone = '" + phone+ "'";
            String pa= Objects.requireNonNull(jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class))).getPassword();
            if(Objects.equals(pa, password)){
                return succeed;//密码为旧密码,返回2
            }
            else{
                String sql1="update user_info set password='"+ password +"' " +
                        "where phone = '" + phone+ "'";
                return jdbcTemplate.update(sql1);//修改密码成功返回1
            }
        }catch (Exception e){

        }
        return 0;//电话号码不存在,返回0
    }
```
