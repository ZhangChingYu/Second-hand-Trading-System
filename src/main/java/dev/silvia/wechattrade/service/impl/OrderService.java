package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.*;
import dev.silvia.wechattrade.dto.booking.BoReDto;
import dev.silvia.wechattrade.dto.booking.BookDetails;
import dev.silvia.wechattrade.dto.booking.BookingDto;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.dto.exchangedto.ExchangeDto;
import dev.silvia.wechattrade.dto.exchangedto.OrderDetails;
import dev.silvia.wechattrade.dto.exchangedto.RefundDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.*;
import dev.silvia.wechattrade.handlers.OrderCodeUtils;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.repository.*;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.handlers.picSize.PicUtil;
import dev.silvia.wechattrade.handlers.picSize.PictureSize;
import dev.silvia.wechattrade.service.IOrderService;
import dev.silvia.wechattrade.service.IProductService;
import dev.silvia.wechattrade.service.IRegisterService;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderService extends ServiceImpl<ProductDao, Product> implements IOrderService {
    private  final  static int avatar_width= PictureSize.avatar_width;
    private  final  static  int avatar_height=PictureSize.avatar_height;
    private final String picture_url = FileDirector.PRODUCT_URL;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository accountRepository;
    @Autowired
    private ExchangeInfoRepository exRepository;
    @Autowired
    private BuyerRepository buy;
    @Autowired
    private SellerRepository sel;
    @Resource
    private IRegisterService reService;
    @Resource
    private IProductService service;
    @Autowired
    TransferUTF8 transferUTF8 = new TransferUTF8();
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReadFile readFile = new ReadFile();

    private Result res;

    @Autowired
    ProductDao productDao;

    private final String ph = "^[1][3578]\\d{9}$";

    @Override
    public Result getSellerInfo(String number) {
        //获取商品信息
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", number);
        Product product = productDao.selectOne(productWrapper);

        User user4=transferUTF8.switchUtf8Tc(userRepository.findByPhone(product.getSPhone()).get());
        //图片路径
        String picture1;
        if(user4.getAvatar()==null||user4.getAvatar().isEmpty()){
            //默认图片
            picture1 = PicUtil.resizeImageToSize(FileDirector.AVATAR_URL,avatar_width,avatar_height);
            user4.setAvatar(picture1);
        }
        else{
            picture1 = PicUtil.resizeImageToSize(readFile.getAvatarPicture(user4.getPhone()),avatar_width,avatar_height);
            user4.setAvatar(picture1);
        }
        res=new Result(ResultCode.SUCCESS,user4);
        return res;
    }

    @Override
    public Result build(ExRequestDto request) {
        //预约信息表   删除已下单预约
        Booking bo=accountRepository.findByNumber(request.getBookNum());

        //获取商品信息
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number",bo.getProductId());
        Product pro = productDao.selectOne(productWrapper);
        if(pro.getStatus()==0){
            pro.setStatus(5);
        }
        productDao.updateById(pro);
        ExchangeInfo ex=new ExchangeInfo();

        //订单信息表
        String pronum=OrderCodeUtils.createCode("DT");
        ex.setNumber(pronum);
        ex.setOrdersNum(bo.getOrdersNum());
        ex.setPrice(request.getPrice());
        ex.setPayment(transferUTF8.CtoUTF8(request.getPayment()));
        ex.setBuildTime(new Date());
        ex.setProductNum(bo.getProductId());
        ex.setName(pro.getName());
        ex.setExpressDelivery(transferUTF8.CtoUTF8(request.getExpressDelivery()));
        ex.setFreight(6.00);
        ex.setDiscounts(request.getDiscounts());
        ex.setIsDelete(0);
        if(Objects.equals(request.getExpressDelivery(), "自取")){
            ex.setStatus(transferUTF8.CtoUTF8("待收货"));
        }
        else{
            ex.setStatus(transferUTF8.CtoUTF8("待发货"));
        }
        exRepository.save(ex);

        //买家信息表
        Buyer buyer=new Buyer();
        buyer.setAddress(transferUTF8.CtoUTF8(request.getMyAddress()));
        buyer.setRemark(transferUTF8.CtoUTF8(request.getRemark()));
        buyer.setExchangeId(pronum);  //外键
        buyer.setPhone(bo.getBuyerId());
        buy.save(buyer);

        //卖家信息表
        Seller seller=new Seller();
        seller.setAddress(transferUTF8.CtoUTF8(pro.getAddress()));
        seller.setExchangeId(pronum);  //外键
        seller.setPhone(pro.getSPhone());
        sel.save(seller);

        //删除已下单预约
        accountRepository.delete(bo);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result toBeReceived(String number, String deliveryId) {
        //订单
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number).get();
        ex.setDeliveryId(deliveryId);
        ex.setStatus(transferUTF8.CtoUTF8("待收货"));
        exRepository.save(ex);

        //卖家
        Seller seller;
        seller=sel.findByExchangeId(number);
        seller.setShippingTime(new Date());
        sel.save(seller);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result received(String number) {
        //订单
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number).get();
        ex.setStatus(transferUTF8.CtoUTF8("已购买"));
        exRepository.save(ex);
        //卖方
        Buyer buyer=buy.findByExchangeId(number);
        buyer.setReceiptTime(new Date());
        buy.save(buyer);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result after(RefundDto refundDto) {
        //订单
        ExchangeInfo ex;
        ex=exRepository.findByNumber(refundDto.getNumber()).get();
        ex.setStatus(transferUTF8.CtoUTF8("待退款"));
        exRepository.save(ex);

        //买家
        Buyer buyer=buy.findByExchangeId(refundDto.getNumber());
        String remark=refundDto.getRefundRequest().getGoodsState()+"#"+
                refundDto.getRefundRequest().getRefundReason()+"#"
                +refundDto.getRefundRequest().getDescription();
        buyer.setRemark(transferUTF8.CtoUTF8(remark));
        buyer.setRefundTime(new Date());
        buy.save(buyer);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result sellerAfter(String number) {
        //订单状态修改
        ExchangeInfo ex;
        String proNum = OrderCodeUtils.createCode("TK");
        ex=exRepository.findByNumber(number).get();
        ex.setNumber(proNum);
        ex.setStatus(transferUTF8.CtoUTF8("已退款"));
        exRepository.save(ex);

        //卖家
        Seller seller;
        seller=sel.findByExchangeId(number);
        seller.setRefundTime(new Date());
        sel.save(seller);

        //修改商品状态
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number",ex.getProductNum());
        Product product = productDao.selectOne(productWrapper);
        Integer num=product.getStorage()+ex.getOrdersNum();
        product.setStorage(num);
        if(product.getStatus()==4){
            List<ExchangeInfo> orders=exRepository.findByProductNum(product.getNumber());
            if(orders.size()==0){
                product.setStatus(0);
            }
            else
                product.setStatus(5);
        }
        productDao.updateById(product);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result cancelAfter(String number) {
        //订单
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number).get();
        //卖家
        Seller seller=sel.findByExchangeId(number);
        //买家
        Buyer buyer=buy.findByExchangeId(number);
        String remark=transferUTF8.UTF8toC(buyer.getRemark());
        String[] remark_parts = remark.split("#");
        ex.setStatus(transferUTF8.CtoUTF8(remark_parts[0]));
        exRepository.save(ex);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result delete(String number) {
        exRepository.delete(exRepository.findByNumber(number).get());
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result sellerDelete(String number) {
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number).get();
        if(ex.getIsDelete()==2){
            exRepository.delete(ex);
        }
        else{
            ex.setIsDelete(1);
        }
        sel.delete(sel.findByExchangeId(number));
        return res;
    }

    @Override
    public Result buyerDelete(String number) {
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number).get();
        if(ex.getIsDelete()==1){
            exRepository.delete(ex);
        }
        else{
            ex.setIsDelete(2);
        }
        buy.delete(buy.findByExchangeId(number));
        return res;
    }

    @Override
    public Result selectAllOrder(String status) {
        List<Seller> sell=sel.findAll();
        List<ExchangeDto> bookLst=new ArrayList<>();
        ProductDetailVo pro;
        ExchangeInfo ex;
        for (Seller value : sell) {

            //获取产品编号
            if (Objects.equals(status, "全部")) {
                ex = exRepository.findByNumber(value.getExchangeId()).get();
            } else {
                ex = exRepository.findByNumberAndStatus(value.getExchangeId(), transferUTF8.CtoUTF8(status));
            }
            //获取商品信息
            pro = service.getProductDetail(ex.getProductNum());
            ExchangeDto bookDto=new ExchangeDto();

            bookDto.setName(pro.getName());
            bookDto.setState(transferUTF8.UTF8toC(ex.getStatus()));
            bookDto.setCount(ex.getOrdersNum());
            //图片信息
            String path = "";
            if (pro.getPicture_count() == 0) {
                bookDto.setCoverPic(path);
            } else {
                List<String> pictures = readFile.getPicturesBase64(ex.getProductNum(), pro.getPictures().size());
                bookDto.setCoverPic(pictures.get(0));
            }
            //输出信息
            bookDto.setPrice(ex.getPrice());
            bookDto.setProNumber(ex.getProductNum());
            bookDto.setOrdNumber(ex.getNumber());
            bookLst.add(bookDto);
        }
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result selectSellerOrder(String phone,String status) {
        List<Seller> sell=sel.findAll();
        List<ExchangeDto> bookLst=new ArrayList<>();
        ExchangeInfo ex;
        for (Seller value : sell) {
            if (Objects.equals(value.getPhone(), phone)) {
                //获取产品编号
                if (Objects.equals(status, "全部")) {
                    ex = exRepository.findByNumber(value.getExchangeId()).get();
                } else {
                    ex = exRepository.findByNumberAndStatus(value.getExchangeId(), transferUTF8.CtoUTF8(status));
                }
                //获取商品信息
                if(ex.getIsDelete()!=1){
                    //获取商品图片
                    //获取商品信息
                    QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                    productWrapper.eq("number",ex.getProductNum());
                    Product pro = productDao.selectOne(productWrapper);
                    ExchangeDto bookDto=new ExchangeDto();

                    //图片信息
                    String path = "";
                    if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                        String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                        path= ReadFile.getBaseFile(url);
                        bookDto.setCoverPic(path);

                    }

                    bookDto.setCount(ex.getOrdersNum());
                    bookDto.setName(transferUTF8.UTF8toC(pro.getName()));
                    bookDto.setState(transferUTF8.UTF8toC(ex.getStatus()));
                    bookDto.setPrice(ex.getPrice());
                    bookDto.setProNumber(ex.getProductNum());
                    bookDto.setOrdNumber(ex.getNumber());
                    bookLst.add(bookDto);
                }
            }
        }
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result selectBuyerOrder(String phone,String status) {
        //获取用户所有商品
        List<Buyer> bu=buy.findAll();
        List<ExchangeDto> bookLst=new ArrayList<>();
        ExchangeInfo ex;
        for (Buyer value : bu) {
            if (Objects.equals(value.getPhone(), phone)) {
                //获取产品编号
                if (Objects.equals(status, "全部")) {
                    ex = exRepository.findByNumber(value.getExchangeId()).get();
                } else {
                    ex = exRepository.findByNumberAndStatus(value.getExchangeId(), transferUTF8.CtoUTF8(status));
                }
                if(ex.getIsDelete()!=2){
                    //获取商品图片
                    //获取商品信息
                    QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                    productWrapper.eq("number",ex.getProductNum());
                    Product pro = productDao.selectOne(productWrapper);
                    ExchangeDto bookDto=new ExchangeDto();

                    //图片信息
                    String path = "";
                    if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                        String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                        path= ReadFile.getBaseFile(url);
                        bookDto.setCoverPic(path);
                    }
                    bookDto.setCount(ex.getOrdersNum());
                    bookDto.setName(transferUTF8.UTF8toC(pro.getName()));
                    bookDto.setState(transferUTF8.UTF8toC(ex.getStatus()));
                    bookDto.setPrice(ex.getPrice());
                    bookDto.setProNumber(ex.getProductNum());
                    bookDto.setOrdNumber(ex.getNumber());
                    bookLst.add(bookDto);
                }
            }
        }
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result addAppointments(BoReDto request) {
        String proNum=OrderCodeUtils.createCode("YY");
        Booking booking =new Booking();
        booking.setStatus(transferUTF8.CtoUTF8("已预约"));
        booking.setAppointmentTime(new Date());
        booking.setProductId(request.getProductId());
        booking.setNumber(proNum);
        booking.setPrice(request.getPrice());
        booking.setOrdersNum(request.getOrdersNum());
        booking.setBuyerId(request.getBuyerId());
        booking.setSellerId(request.getSellerId());
        booking.setName(transferUTF8.CtoUTF8(request.getProductName()));
        booking.setIsDelete(0);
        accountRepository.save(booking);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result deleteAppointments(String number) {
        Booking book=accountRepository.findByNumber(number);
        if(Objects.equals(book.getStatus(), transferUTF8.CtoUTF8("已拒绝"))){
            accountRepository.delete(book);
            res=new Result(ResultCode.SUCCESS);
        }
        else{
            res=new Result(ResultCode.FAIL);
        }
        return res;
    }

    @Override
    public Result cancelAppointments(String number, Integer isbuyer) {
        Booking book=accountRepository.findByNumber(number);
        if(Objects.equals(book.getStatus(), transferUTF8.CtoUTF8("已预约"))){
            if(isbuyer==1){
                accountRepository.delete(book);
            }
            else{
                book.setStatus(transferUTF8.CtoUTF8("已拒绝"));
                accountRepository.save(book);
            }
            res=new Result(ResultCode.SUCCESS);
        }
        else if(Objects.equals(book.getStatus(), transferUTF8.CtoUTF8("待下单"))){
            if(isbuyer==1){
                //修改商品状态
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number", book.getProductId());
                Product product = productDao.selectOne(productWrapper);

                int c= product.getStorage() + book.getOrdersNum();
                product.setStorage(c);
                if(product.getStatus()==4){
                    product.setStatus(5);
                }
                productDao.updateById(product);
                accountRepository.delete(book);
            }
            else{
                book.setStatus(transferUTF8.CtoUTF8("已拒绝"));
                accountRepository.save(book);
            }
            res=new Result(ResultCode.SUCCESS);
        }
        else{
            res=new Result(ResultCode.FAIL);
        }
        return res;
    }

    @Override
    public Result selectAllBuyer(String phone, String status) {
        //获取用户所有预约
        List<BookingDto> bookLst=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "全部")){
            bidList=accountRepository.findByBuyerId(phone);
        }
        else{
            bidList=accountRepository.findByBuyerIdAndStatus(phone,transferUTF8.CtoUTF8(status));
        }
        for (Booking booking : bidList) {

            //获取商品图片
            //获取商品信息
            QueryWrapper<Product> productWrapper = new QueryWrapper<>();
            productWrapper.eq("number",booking.getProductId());
            Product pro = productDao.selectOne(productWrapper);
            BookingDto bookDto=new BookingDto();
            //图片信息
            String path = " ";
            if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                path= ReadFile.getBaseFile(url);
                bookDto.setCoverPic(path);
            }

            bookDto.setCount(booking.getOrdersNum());
            bookDto.setName(transferUTF8.UTF8toC(pro.getName()));
            bookDto.setState(transferUTF8.UTF8toC(booking.getStatus()));
            bookDto.setBookNum(booking.getNumber());
            bookDto.setPrice(booking.getPrice());
            bookDto.setNumber(pro.getNumber());
            bookLst.add(bookDto);

        }
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result selectAllSeller(String phone, String status) {
        //获取用户所有预约
        List<BookingDto> bookLst=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "全部")){
            bidList=accountRepository.findBySellerId(phone);
        }
        else{
            bidList=accountRepository.findBySellerIdAndStatus(phone,transferUTF8.CtoUTF8(status));
        }
        for (Booking booking : bidList) {
            if (!Objects.equals(transferUTF8.UTF8toC(booking.getStatus()), "已拒绝")) {
                BookingDto bookDto=new BookingDto();
                //获取商品图片
                //获取商品信息
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",booking.getProductId());
                Product pro = productDao.selectOne(productWrapper);

                //图片信息
                String path = "";
                if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                    String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                    path= ReadFile.getBaseFile(url);
                    bookDto.setCoverPic(path);
                }

                bookDto.setCount(booking.getOrdersNum());
                bookDto.setName(transferUTF8.UTF8toC(pro.getName()));
                bookDto.setState(transferUTF8.UTF8toC(booking.getStatus()));
                bookDto.setBookNum(booking.getNumber());
                bookDto.setPrice(booking.getPrice());
                bookDto.setNumber(pro.getNumber());
                bookLst.add(bookDto);
            }
        }
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result selectAll(String status) {
        List<BookingDto> bookLst=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "全部")){
            bidList=accountRepository.findByStatus(transferUTF8.CtoUTF8(status));
        }
        else{
            bidList=accountRepository.findAll();
        }
        ProductDetailVo pro;
        for (Booking booking : bidList) {
            //获取商品信息
            BookingDto bookDto=new BookingDto();
            pro = service.getProductDetail(booking.getProductId());

            bookDto.setName(pro.getName());
            bookDto.setState(transferUTF8.UTF8toC(booking.getStatus()));
            //图片信息
            String path = "";
            if (pro.getPicture_count() == 0) {
                bookDto.setCoverPic(path);
            } else {
                List<String> pictures = readFile.getPicturesBase64(booking.getProductId(), pro.getPictures().size());
                bookDto.setCoverPic(pictures.get(0));
            }
            bookDto.setCount(booking.getOrdersNum());
            bookDto.setBookNum(booking.getNumber());
            bookDto.setPrice(booking.getPrice());
            bookDto.setNumber(booking.getNumber());
            bookLst.add(bookDto);
        }
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result selectAllByName(String name, Integer type, Integer isbuyer) {
        if(type==1){
            //订单信息
            List<ExchangeInfo> ei=exRepository.findByNameLike("%"+name+"%");
            List<ExchangeDto> bidList = new ArrayList<>();
            for (ExchangeInfo booking : ei) {
                if(isbuyer==1&&booking.getIsDelete()!=2
                    ||isbuyer==0&&booking.getIsDelete()!=1){
                    ExchangeDto ed=new ExchangeDto();
                    //获取商品图片
                    //获取商品信息
                    QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                    productWrapper.eq("number",booking.getProductNum());
                    Product pro = productDao.selectOne(productWrapper);

                    //图片信息
                    String path = "";
                    if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                        String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                        path= ReadFile.getBaseFile(url);
                        ed.setCoverPic(path);
                    }

                    ed.setCount(booking.getOrdersNum());
                    ed.setName(transferUTF8.UTF8toC(pro.getName()));
                    ed.setState(transferUTF8.UTF8toC(booking.getStatus()));
                    ed.setOrdNumber(booking.getNumber());
                    ed.setPrice(booking.getPrice());
                    ed.setProNumber(booking.getProductNum());
                    bidList.add(ed);
                }
            }
            res=new Result(ResultCode.SUCCESS, bidList);
        }
        else{
            //预约信息
            BookingDto bok=new BookingDto();
            List<Booking> bi;
            if(isbuyer==1){
                if (name.matches(ph)){
                    bi=accountRepository.findByBuyerId(name);
                }
                else{
                    bi=accountRepository.findByNameLike("%"+name+"%");
                }
            }
            else{
                if (name.matches(ph)){
                    bi=accountRepository.findBySellerId(name);
                }
                else{
                    bi=accountRepository.findByNameLike("%"+name+"%");
                }
            }
            List<BookingDto> bidList = new ArrayList<>();

            for (Booking booking : bi) {
                if(isbuyer==1&&booking.getIsDelete()!=2
                        ||isbuyer==0&&booking.getIsDelete()!=1) {
                    //获取商品信息
                    QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                    productWrapper.eq("number",booking.getProductId());
                    Product pro = productDao.selectOne(productWrapper);

                    //图片信息
                    String path = "";
                    if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                        String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                        path= ReadFile.getBaseFile(url);
                        bok.setCoverPic(path);
                    }

                    bok.setCount(booking.getOrdersNum());
                    bok.setName(transferUTF8.UTF8toC(pro.getName()));
                    bok.setState(transferUTF8.UTF8toC(booking.getStatus()));
                    bok.setBookNum(booking.getNumber());
                    bok.setPrice(booking.getPrice());
                    bok.setNumber(booking.getProductId());
                    bidList.add(bok);
                }
            }
            res=new Result(ResultCode.SUCCESS, bidList);
        }
        return res;
    }

    @Override
    public Result sellerBookingByName(String number, String state) {
        List<Booking> bookLst=new ArrayList<>();
        List<ExchangeInfo> exList=new ArrayList<>();
        if(Objects.equals(state, "全部")){
            bookLst=accountRepository.findByProductId(number);
            exList=exRepository.findByProductNum(number);
        }
        if(Objects.equals(state, "待处理")){
            bookLst=accountRepository.findByProductIdAndStatus(number,transferUTF8.CtoUTF8("已预约"));
        }
        if(Objects.equals(state, "已卖出")){
            exList=exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8("已购买"));
        }
        if(Objects.equals(state, "待发货")||Objects.equals(state, "待退款")){
            exList=exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8(state));
        }
        if(Objects.equals(state, "其他")){
            bookLst=accountRepository.findByProductIdAndStatus(number,transferUTF8.CtoUTF8("待下单"));
            bookLst.addAll(accountRepository.findByProductIdAndStatus(number,transferUTF8.CtoUTF8("已拒绝")));
            exList=exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8("待收货"));
            exList.addAll(exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8("已退款")));
        }
        List<BookDetails> bidList=new ArrayList<>();
        for (Booking value : bookLst) {
            BookDetails bookDto=new BookDetails();
            User user2=userRepository.findByPhone(value.getBuyerId()).get();
            bookDto.setPhone(user2.getPhone());
            bookDto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
            bookDto.setNumber(number);
            String status=transferUTF8.UTF8toC(value.getStatus());
            if(Objects.equals(status, "已预约")){
                bookDto.setState("待处理");
            }
            else
                bookDto.setState(transferUTF8.UTF8toC(value.getStatus()));
            //图片路径
            String picture1;
            if(user2.getAvatar()==null||user2.getAvatar().isEmpty()){
                //默认图片
                picture1 = PicUtil.resizeImageToSize(FileDirector.AVATAR_URL,avatar_width,avatar_height);
                bookDto.setAvatar(picture1);
            }
            else{
                picture1 = PicUtil.resizeImageToSize(readFile.getAvatarPicture(user2.getPhone()),avatar_width,avatar_height);
                bookDto.setAvatar(picture1);
            }
            bookDto.setCount(value.getOrdersNum());
            bidList.add(bookDto);
        }
        for (ExchangeInfo value : exList) {
            BookDetails bookDto=new BookDetails();
            Seller seller=sel.findByExchangeId(value.getNumber());
            Buyer buyer=buy.findByExchangeId(value.getNumber());
            User user2=userRepository.findByPhone(buyer.getPhone()).get();
            bookDto.setPhone(user2.getPhone());
            bookDto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
            bookDto.setNumber(number);
            String status=transferUTF8.UTF8toC(value.getStatus());
            if(Objects.equals(status, "已购买")){
                bookDto.setState("已卖出");
            }
            else
                bookDto.setState(transferUTF8.UTF8toC(value.getStatus()));
            //图片路径
            String picture1;
            if(user2.getAvatar()==null||user2.getAvatar().isEmpty()){
                //默认图片
                picture1 = PicUtil.resizeImageToSize(FileDirector.AVATAR_URL,avatar_width,avatar_height);
                bookDto.setAvatar(picture1);
            }
            else{
                picture1 = PicUtil.resizeImageToSize(readFile.getAvatarPicture(user2.getPhone()),avatar_width,avatar_height);
                bookDto.setAvatar(picture1);
            }
            bookDto.setCount(value.getOrdersNum());
            bidList.add(bookDto);
        }
        res=new Result(ResultCode.SUCCESS, bidList);
        return res;
    }

    @Override
    public Result bookingByPhone(String phone, String number) {
        List<Booking> bookLst= accountRepository.findByProductIdAndSellerId(number,phone);
        List<BookDetails> details=new ArrayList<>();
        for (Booking booking : bookLst) {
            BookDetails bookDto = new BookDetails();
            User user2;
            user2 = userRepository.findByPhone(booking.getBuyerId()).get();
            bookDto.setPhone(user2.getPhone());
            bookDto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
            bookDto.setNumber(number);
            bookDto.setState(transferUTF8.UTF8toC(booking.getStatus()));
            bookDto.setCount(booking.getOrdersNum());
            //图片路径
            String picture1;
            if (user2.getAvatar() == null || user2.getAvatar().isEmpty()) {
                //默认图片
                picture1 = PicUtil.resizeImageToSize(FileDirector.AVATAR_URL, avatar_width, avatar_height);
                bookDto.setAvatar(picture1);
            } else {
                picture1 = PicUtil.resizeImageToSize(readFile.getAvatarPicture(user2.getPhone()), avatar_width, avatar_height);
                bookDto.setAvatar(picture1);
            }
            details.add(bookDto);
        }
        res=new Result(ResultCode.SUCCESS, details);
        return res;
    }

    @Override
    public Result bookingByBuyer(String phone, String number) {
        List<Booking> bookLst= accountRepository.findByProductIdAndBuyerId(number,phone);
        List<BookDetails> details=new ArrayList<>();
        for (Booking booking : bookLst) {
            BookDetails bookDto = new BookDetails();
            User user2;
            user2 = userRepository.findByPhone(booking.getBuyerId()).get();
            bookDto.setPhone(user2.getPhone());
            bookDto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
            bookDto.setNumber(number);
            bookDto.setState(transferUTF8.UTF8toC(booking.getStatus()));
            bookDto.setCount(booking.getOrdersNum());
            //图片路径
            String picture1;
            if (user2.getAvatar() == null || user2.getAvatar().isEmpty()) {
                //默认图片
                picture1 = PicUtil.resizeImageToSize(FileDirector.AVATAR_URL, avatar_width, avatar_height);
                bookDto.setAvatar(picture1);
            } else {
                picture1 = PicUtil.resizeImageToSize(readFile.getAvatarPicture(user2.getPhone()), avatar_width, avatar_height);
                bookDto.setAvatar(picture1);
            }
            details.add(bookDto);
        }
        res=new Result(ResultCode.SUCCESS, details);
        return res;
    }

    @Override
    public Result bookingBuyer(String phone, String number) {
        List<Booking> bookLst= accountRepository.findByProductIdAndBuyerId(number,phone);
        if(bookLst.size()>0){
            res=new Result(ResultCode.SUCCESS, true);
        }
        else{
            res=new Result(ResultCode.SUCCESS, false);
        }
        return res;
    }

    @Override
    public Result orderByBuyer(String phone, String number) {
        List<ExchangeInfo> exLst= exRepository.findByProductNum(number);
        List<Buyer> buyers=new ArrayList<>();
        for (ExchangeInfo exchangeInfo : exLst) {
            String num = exchangeInfo.getNumber();
            List<Buyer> buyers1 = buy.findByExchangeIdAndPhone(num, phone);
            buyers.addAll(buyers1);
        }
        if(buyers.size()>0){
            res=new Result("666", "已购买",exLst.size());
        }
        else
            res=new Result(ResultCode.SUCCESS, "未购买");
        return res;
    }

    @Override
    public Result sellerDeleteBooking(String number) {
        Booking book;
        book=accountRepository.findByNumber(number);
        if(book.getIsDelete()==2){
            accountRepository.delete(book);
        }
        else{
            book.setIsDelete(1);
        }
        return res;
    }

    @Override
    public Result buyerDeleteBooking(String number) {
        Booking book;
        book=accountRepository.findByNumber(number);
        if(book.getIsDelete()==1){
            accountRepository.delete(book);
        }
        else{
            book.setIsDelete(2);
        }
        return res;
    }

    @Override
    public Result acquireAppointments(String number) {
        Booking bo=accountRepository.findByNumber(number);
        bo.setStatus(transferUTF8.CtoUTF8("待下单"));
        bo.setConfirmTime(new Date());
        accountRepository.save(bo);

        //修改商品状态
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", bo.getProductId());
        Product product = productDao.selectOne(productWrapper);
        if(product.getStorage()==0){
            res=new Result(ResultCode.FAIL);
        }else{
            Integer c= product.getStorage() - bo.getOrdersNum();
            product.setStorage(c);
            if(c==0){
                product.setStatus(4);
            }
            productDao.updateById(product);

            res=new Result(ResultCode.SUCCESS,bo);
        }
        return res;

    }

    @Override
    public Result allAllDetails(String number) {
        Buyer bu=buy.findByExchangeId(number);
        Seller se=sel.findByExchangeId(number);
        ExchangeInfo bidList=exRepository.findByNumber(number).get();
        OrderDetails bookDto=new OrderDetails();
        bookDto.setDiscount(bidList.getDiscounts());
        if(bidList.getDeliveryId()!=null){
            bookDto.setDeliveryId(bidList.getDeliveryId());
        }
        bookDto.setDelivery(transferUTF8.UTF8toC(bidList.getExpressDelivery()));
        bookDto.setPay(transferUTF8.UTF8toC(bidList.getPayment()));
        bookDto.setNumber(number);
        String address=transferUTF8.UTF8toC(bu.getAddress());
        String[] address_parts = address.split("%");
        bookDto.setConsignee(address_parts[0]);
        bookDto.setPhone(address_parts[1]);
        String add=address_parts[2]+address_parts[3];
        bookDto.setAddress(add);
        bookDto.setTotal(bidList.getPrice());
        if(bu.getReceiptTime()!=null){
            bookDto.setConfirmTime(bu.getReceiptTime());
        }
        if(bu.getRefundTime()!=null){
            bookDto.setApplyTime(bu.getRefundTime());
        }
        if(se.getShippingTime()!=null){
            bookDto.setDeliveryTime(se.getShippingTime());
        }
        if(bidList.getBuildTime()!=null){
            bookDto.setPayTime(bidList.getBuildTime());
        }
        if(se.getRefundTime()!=null){
            bookDto.setRefundTime(se.getRefundTime());
        }
        String remark=transferUTF8.UTF8toC(bu.getRemark());
        if(Objects.equals(remark, "待退款")){
            String[] remark_parts = remark.split("#");
            bookDto.setRemark(remark_parts[1]+","+remark_parts[2]);
        }
        if(remark!=null){
            bookDto.setRemark(transferUTF8.UTF8toC(bu.getRemark()));
        }
        res=new Result(ResultCode.SUCCESS, bookDto);
        return res;
    }
}
