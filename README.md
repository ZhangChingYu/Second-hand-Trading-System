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
## 头像
    用户未设置头像时使用路径：C:/Users/Sunny/Desktop/Avatar/default/default_0.jpg
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
##订单相关接口

    //查找seller /appointments/getsellerinfo
    //需要 number为商品编号   输出user
    @RequestMapping(value ="/appointments/getsellerinfo",method = RequestMethod.GET)
    public ResponseEntity<?> getsellerinfo(@RequestParam String number) {
        return ResponseEntity.ok(service.getsellerinfo(number));
    }

    //新增预约  /appointments/add
    // 输入    BoReDto { String sellerId;   //seller phone
    //                String  buyerId;    //buy phone
    //                String productId;   //product number
    //                Integer ordersNum;   //product 数量
    //                Double price;   //product price}
    //输出    Result(msg: ; code:"666" ;data:)
    @RequestMapping(value ="/appointments/add",method = RequestMethod.POST)
    public ResponseEntity<?> addappointments(@RequestBody BoReDto request) {
        return ResponseEntity.ok(service.addappointments(request));
    }

    //删除预约  /appointments/delete
    // 输入number为预约编号
    //输出    Result(msg: ; code:"666" ;data:)
    @RequestMapping(value ="/appointments/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteappointments(@RequestParam String number) {
    return ResponseEntity.ok(service.deleteappointments(number));

    }
    //查找全部buyer预约    /appointments/selectallbuyer
    // 输入 phone（buyer）
    //    输出BookingDto {
    //         String number;   //product number
    //
    //         String name;  //product name
    //
    //         String  coverPic;   //product coverPic
    //
    //         Double pice;  //product pice
    //
    //         Integer status;  //product status
    //    }
    @RequestMapping(value ="/appointments/selectallbuyer",method = RequestMethod.GET)
    public ResponseEntity<?> selectallappointments(@RequestParam String phone) {
    return ResponseEntity.ok(service.selectallbuyer(phone));

    }

    //查找全部seller预约  /appointments/selectallseller
    // 输入（seller） phone  输出同buyer
    @RequestMapping(value ="/appointments/selectallseller",method = RequestMethod.GET)
    public ResponseEntity<?> selectallseller(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectallseller(phone));
    }

    //卖家确认预约  /appointments/acquire
    // 输入 预约编号number和商品数量number
    //输出    Result(msg: ; code:"666" ;data:)
    @RequestMapping(value ="/appointments/acquire",method = RequestMethod.PUT)
    public ResponseEntity<?> acquireappointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        int count = Integer.parseInt(param.get("number").toString());
        return ResponseEntity.ok(service.acquireappointments(number,count));
    }


    //买方/卖方取消预约   /orders/cancelappointments
    // 预约编号number和isbuyer:是否为buyer   是为1为buyer，否为0
    //输出    Result(msg: ; code:"666" ;data:)
    @RequestMapping(value ="/orders/cancelappointments",method = RequestMethod.PUT)
    public ResponseEntity<?> cancelappointments(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer isbuyer= Integer.valueOf(param.get("isbuyer").toString());
        return ResponseEntity.ok(service.cancelappointments(number,isbuyer));
    }

    //生成订单----/orders/build
    // ExRequestDto{
    //      String projectnubmer;  //商品编号
    //      String buyerphone;      //买家phone
    //      String shippingaddre;  //收货地址
    //      String expressDelivery;  //收货方式
    //      Integer ordersNum;     //商品数量
    //      Double price;          //交易价格
    //      Double freight;          //运费
    //      Double discounts;      //优惠
    //      String payment;        //付款方式
    //      String remark;         //备注}
    //输出    Result(msg: ; code:"666" ;data:)
    @RequestMapping(value ="/orders/build",method = RequestMethod.POST)
    public ResponseEntity<?> build(@RequestBody ExRequestDto request) {
        return ResponseEntity.ok(service.build(request));
    }

    //卖方确认发货-----/orders/tobereceived    输入订单编号number和快递单号deliveryId
    @RequestMapping(value ="/orders/tobereceived",method = RequestMethod.PUT)
    public ResponseEntity<?> tobereceived(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        String deliveryId= param.get("deliveryId").toString();
        return ResponseEntity.ok(service.tobereceived(number,deliveryId));
    }

    //买方确认收货-----/orders/received    输入订单编号number
    @RequestMapping(value ="/orders/received",method = RequestMethod.PUT)
    public ResponseEntity<?> received(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.received(number));

    }

    //退款、售后--/orders/after    输入订单编号number
    @RequestMapping(value ="/orders/after",method = RequestMethod.PUT)
    public ResponseEntity<?> after(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        return ResponseEntity.ok(service.after(number));

    }

    //退款、售后--/orders/sellerafter    输入订单编号number
    @RequestMapping(value ="/orders/sellerafter",method = RequestMethod.PUT)
    public ResponseEntity<?> sellerafter(@RequestBody Map<String, Object> param) {
        String number = param.get("number").toString();
        Integer count= Integer.valueOf(param.get("number").toString());
        return ResponseEntity.ok(service.sellerafter(number,count));
    }

    //删除订单   /orders/delete  输入订单编号number
    @RequestMapping(value ="/orders/delete",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam String number) {
        return ResponseEntity.ok(service.delete(number));

    }

    //seller删除订单 /orders/sellerdelete  输入订单编号number
    @RequestMapping(value ="/orders/sellerdelete",method = RequestMethod.DELETE)
    public ResponseEntity<?> sellerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.sellerdelete(number));

    }

    //buyer删除订单  输入订单编号number
    @RequestMapping(value ="/orders/buyerdelete",method = RequestMethod.DELETE)
    public ResponseEntity<?> buyerdelete(@RequestParam String number) {
        return ResponseEntity.ok(service.buyerdelete(number));

    }

    查找所有订单输出为
    ExchangeDto {
    String pronumber;   //商品编号

    String ordnumber;   //订单编号

    String name;     //商品名称

    String  coverPic;   //商品图片

    Double pice;     //商品价格

    Integer status;    //订单状态
    }
    //buyer查找所有订单  /orders/selectbuyerorder  phone
    @RequestMapping(value ="/orders/selectbuyerorder",method = RequestMethod.GET)
    public ResponseEntity<?> selectbuyerorder(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectbuyerorder(phone));
    }

    //seller查找所有订单  /orders/selectsellerorder  phone
    @RequestMapping(value ="/orders/selectsellerorder",method = RequestMethod.GET)
    public ResponseEntity<?> selectsellerorder(@RequestParam String phone) {
        return ResponseEntity.ok(service.selectsellerorder(phone));
    }

