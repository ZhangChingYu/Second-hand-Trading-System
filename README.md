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

   > FavoriteInfo.java        //商品收藏表

   > ......
- dao 實體層映射
   > UserDao.java

   > ProductDao.java

   > ProductCatalogDao.java   

   > FavoriteInfoDao.java
     
   > ......
- dto 數據傳輸處理中介 service -> 前端 
   > UserDto.java
   
   > ProductDetailDto.java

   > ProductOutlineDto.java
  
   > ......
- service 服務層抽象 
   > IRegisterService.java

   > ICatalogService.java

   > IProductService.java    //商品檢索等相關功能實現
   
   > ILikeService.java       //商品收藏等相關功能實現 

   > ......
   - Impl 服務層實現
      > RegisterServiceImpl.java
        
      > CatalogServiceImpl.java
         
      > ProductServiceImpl.java
      
      > LikeServiceImpl.java
      
      > ......
- controller 控制層
   > RegisterController.java

   > CatalogController.java

   > ProductSearchController.java
  
   > LikeController.java
     
   > ......
- vo 數據傳輸處理中介 前端 -> service
   > ProductVo.java

   > DeleteBatchVo.java     

   > ......
- handlers 工具包(一些常調用的功能類)
   >   TransferUTF8.java

   >   ReadFile.java

   >   ProductPacking.java  

   >   ......
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
## 註冊功能
由用戶輸入手機號、郵箱及密碼來完成註冊，且一個手機號只能註冊一次，若手機號被重複註冊則會判定註冊失敗。
手機號須為11位手機號，其他型號本系統不予支持。所有前後端交互數據皆由UTF-8進行編碼與解碼。
為避免中英文混和而產生亂碼，數據庫除日期、純數字、純英文數據外，接先進行UTF-8編碼後存入數據庫，讀取時再進行相應的解碼顯示。
* 後端api: **POST**  /user
* json語句: {"phone":"手機號", "password":"密碼", "email":"郵箱"}
* 返回信息(int): 201(用戶創建成功), 422(手機號已存在), 404(註冊失敗，信息未成功添加)
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
## 商品分類功能
目前本系統的商品分類為如下，同樣已UTF-8編碼後存儲含中文內容的數據。其中，以各項分類的英文名稱首字母大寫作為商品的分類編號。
商品分類功能主要包含商品份類的增、刪、改、查。目前分類如下:

 | id | number | name |
 | -- | ------ | ---- |
 | 1 | B | 書籍 |
 | 2 | M | 美妝 |
 | 3 | D | 數碼 |
 | 4 | F | 家居 |
 | 5 | E | 電器 |
 | 6 | C | 服裝 |
 | 7 | O | 其他 |

