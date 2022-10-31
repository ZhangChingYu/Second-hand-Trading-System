# 閒置蟲蟲 IDLE BUGS
## 簡介

This is a WeChat mini program, called "Idle Bugs"

The "Idle Bugs" is a Campus Second-hand Trading System that designed for a university

The developers of this system are : 張晴渝, 楊單詞, 謝杭靜, 普文平, 卜凡凡
***
## 後端項目結構
1. entity 實體層 
> User.java
> ...
2. dao 實體層映射
> UserDao.java
> ...
3. dto 數據傳輸處理中介 service -> 前端 
> UserDto.java
> ...
4. service 服務層抽象 
> IRegisterService.java
> ...
   1. Impl 服務層實現
> RegisterServiceImpl.java
> ... 
5. controller 控制層
> RegisterController.java
> ...
6. vo 數據傳輸處理中介 前端 -> service
> UserVo.java
> ...
7. handlers 工具包(一些常調用的功能類)
> TransferUTF8.java
> ...
***
## 註冊功能
由用戶輸入手機號、郵箱及密碼來完成註冊，且一個手機號只能註冊一次，若手機號被重複註冊則會判定註冊失敗。
手機號須為11位手機號，其他型號本系統不予支持。註冊功能後端暫定api為/register，所有前後端交互數據皆由UTF-8進行編碼與解碼。
為避免中英文混和而產生亂碼，數據庫除日期、純數字、純英文數據外，接先進行UTF-8編碼後存入數據庫，讀取時再進行相應的解碼顯示。
### RegisterController.java
``` 
    @RequestMapping(value = "/register")
    public Integer register(@RequestBody Map<String, Object> param){
        // 用戶輸入手機號&密碼即可完成註冊，email可選
        String phone = param.get("phone").toString();
        String password = param.get("password").toString();
        String email = param.get("email").toString();
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setEmail(email);
        return service.Register(user);
    }
```

### RegisterServiceImpl.java
``` 
    @Override
    public int Register(User user) {
        String phone_num = user.getPhone();
        // make sure that the phone number haven't been registered before.
        String check = "select count(*) from user_info where phone = '" + phone_num+ "'";
        int checked = jdbcTemplate.queryForObject(check, Integer.class);
        if(checked == 1){
            return 806;    // 如果手機號已註冊，則判定為註冊失敗，約定狀態碼為806
        }
        else {
            user.setAuthority(1);
            user.setRegisterDate(new Date());
            userDao.insert(user);
            int success = jdbcTemplate.queryForObject(check, Integer.class);
            if(success == 1){
                return 800;   // 註冊成功約定狀態碼為800
            }
            return 808;    // 由數據庫引起的註冊失敗判，約定狀態碼為808
        }
    }
```