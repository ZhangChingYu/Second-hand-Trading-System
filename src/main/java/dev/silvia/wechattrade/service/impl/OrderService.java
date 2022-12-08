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
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.*;
import dev.silvia.wechattrade.handlers.OrderCodeUtils;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.repository.*;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.service.IOrderService;
import dev.silvia.wechattrade.service.IProductService;
import dev.silvia.wechattrade.service.IRegisterService;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderService extends ServiceImpl<ProductDao, Product> implements IOrderService {
    private String picture_url = FileDirector.PRODUCT_URL;
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
    private Optional<Booking> user;

    @Autowired
    private ReadFile readFile = new ReadFile();

    private List<ExchangeDto> addre=new ArrayList<>();
    private List<Booking> bid=new ArrayList<>();
    private List<ExchangeInfo> exd=new ArrayList<>();

    private Result res;
    private BookingDto bto=new BookingDto();
    private ExchangeInfo ex=new ExchangeInfo();
    private ExRequestDto exdto=new ExRequestDto();
    private Seller seller=new Seller();
    private Buyer buyer=new Buyer();

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
        List<String> picture1;
        if(user4.getAvatar().isEmpty()){
            //默认图片
            picture1 = Collections.singletonList(ReadFile.getBaseFile(FileDirector.AVATAR_URL));
            user4.setAvatar(picture1.get(0));
        }
        else{
            picture1= Collections.singletonList(ReadFile.getBaseFile(user4.getAvatar()));
            user4.setAvatar(picture1.get(0));
        }
        res=new Result(ResultCode.SUCCESS,user4);
        return res;
    }

    @Override
    public Result build(ExRequestDto request) {
        //获取商品信息
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number",request.getProjectNumber());
        Product pro = productDao.selectOne(productWrapper);
        if(pro.getStatus()==0){
            pro.setStatus(5);
        }
        productDao.updateById(pro);

        //订单信息表
        String pronum=OrderCodeUtils.createCode("DT");
        ex.setNumber(pronum);
        ex.setOrdersNum(request.getOrdersNum());
        ex.setPrice(request.getPrice());
        ex.setPayment(transferUTF8.CtoUTF8(request.getPayment()));
        ex.setBuildTime(new Date());
        ex.setProductNum(request.getProjectNumber());
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
        buyer.setAddress(transferUTF8.CtoUTF8(request.getShipping()));
       // buyer.setRemark(transferUTF8.CtoUTF8(request.getRemark()));
        buyer.setExchangeId(pronum);  //外键
        buyer.setPhone(request.getBuyerPhone());
        buy.save(buyer);

        //卖家信息表
        seller.setAddress(transferUTF8.CtoUTF8(pro.getAddress()));
        seller.setExchangeId(pronum);  //外键
        seller.setPhone(pro.getSPhone());
        sel.save(seller);

        //预约信息表   删除已下单预约
        Booking bo=accountRepository.findByNumber(request.getBookNum());
        accountRepository.delete(bo);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result toBeReceived(String number, String deliveryId) {
        //订单
        ex=exRepository.findByNumber(number).get();
        ex.setDeliveryId(deliveryId);
        ex.setStatus(transferUTF8.CtoUTF8("待收货"));
        exRepository.save(ex);

        //卖家
        seller=sel.findByExchangeId(number);
        seller.setShippingTime(new Date());
        sel.save(seller);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result received(String number) {
        //订单
        ex=exRepository.findByNumber(number).get();
        ex.setStatus(transferUTF8.CtoUTF8("已购买"));
        exRepository.save(ex);
        //卖方
        buyer=buy.findByExchangeId(number);
        buyer.setReceiptTime(new Date());
        buy.save(buyer);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result after(String number) {
        //订单
        ex=exRepository.findByNumber(number).get();
        ex.setStatus(transferUTF8.CtoUTF8("待退款"));
        exRepository.save(ex);

        //买家
        buyer=buy.findByExchangeId(number);
        buyer.setRefundTime(new Date());
        buy.save(buyer);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result sellerAfter(String number) {
        //订单状态修改
        String pronum = OrderCodeUtils.createCode("TK");
        ex=exRepository.findByNumber(number).get();
        ex.setNumber(pronum);
        ex.setStatus(transferUTF8.CtoUTF8("已退款"));
        exRepository.save(ex);

        //卖家
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

            product.setStatus(5);
        }
        productDao.updateById(product);

        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result cancelAfter(String number) {
        //订单
        ex=exRepository.findByNumber(number).get();
        //卖家
        seller=sel.findByExchangeId(number);
        //买家
        buyer=buy.findByExchangeId(number);
        if(buyer.getReceiptTime()==null){
            if(seller.getShippingTime()==null){
                ex.setStatus(transferUTF8.CtoUTF8("待发货"));
            }
            else
                ex.setStatus(transferUTF8.CtoUTF8("待收货"));
        }
        else
            ex.setStatus(transferUTF8.CtoUTF8("已购买"));
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
        ex=exRepository.findByNumber(number).get();
        if(ex.getIsDelete()==0){
            ex.setIsDelete(1);
        }
        else{
            exRepository.delete(ex);
        }
        sel.delete(sel.findByExchangeId(number));
        return res;
    }

    @Override
    public Result buyerDelete(String number) {
        ex=exRepository.findByNumber(number).get();
        if(ex.getIsDelete()==0){
            ex.setIsDelete(2);
        }
        else{
            exRepository.delete(ex);
        }
        buy.delete(buy.findByExchangeId(number));
        return res;
    }

    @Override
    public Result selectAllOrder(String status) {
        List<Seller> sell=sel.findAll();
        List<ExchangeDto> booklist=new ArrayList<>();
        ProductDetailVo pro;
        for (Seller value : sell) {
            //获取产品编号
            if (Objects.equals(status, "全部")) {
                ex = exRepository.findByNumber(value.getExchangeId()).get();
            } else {
                ex = exRepository.findByNumberAndStatus(value.getExchangeId(), transferUTF8.CtoUTF8(status));
            }
            //获取商品信息
            pro = service.getProductDetail(ex.getProductNum());
            ExchangeDto bookdto=new ExchangeDto();

            bookdto.setName(pro.getName());
            bookdto.setState(transferUTF8.UTF8toC(ex.getStatus()));
            bookdto.setCount(ex.getOrdersNum());
            //图片信息
            String path = "";
            if (pro.getPicture_count() == 0) {
                bookdto.setCoverPic(path);
            } else {
                List<String> pictures = readFile.getPicturesBase64(ex.getProductNum(), pro.getPictures().size());
                bookdto.setCoverPic(pictures.get(0));
            }
            //输出信息
            bookdto.setPrice(ex.getPrice());
            bookdto.setProNumber(ex.getProductNum());
            bookdto.setOrdNumber(ex.getNumber());
            booklist.add(bookdto);
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }

    @Override
    public Result selectSellerOrder(String phone,String status) {
        List<Seller> sell=sel.findAll();
        List<ExchangeDto> booklist=new ArrayList<>();

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

                    //图片信息
                    String path = "";
                    if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                        String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                        path= ReadFile.getBaseFile(url);
                    }
                    ExchangeDto bookdto=new ExchangeDto();

                    bookdto.setCoverPic(path);
                    bookdto.setCount(ex.getOrdersNum());
                    bookdto.setName(transferUTF8.UTF8toC(pro.getName()));
                    bookdto.setState(transferUTF8.UTF8toC(ex.getStatus()));
                    bookdto.setPrice(ex.getPrice());
                    bookdto.setProNumber(ex.getProductNum());
                    bookdto.setOrdNumber(ex.getNumber());
                    booklist.add(bookdto);
                }
            }
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }

    @Override
    public Result selectBuyerOrder(String phone,String status) {
        //获取用户所有商品
        List<Buyer> bu=buy.findAll();
        List<ExchangeDto> booklist=new ArrayList<>();
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

                    //图片信息
                    String path = "";
                    if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                        String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                        path= ReadFile.getBaseFile(url);
                    }
                    ExchangeDto bookdto=new ExchangeDto();
                    bookdto.setCoverPic(path);
                    bookdto.setCount(ex.getOrdersNum());
                    bookdto.setName(transferUTF8.UTF8toC(pro.getName()));
                    bookdto.setState(transferUTF8.UTF8toC(ex.getStatus()));
                    bookdto.setPrice(ex.getPrice());
                    bookdto.setProNumber(ex.getProductNum());
                    bookdto.setOrdNumber(ex.getNumber());
                    booklist.add(bookdto);
                }

            }
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }

    @Override
    public Result addAppointments(BoReDto request) {
        String pronum=OrderCodeUtils.createCode("YY");
        Booking booking =new Booking();
        booking.setStatus(transferUTF8.CtoUTF8("已预约"));
        booking.setAppointmentTime(new Date());
        booking.setProductId(request.getProductId());
        booking.setNumber(pronum);
        booking.setPrice(request.getPrice());
        booking.setOrdersNum(request.getOrdersNum());
        booking.setBuyerId(request.getBuyerId());
        booking.setSellerId(request.getSellerId());
        booking.setName(request.getProductName());
        accountRepository.save(booking);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result deleteAppointments(String number) {
        Booking book=accountRepository.findByNumber(number);
        if(Objects.equals(book.getStatus(), transferUTF8.UTF8toC("已拒绝"))){
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
        if(Objects.equals(book.getStatus(), transferUTF8.UTF8toC("已预约"))){
            if(isbuyer==1){
                accountRepository.delete(book);
            }
            else{
                book.setStatus(transferUTF8.CtoUTF8("已拒绝"));
                accountRepository.save(book);
            }
            res=new Result(ResultCode.SUCCESS);
        }
        if(Objects.equals(book.getStatus(), transferUTF8.UTF8toC("待下单"))){
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
        List<BookingDto> booklist=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "全部")){
            bidList=accountRepository.findByBuyerId(phone);
        }
        else{
            bidList=accountRepository.findByBuyerIdAndStatus(phone,transferUTF8.CtoUTF8(status));
        }
        for (Booking booking : bidList) {

            if (Objects.equals(booking.getBuyerId(), phone)&&!
                    Objects.equals(transferUTF8.UTF8toC(booking.getStatus()), "已拒绝")) {
                //获取商品图片
                //获取商品信息
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",ex.getProductNum());
                Product pro = productDao.selectOne(productWrapper);

                //图片信息
                String path = "";
                if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                    String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                    path= ReadFile.getBaseFile(url);
                }
                BookingDto bookdto=new BookingDto();
                bookdto.setCoverPic(path);
                bookdto.setCount(booking.getOrdersNum());
                bookdto.setName(transferUTF8.UTF8toC(pro.getName()));
                bookdto.setState(transferUTF8.UTF8toC(booking.getStatus()));
                bookdto.setBookNum(booking.getNumber());
                bookdto.setPrice(booking.getPrice());
                bookdto.setNumber(booking.getNumber());
                booklist.add(bookdto);
            }

        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }

    @Override
    public Result selectAllSeller(String phone, String status) {
        //获取用户所有预约
        List<BookingDto> booklist=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "全部")){
            bidList=accountRepository.findBySellerId(phone);
        }
        else{
            bidList=accountRepository.findBySellerIdAndStatus(phone,transferUTF8.CtoUTF8(status));
        }
        for (Booking booking : bidList) {
            if (Objects.equals(booking.getSellerId(), phone)) {
                BookingDto bookdto=new BookingDto();
                //获取商品图片
                //获取商品信息
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",ex.getProductNum());
                Product pro = productDao.selectOne(productWrapper);

                //图片信息
                String path = "";
                if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                    String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                    path= ReadFile.getBaseFile(url);
                }
                bookdto.setCoverPic(path);
                bookdto.setCount(booking.getOrdersNum());
                bookdto.setName(transferUTF8.UTF8toC(pro.getName()));
                bookdto.setState(transferUTF8.UTF8toC(booking.getStatus()));
                bookdto.setBookNum(booking.getNumber());
                bookdto.setPrice(booking.getPrice());
                bookdto.setNumber(booking.getNumber());
                booklist.add(bookdto);
            }
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }

    @Override
    public Result selectAll(String status) {
        List<BookingDto> booklist=new ArrayList<>();
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
            BookingDto bookdto=new BookingDto();
            pro = service.getProductDetail(booking.getProductId());

            bookdto.setName(pro.getName());
            bookdto.setState(transferUTF8.UTF8toC(booking.getStatus()));
            //图片信息
            String path = "";
            if (pro.getPicture_count() == 0) {
                bookdto.setCoverPic(path);
            } else {
                List<String> pictures = readFile.getPicturesBase64(booking.getProductId(), pro.getPictures().size());
                bookdto.setCoverPic(pictures.get(0));
            }
            bookdto.setCount(booking.getOrdersNum());
            bookdto.setBookNum(booking.getNumber());
            bookdto.setPrice(booking.getPrice());
            bookdto.setNumber(booking.getNumber());
            booklist.add(bookdto);
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }

    @Override
    public Result selectAllByName(String name, Integer type, Integer isbuyer) {
        if(type==1){

            //订单信息
            List<ExchangeInfo> ei;
            ei=exRepository.findByNameLike("%"+name+"%");
            List<ExchangeDto> bidList = new ArrayList<>();

            for (ExchangeInfo booking : ei) {
                ExchangeDto ed=new ExchangeDto();
                //获取商品图片
                //获取商品信息
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",ex.getProductNum());
                Product pro = productDao.selectOne(productWrapper);

                //图片信息
                String path = "";
                if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                    String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                    path= ReadFile.getBaseFile(url);
                }
                ed.setCoverPic(path);
                ed.setCount(booking.getOrdersNum());
                ed.setName(transferUTF8.UTF8toC(pro.getName()));
                ed.setState(transferUTF8.UTF8toC(booking.getStatus()));
                ed.setOrdNumber(booking.getNumber());
                ed.setPrice(booking.getPrice());
                ed.setProNumber(booking.getProductNum());
                bidList.add(ed);
            }
            res=new Result(ResultCode.SUCCESS, bidList);
            return res;
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
                //获取商品信息
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",ex.getProductNum());
                Product pro = productDao.selectOne(productWrapper);

                //图片信息
                String path = "";
                if(pro.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
                    String url = picture_url+pro.getCatalog()+"/"+pro.getNumber()+"/"+pro.getNumber()+"_0.jpg";
                    path= ReadFile.getBaseFile(url);
                }
                bok.setCoverPic(path);
                bok.setCount(booking.getOrdersNum());
                bok.setName(transferUTF8.UTF8toC(pro.getName()));
                bok.setState(transferUTF8.UTF8toC(booking.getStatus()));
                bok.setBookNum(booking.getNumber());
                bok.setPrice(booking.getPrice());
                bok.setNumber(booking.getProductId());
                bidList.add(bok);
            }
            res=new Result(ResultCode.SUCCESS, bidList);
            return res;
        }
    }

    @Override
    public Result sellerBookingByName(String number) {
        List<Booking> booklist= accountRepository.findByProductId(number);
        List<BookDetails> bidlist=new ArrayList<>();
        User user2;
        for (Booking value : booklist) {
            BookDetails bookdto=new BookDetails();
            user2=userRepository.findByPhone(value.getBuyerId()).get();
            bookdto.setPhone(user2.getPhone());
            bookdto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
            bookdto.setNumber(number);
      //      bookdto.setTime(value.getAppointmentTime());
            //图片路径
            List<String> picture1;
            if(bookdto.getAvatar().isEmpty()){
                //默认图片
                picture1 = Collections.singletonList(ReadFile.getBaseFile(FileDirector.AVATAR_URL));
                bookdto.setAvatar(picture1.get(0));
            }
            else{
                picture1= Collections.singletonList(ReadFile.getBaseFile(user2.getAvatar()));
                bookdto.setAvatar(picture1.get(0));
            }
            bidlist.add(bookdto);
        }
        res=new Result(ResultCode.SUCCESS, bidlist);
        return res;
    }

    @Override
    public Result bookingByPhone(String phone,String number) {
        Booking booklist= accountRepository.findByProductIdAndSellerId(number,phone);
        BookDetails bookdto=new BookDetails();

        User user2;
        user2=userRepository.findByPhone(booklist.getBuyerId()).get();
        bookdto.setPhone(user2.getPhone());
        bookdto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
        bookdto.setNumber(number);
       // bookdto.setTime(booklist.getAppointmentTime());
        //图片路径
        List<String> picture1;
        if(bookdto.getAvatar().isEmpty()){
            //默认图片
            picture1 = Collections.singletonList(ReadFile.getBaseFile(FileDirector.AVATAR_URL));
            bookdto.setAvatar(picture1.get(0));
        }
        else{
            picture1= Collections.singletonList(ReadFile.getBaseFile(user2.getAvatar()));
            bookdto.setAvatar(picture1.get(0));
        }

        res=new Result(ResultCode.SUCCESS, bookdto);
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

        Integer c= product.getStorage() - bo.getOrdersNum();
        System.out.println(c);
        product.setStorage(c);
        if(c==0){
            product.setStatus(4);
        }
        productDao.updateById(product);

        res=new Result(ResultCode.SUCCESS,bo);
        return res;
    }

    @Override
    public Result allAllDetails(String number) {
        Buyer bu=buy.findByExchangeId(number);
        Seller se=sel.findByExchangeId(number);
        ExchangeInfo bidList=exRepository.findByNumber(number).get();
        OrderDetails bookdto=new OrderDetails();
        bookdto.setDiscount(bidList.getDiscounts());
        if(!bidList.getDeliveryId().isEmpty()){
            bookdto.setDeliveryId(bidList.getDeliveryId());
        }
        bookdto.setDelivery(transferUTF8.UTF8toC(bidList.getExpressDelivery()));
        bookdto.setPay(transferUTF8.UTF8toC(bidList.getPayment()));
        bookdto.setNumber(number);
        String address=transferUTF8.UTF8toC(bu.getAddress());
        String[] address_parts = address.split("%");
        bookdto.setConsignee(address_parts[0]);
        bookdto.setPhone(address_parts[1]);
        String add=address_parts[1]+address_parts[3];
        bookdto.setAddress(add);
        bookdto.setTotal(bidList.getPrice());
        if(bu.getReceiptTime()!=null){
            bookdto.setConfirmTime(bu.getReceiptTime());
        }
        if(bu.getRefundTime()!=null){
            bookdto.setApplyTime(bu.getRefundTime());
        }
        if(se.getShippingTime()!=null){
            bookdto.setDeliveryTime(se.getShippingTime());
        }
        if(bidList.getBuildTime()!=null){
            bookdto.setPayTime(bidList.getBuildTime());
        }
        if(se.getRefundTime()!=null){
            bookdto.setRefundTime(se.getRefundTime());
        }
        res=new Result(ResultCode.SUCCESS, bookdto);
        return res;
    }
}