1. 顯示所有分類:
* 後端api: **GET**  /catalog/catalogs
* 返回信息(Object): {"id":int, "number":"分類編碼", "name":"分類名稱"},{},...,{}
2. 添加分類
* 後端api: **POST**  /catalog
* json語句: {"number":"分類編碼", "name":"分類名稱"}
* 返回信息(int): 201(分類創建成功), 422(分類編碼已存在), 404(添加失敗，信息未成功添加)
3. 更新分類信息
* 後端api: **PUT**  /catalog
* json語句: {"id":int, "number":"分類編碼", "name":"分類名稱"}
* 返回信息(int): 800(更新成功), 808(更新失敗)
4. 刪除分類
* 後端api: **DELETE**  /catalog
* json語句: {"id":int}
* 返回信息(int): 204(分類刪除成功), 400(沒有選中的對象，數據庫為發生改動)
### ProductCatalogController.java
``` Java
// 顯示所有分類，一個分類的結構為{"id":int, "number":"分類編碼", "name":"分類名稱"}
    @RequestMapping(value = "/catalogs", method = RequestMethod.GET)
    public String getAll(){
        return gson.toJson(service.showAllCatalog());
    }
    // 添加分類，請求報文body的json格式為{"name":"分類名稱", "number":"分類編碼"}
    @RequestMapping(value = "/catalog", method = RequestMethod.POST)
    public Integer addCatalog(@RequestBody Map<String, Object> param){
        String name = param.get("name").toString();
        String number = param.get("number").toString();
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setName(name);
        productCatalog.setNumber(number);
        return service.addProductCatalog(productCatalog);
    }
    // 更新分類，請求報文body的json格式為{"id":int, "name":"分類名稱", "number":"分類編碼"}
    @RequestMapping(value = "/catalog", method = RequestMethod.PUT)
    public Integer updateCatalog(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        String name = param.get("name").toString();
        String number = param.get("number").toString();
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setId(id);
        productCatalog.setName(name);
        productCatalog.setNumber(number);
        return service.updateProductCatalog(productCatalog);
    }
    // 刪除分類，請求報文body的json格式為{"id":int}
    @RequestMapping(value = "/catalog", method = RequestMethod.DELETE)
    public Integer deleteCatalog(@RequestBody Map<String, Object> param){
        Integer id = Integer.parseInt(param.get("id").toString());
        return service.deleteProductCatalog(id);
    }
```
## 商品查詢及詳細信息顯示功能
本系統主頁面的商品推送算法尚在開發中，為測試其他功能是否能正常運行，作為主頁面的商品顯示。
商品查詢共分為分類查詢和搜索欄輸入兩種，分類檢索由用戶點選商品分類後，前端返回商品編碼(B,M,E...)給後端接收。
搜索欄檢索則是由用戶在首頁搜索欄中輸入查詢信息，商品詳細信息的顯示是由用戶點選某一商品後，由前端返回商品的編碼(number)給後端。
1. 主頁面商品顯示:
* 後端api: **GET**  /homepage
* json語句: {"id":int}
* 返回信息(Object): {"number":"商品編碼","coverPic":"商品封面照片","name":"商品名稱","price":"商品價格"},...,{}
2. 分類檢索商品:
* 後端api: **GET**  /catalog/products
* json語句: {"catalog":"商品編碼"}
* 返回信息(Object): {"number":"商品編碼","coverPic":"商品封面照片","name":"商品名稱","price":"商品價格"},...,{}
3. 搜索欄檢索:
* 後端api: **GET**  /search/products
* json語句: {"keyword":"輸入內容"}
* 返回信息(Object): {"number":"商品編碼","coverPic":"商品封面照片","name":"商品名稱","price":"商品價格"},...,{}
4. 商品詳細信息顯示:
* 後端api: **GET**  /product/detail，
* json語句: {"number":"商品編碼"}
* 返回信息(Object): {"name":"商品名稱","seller_name":"賣家名稱","address":"地址" ,"date":"發布時間(yyyy-MM-dd,hh:mm:ss)","price":"價格","intro":"商品介紹","like_count":"收藏數","picture_count":"照片數", "pictures","照片的base64編碼list"}
#### 注意!!! 所有後端傳給前端的圖片都是base64的編碼，前端再渲染時請加上標示，例如:
``` html
    <!-- 這裡是顯示商品封面的範例 -->
    <img width="400" height="200" src="data:image/jpg;base64,{{response.coverPic}}" />
```
### ProductSearchController.java
``` Java
    // 通過商品編號查詢商品詳細信息
    @RequestMapping(value = "/product/detail", method = RequestMethod.GET)
    public String getProductDetail(@RequestBody Map<String, Object> param){
        String number = param.get("number").toString();
        return gson.toJson(service.getProductDetail(number));
    }
    // 分類查找
    @RequestMapping(value = "/catalog/products", method = RequestMethod.GET)
    public String getProductByCatalog(@RequestBody Map<String, Object> param){
        String catalog = param.get("catalog").toString();
        return gson.toJson(service.getProductByCatalog(catalog));
    }
    // 關鍵字查詢商品
    @RequestMapping(value = "/search/products", method = RequestMethod.GET)
    public String getProductByKeyword(@RequestBody Map<String, Object> param){
        String keyword = param.get("keyword").toString();
        return gson.toJson(service.searchProductByKey(keyword));
    }
    // 首頁商品推送(暫時)
    @RequestMapping(value = "/homepage", method = RequestMethod.GET)
    public String homepageProduct(){
        return gson.toJson(service.homepageProductPromote());
    }
```
## 商品收藏功能
商品收藏功能需要用戶進行了實名認證後方可使用，本模塊主要包含的功能如下，
檢查商品是否已收藏的功能可用於前端界面顯示愛心顏色的判斷標準。
添加/取消收藏是當用戶點下收藏鍵時，通過商品收藏狀態自動判斷該進行收藏/取消收藏的動作。
顯示所有收藏商品功能會將該用戶所有已收藏商品，以時間最新的順序顯示。

