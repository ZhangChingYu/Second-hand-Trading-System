# 閒置蟲蟲 IDLE BUGS
## 簡介

This is a WeChat mini program, called "Idle Bugs"

The "Idle Bugs" is a Campus Second-hand Trading System that designed for a university

The developers of this system are : 張晴渝, 楊單詞, 謝杭靜, 普文平, 卜凡凡

***
## 後端項目結構
- entity 實體層 
   > User.java
     
   > Product.java

   > ProductCatalog.java

   > ......
- dao 實體層映射
   > UserDao.java

   > ProductDao.java

   > ProductCatalogDao.java
     
   > ......
- dto 數據傳輸處理中介 service -> 前端 
   > UserDto.java
  
   > ......
- service 服務層抽象 
   > IRegisterService.java

   > IProductCatalogService.java

   > ......
   - Impl 服務層實現
      > RegisterServiceImpl.java
        
      > ProductCatalogServiceImpl.java
   
      > ......
- controller 控制層
   > RegisterController.java

   > ProductCatalogController.java
     
   > ......
- vo 數據傳輸處理中介 前端 -> service
   > UserVo.java
     
   > ......
- handlers 工具包(一些常調用的功能類)
   > TransferUTF8.java
  
   > ......
***
## 註冊功能
由用戶輸入手機號、郵箱及密碼來完成註冊，且一個手機號只能註冊一次，若手機號被重複註冊則會判定註冊失敗。
手機號須為11位手機號，其他型號本系統不予支持。註冊功能後端暫定api為**POST**  /user，所有前後端交互數據皆由UTF-8進行編碼與解碼。
為避免中英文混和而產生亂碼，數據庫除日期、純數字、純英文數據外，接先進行UTF-8編碼後存入數據庫，讀取時再進行相應的解碼顯示。
### RegisterController.java
``` Java
    @RequestMapping(value = "/user", method = RequestMethod.POST)
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
    }eturn service.Register(user);
```

### RegisterServiceImpl.java
``` Java
    @Override
    public int Register(User user) {
        String phone_num = user.getPhone();
        // make sure that the phone number haven't been registered before.
        String check = "select count(*) from user_info where phone = '" + phone_num+ "'";
        int checked = jdbcTemplate.queryForObject(check, Integer.class);
        if(checked == 1){
            return 422;     // 該手機號已被註冊，不可重複註冊
        }
        else {
            user.setAuthority(1);
            user.setRegisterDate(new Date());
            userDao.insert(user);
            int success = jdbcTemplate.queryForObject(check, Integer.class);
            if(success == 1){
                return 201; // 註冊成功
            }
            return 404;     // 註冊失敗，信息未成功添加
        }
    }
```
## 商品分類功能
目前本系統的商品分類為如下，同樣已UTF-8編碼後存儲含中文內容的數據。其中，以各項分類的英文名稱首字母大寫作為商品的分類編號。
商品分類功能主要包含商品份類的增、刪、改、查。
1. 書籍 Book
2. 美妝 Makeup
3. 數碼 Digital
4. 家居 Furniture
5. 電器 Electronic
6. 服裝 Clothes
7. 其他 Others
***
# 關於RestFul api的一些規範
什么是RestFul架构：
1. 每一个URI代表一种资源；
2. 客户端和服务器之间，传递这种资源的某种表现层；
3. 客户端通过HTTP动词(GET,POST等等)，对服务器端资源进行操作，实现"表现层状态转化"。
下面為符合RestFul的例子:
``` Text
GET         /zoos：列出所有动物园
POST        /zoos：新建一个动物园
GET         /zoos/ID：获取某个指定动物园的信息
PUT         /zoos/ID：更新某个指定动物园的信息（提供该动物园的全部信息）
PATCH       /zoos/ID：更新某个指定动物园的信息（提供该动物园的部分信息）
DELETE      /zoos/ID：删除某个动物园
GET         /zoos/ID/animals：列出某个指定动物园的所有动物
DELETE      /zoos/ID/animals/ID：删除某个指定动物园的指定动物复制代码
```
還有對狀態碼一些共識:
``` Text
200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）。
201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
204 NO CONTENT - [DELETE]：用户删除数据成功。
400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
502 网关错误
503 Service Unavailable
504 网关超时
```