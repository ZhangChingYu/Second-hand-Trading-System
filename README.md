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
## 头像
    用户未设置头像时使用路径：C:/Users/Sunny/Desktop/Avatar/default/default.jpg
## 微信登录相关接口
    //输入code获取 sessionId
    @RequestMapping(value ="/weixin/sessionId",method = RequestMethod.GET)
    public String getSessionId(@RequestParam String code){
       // String code = param.get("code").toString();
    //        System.out.println("ssucceed");
    //        System.out.println(code);
    return  weixinService.getSessionId(code);
    }

    //   输入  {String encryptedData;String iv;String sessionId;}
    //   返回LoginResponseDto{code: "666" （成功）;mag:  ;User:user}
    @RequestMapping(value ="/weixin/authLogin", method = RequestMethod.POST)
    public LoginResponseDto authLogin(@RequestBody WXAuth wxAuth) {
    LoginResponseDto result = weixinService.authLogin(wxAuth);
    return result;
    }
##订单、预约功能
一，查找相关
1. 根据手机号buyer查找所有订单  GET /orders/select/buyer  输入 phone  status（整型）:
* 前端api: **GET**  /orders/select/buyer
* json语句: {"phone":"手机号", "status":"状态（全部/已发货/未发货/..."}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<ExchangeDto>}
* ExchangeDto {
  String proNumber;   //商品编号

  String ordNumber;   //订单编号
  String count;     //商品数量
  String name;     //商品名称

  String  coverPic;   //商品图片

  Double price;     //商品价格

  String status;    //订单状态
  }
2. 根据手机号seller查找所有订单:
* 前端api: **GET**  /orders/select/buyer
* json语句: {"phone":"手机号", "status":"状态（全部/已发货/未发货/..."}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<ExchangeDto>}
3. 根据手机号查找全部buyer预约:
* 前端api: **GET**  /booking/select/buyer
* json语句: {"phone":"手机号", "status":"状态（全部/已发货/未发货/..."}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<BookingDto>}
    * BookingDto {
      //         String bookNum;   //预约 number
      //         String number;   //product number
      //
      //         String name;  //product name
      //         String count;  //product count
      //         String  coverPic;   //product coverPic
      //
      //         Double price;  //product price
      //
      //         String status;  //product status
      //    }
4. 根据手机号查找全部seller预约:
* 前端api: **GET**  /booking/select/seller
* json语句: {"phone":"手机号", "status":"状态（全部/已发货/未发货/..."}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<BookingDto>}
5,卖方根据商品编号查询预约:
* 前端api: **GET**  /booking/select/bookings
* json语句: {"number":"商品编号"}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<BookingDetails>}
* BookingDetails{    String number;  //预约编号
  String avatar;  //买方头像
  String phone;  //买方phone
  String nickName;  //买方昵称}
  6,在订单中根据商品名称模糊搜索:
* 前端api: **GET**   /orders/fuzzy/name
* json语句: {"name":"商品名称/电话","isbuyer":1未buyer，0为seller}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<ExchangeDto>}
  7,在预约中根据商品名称模糊/电话搜索:
* 前端api: **GET**  /booking/fuzzy/name
* json语句: {"name":"商品名称/电话","isbuyer":1未buyer，0为seller}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<BookingDto>}
  8,卖方根据买方手机号和商品编号在预约信息里查找:
* 前端api: **GET**  /booking/select/phone
* json语句: {"phone":"手机号","number":"商品编号"}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<BookingDto>}
  9,根据订单编号获取订单详情:
* 前端api: **GET** /orders/details
* json语句: {"number":"订单编号"}
* 返回信息(Object):{code: "666" （成功）;mag:  ; data:List<OrderDetails>}
* OrderDetails {
  String number;  //订单编号
  String receipt;  //收货地址

  Date  time;;    //时间--根据订单状态获取其时间状态

  String expressDelivery;  //收货方式
  String deliveryId;  //快递单号
  Double freight;   //运费
  String payment;        //付款方式

  Double discounts;  //优惠
  String status;    //订单状态
  }
  二，订单相关