若須以其他順序顯示，按序顯示收藏功能可提供按時間先後和價格高低的順序顯示商品:
* 0(添加日期:新-->舊), 1(添加日期:舊-->新), 2(商品價格:低-->高), 3(商品價格:高-->低)

此外，用戶也可根據商品分類篩選收藏商品，且提供批量取消收藏的功能。
1. 確認該商品是否已收藏:
* 後端api: **GET**  /like
* json語句: {"phone":"用戶手機號", "number":"商品編碼"}
* 返回信息(Boolean): True/False
2. 添加/取消收藏:
* 後端api: **POST**  /like
* json語句: {"phone":"用戶手機號", "number":"商品編碼"}
* 返回信息(int): 201(收藏成功), 204(取消收藏成功), 403(用戶無權限), 404(收藏失敗)
3. 顯示所有收藏商品(默認排序:收藏日期新到舊):
* 後端api: **GET**  /all/likes
* json語句: {"phone":"用戶手機號"}
* 返回信息(Object): {"number":"商品編碼","coverPic":"商品封面照片","name":"商品名稱","price":"商品價格"},...,{}
4. 照切換不同的排序方式:
* 後端api: **GET**  /all/likes/order，
* json語句: {"phone":"用戶手機號", "type":int}
* 返回信息(Object): {"number":"商品編碼","coverPic":"商品封面照片","name":"商品名稱","price":"商品價格"},...,{}
5. 依照商品分類顯示收藏商品:
* 後端api: **GET**  /catalog/likes，
* json語句: {"phone":"用戶手機號", "catalog":"分類編碼"}
* 返回信息(Object): {"number":"商品編碼","coverPic":"商品封面照片","name":"商品名稱","price":"商品價格"},...,{}
6. 批量取消收藏:
* 後端api: **DELETE**  /likes，
* json語句: {"phone":"用戶手機號", "numbers": ["商品編碼1", "商品編碼2",...,"商品編碼N"]}
* 返回信息(int): 204(取消收藏成功), 404(取消收藏失敗)

### LikeController.java
``` Java
    // 查看該商品是否已被收藏，True(以收藏)/False(未收藏)
    @RequestMapping(value = "/like", method = RequestMethod.GET)
    public boolean checkLikeHistory(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        String number = param.get("number").toString();
        return service.checkLike(phone, number);
    }
    // 添加/取消收藏
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public int PressLikeButton(@RequestBody Map<String, Object> param){
        String number = param.get("number").toString();
        String phone = param.get("phone").toString();
        return service.PressLikeButton(phone, number);
    }
    // 顯示所有收藏商品(默認排序:收藏日期新到舊)
    @RequestMapping(value = "/all/likes", method = RequestMethod.GET)
    public String showAllLikes(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        return gson.toJson(service.showAllLike(phone));
    }
    // 照切換不同的排序方式
    @RequestMapping(value = "/all/likes/order", method = RequestMethod.GET)
    public String showLikeByOrder(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        Integer type = Integer.parseInt(param.get("type").toString());
        return gson.toJson(service.showLikeByOrder(phone, type));
    }
    // 依照商品分類顯示收藏商品
    @RequestMapping(value = "/catalog/likes", method = RequestMethod.GET)
    public String showLikeByCatalog(@RequestBody Map<String, Object> param){
        String phone = param.get("phone").toString();
        String catalog = param.get("catalog").toString();
        return gson.toJson(service.showLikeByCatalog(phone, catalog));
    }
    // 批量取消收藏
    @RequestMapping(value = "/likes", method = RequestMethod.DELETE)
    public int deleteLikeBatches(@RequestBody DeleteBatchVo deleteBatchVo){
        String phone = deleteBatchVo.getPhone();
        List<String> numbers = deleteBatchVo.getNumbers();
        return service.deleteLikeBatches(phone, numbers);
    }
```
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