5. 查找seller:
* 前端api: **GET**  /booking/seller/info
* json语句: {"number":"商品编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:user)
6. 生成订单:
* 前端api: **POST**  /orders/build
* ExRequestDto{
  String bookNum;   //预约 number
  String projectNumber;  //商品编号
  String buyerPhone;      //买家phone
  String shipping;  //收货地址
  String expressDelivery;  //收货方式
  Integer ordersNum;     //商品数量
  Double price;          //交易价格
  String payment;        //付款方式
  Double freight;          //运费
  Double discounts;      //优惠
* json语句: {(ExRequestDto)（"projectNumber":"商品编号", "buyerPhone":"买家phone",...）}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  7.卖方确认发货   /orders/receiving:
* 前端api: **PUT** /orders/receiving
* json语句: {"number":"订单编号","deliveryId":"快递单号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
8. 买方确认收货:
* 前端api: **PUT**  /orders/received
* json语句: {"number":"订单编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
9. 买方退款:
* 前端api: **PUT**  /orders/after
* json语句: {"number":"订单编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
10. 卖方确认退款:
* 前端api: **PUT** /orders/refund
* json语句: {"number":"订单编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
11. seller删除订单:
* 前端api: **DELETE**  /orders/seller/delete
* json语句: {"number":"订单编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
12. buyer删除订单:
* 前端api: **DELETE**  /orders/buyer/delete
* json语句: {"number":"订单编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  三，预约相关
13. 新增预约:
* 前端api: **POST**  /booking/add
* json语句: {（BoReDto）"sellerId":"seller phone","buyerId":"buyer phone"....}
* BoReDto {         String sellerId;   //seller phone
  //                String  buyerId;    //buyer phone
  //                String productId;   //product number
  //                String productName;   //product name
  //                Integer ordersNum;   //product 数量
  //                Double price;   //product price}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  14买方删除已拒绝的预约:
* 前端api: **DELETE**  /booking/delete
* json语句: {"number":"预约编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  15卖家确认预约:
* 前端api: **PUT**  /booking/acquire
* json语句: {"number":"预约编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  16买方取消预约/卖方拒绝预约:
* 前端api: **PUT**  /orders/cancel/booking
* json语句: {"number":"预约编号","isbuyer":1为buyer,0为seller}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  17,取消退款:
* 前端api: **PUT**  /orders/cancel
* json语句: {"number":"订单编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
####订单和预约相关状态
    预约
    已预约
    待下单
    已拒绝

    订单
    待发货
    待收货
    已购买
    待退款
    已退款

    商品：
    0:  已上架，審核通過   --- 待预约
    4:  已售空
    5:  查预约
###订单/预约查找相关  OrdersSearchController.java

    //buyer查找所有订单
    @RequestMapping(value ="/orders/select/buyer",method = RequestMethod.GET)
    public ResponseEntity<?> selectBuyerOrder(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("status");
        return ResponseEntity.ok(service.selectBuyerOrder(phone,status));
    }

    //seller查找所有订单
    @RequestMapping(value ="/orders/select/seller",method = RequestMethod.GET)
    public ResponseEntity<?> selectSellerOrder(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("status");
        return ResponseEntity.ok(service.selectSellerOrder(phone,status));
    }

    // 查找全部buyer预约  
    @RequestMapping(value ="/booking/select/buyer",method = RequestMethod.GET)
    public ResponseEntity<?> selectAllBuyer(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("status");
        return ResponseEntity.ok(service.selectAllBuyer(phone,status));

    }

    //查找全部seller预约  /booking/select/seller
    @RequestMapping(value ="/appointments/select/seller",method = RequestMethod.GET)
    public ResponseEntity<?> selectAllSeller(HttpServletRequest request){
        String phone = request.getParameter("phone");
        String status= request.getParameter("status");
        return ResponseEntity.ok(service.selectAllSeller(phone,status));
    }
##订单相关接口    ExchangeController.java

    //查找seller
    @RequestMapping(value ="/booking/seller/info",method = RequestMethod.GET)
    public ResponseEntity<?> getSellerInfo(@RequestParam String number) {
        return ResponseEntity.ok(service.getSellerInfo(number));
    }

    //生成订单
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok(service.build(request));
    }

    //卖方确认发货
    @RequestMapping(value ="/orders/receiving",method = RequestMethod.PUT)
    public ResponseEntity<?> toBeReceived(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        String deliveryId= param.get("deliveryId").toString();
        return ResponseEntity.ok(service.toBeReceived(number,deliveryId));
    }

    //买方确认收货
    @RequestMapping(value ="/orders/received",method = RequestMethod.PUT)
    public ResponseEntity<?> received(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.received(number));

    }

    //退款
    @RequestMapping(value ="/orders/after",method = RequestMethod.PUT)
    public ResponseEntity<?> after(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.after(number));

    }

    //退款
    @RequestMapping(value ="/orders/refund",method = RequestMethod.PUT)
    public ResponseEntity<?> sellerAfter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer count= Integer.valueOf(param.get("number").toString());
        return ResponseEntity.ok(service.sellerAfter(number,count));
    }

    //删除订单   
    @RequestMapping(value ="/orders/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam String number) {
        return ResponseEntity.ok(service.delete(number));

    }

    //seller删除订单
    @RequestMapping(value ="/orders/seller/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> sellerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.sellerdelete(number));

    }

    //buyer删除订单  
    @RequestMapping(value ="/orders/buyer/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> buyerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.buyerdelete(number));
    }

####预约相关   ExchangeController.java
    //新增预约  POST  
    @RequestMapping(value ="/booking/add",method = RequestMethod.POST)
    public ResponseEntity<?> addappointments(@RequestBody BoReDto request) {
    return ResponseEntity.ok(service.addappointments(request));
    }

    //买方删除已拒绝的预约 
    @RequestMapping(value ="/booking/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteappointments(@RequestParam String number) {
    return ResponseEntity.ok(service.deleteappointments(number));
    }

    //卖家确认预约  PUT 
    @RequestMapping(value ="/booking/acquire",method = RequestMethod.PUT)
    public ResponseEntity<?> acquireappointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        int count = Integer.parseInt(param.get("number").toString());
        return ResponseEntity.ok(service.acquireappointments(number,count));
    }


    // 买方取消预约/卖方拒绝预约  
    @RequestMapping(value ="/orders/cancel/booking",method = RequestMethod.PUT)
    public ResponseEntity<?> cancelAppointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer isbuyer= Integer.valueOf(param.get("isbuyer").toString());
        return ResponseEntity.ok(service.cancelAppointments(number,isbuyer));
    }
***
##一对一聊天   pom.xml
    <!--WebSocket-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>

###反馈功能    UserMangerController.java
反馈数据库 feedback.sql
1，新增反馈:
* 前端api: **POST**  /feedback/add
* json语句: {"phone":"反馈人电话"，"content":"反馈人内容"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  2，删除反馈:
* 前端api: **DELETE**  /feedback/delete
* json语句: {"number":"反馈编号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  3，查找所有反馈:
* 前端api: **GET**  /feedback/all
* json语句: 无
* 返回信息(Object):Result(msg: ; code:"666" ;data:List<FeedbackDto>)
  FeedbackDto {
  String number;  //反馈编号

  String phone;    //反馈人电话

  String name;    //反馈人姓名

  Date feedTime;    //反馈时间

  String content;  //反馈内容

  Integer status;   //消息状态：已读为1，未读为0
  }
  5，管理员查看所有有违规记录且authority暂未设置成违规的用户
* 前端api: **GET**  /feedback/user
* json语句: 无
* 返回信息(Object):Result(msg: ; code:"666" ;data:List<FeedUserDto>)
* FeedUserDto {
   String name;  //用户名

   String phone;    //用户电话

   String avatar;    //用户头像

   Integer violationCount;    //违规次数
   }
###用户管理    UserMangerController.java
1，权限更改    普通-->违规用户 number=0  违规用户-->普通 number=1
* 前端api: **PUT**  /manage/user/authority
* json语句: {
  "ids": [
  3,4,5
  ],
  "number": 1
  }
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
2，根据手机查找用户信息
* 前端api: **GET**  /manage/user
* json语句: {"phone":"手机号"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:FeedUserDto)
  3，查看所有用户:
* 前端api: **GET**  /manage/user/all
* json语句: 无
* 返回信息(Object):Result(msg: ; code:"666" ;data:List<FeedUserDto>)
  FeedUserDto {
  private Integer id;

  private String avatar;    //用户头像

  private String userName;  //用户名

  private String realName;  //用户名

  private String authority;    //用户权限

  private Integer violationCount;    //违规次数

  private Integer buy;

  private Integer sell;

}
  4，根据权限、交易、违规筛选
* 前端api: **GET**  /manage/user/select
* json语句: {"number1":权限, 为-1时为全部
*           "number2":违规,为-1时为全部
*            "number3":交易为-1时为全部}
* 返回信息(Object):Result(msg: ; code:"666" ;data:List<FeedUserDto>)
  5，删除用户
* 前端api: **DELETE**  /manage/user/delete
* json语句: [1,2,3]
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
  6，根据用户名或真实姓名模糊查询
* 前端api: **GET**  /manage/user/name
* json语句: {"name":"用户名或真实姓名"}
* 返回信息(Object):Result(msg: ; code:"666" ;data:List<FeedUserDto>)
  7，修改密码为123456
* 前端api: **PUT**  /manage/user/password
* json语句: [1,2,3]
* 返回信息(Object):Result(msg: ; code:"666" ;data:)
8，获取权限目录
* 前端api: **GET**  manage/select/authority
* json语句:无
* 返回{
  "code": "666",
  "msg": "操作成功！",
  "data": [  {
  "number": 0,
  "name": "已认证"
  },  {
  "number": 1,
  "name": "未认证"
  },  {
  "number": 2,
  "name": "已禁用"
  }  ]
  }
9，获取违规目录
* 前端api: **GET**  manage/select/violation
* json语句:无
* 返回{
  "code": "666",
  "msg": "操作成功！",
  "data": [
  {
  "number": 0,
  "range": [  0,  10  ]
  },
  {
  "number": 1,
  "range": [  11,  20  ]
  }}
10，获取交易目录
* 前端api: **GET** manage/select/trade
* json语句:无
* 返回同获取违规目录
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
