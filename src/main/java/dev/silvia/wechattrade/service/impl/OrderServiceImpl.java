package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.NotificationDao;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dto.booking.BoReDto;
import dev.silvia.wechattrade.dto.booking.BookDetails;
import dev.silvia.wechattrade.dto.booking.BookingDto;
import dev.silvia.wechattrade.dto.exchangedto.*;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.*;
import dev.silvia.wechattrade.handlers.OrderCodeUtils;
import dev.silvia.wechattrade.handlers.Packing.OrderNotePacking;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<ProductDao, Product> implements IOrderService {
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
    private JdbcTemplate jdbcTemplate;
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

    @Autowired
    NotificationDao notificationDao;

    private final String ph = "^[1][3578]\\d{9}$";

    @Override
    public Result getSellerInfo(String number) {
        //??????????????????
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", number);
        Product product = productDao.selectOne(productWrapper);
        User user4=transferUTF8.switchUtf8Tc(userRepository.findByPhone(product.getSPhone()).get());
        //????????????
        String picture1;
        if(user4.getAvatar()==null||user4.getAvatar().isEmpty()){
            //????????????
            picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
            user4.setAvatar(picture1);
            res=new Result("666","jpg",user4);
        }
        else{
            if(readFile.getUserAvatarPic(user4.getPhone())!=null){   // ????????????????????????????????????
                Map<String, Object> map = readFile.getUserAvatarPic(user4.getPhone());
                user4.setAvatar(map.get("picture").toString());
                res=new Result("666",map.get("format").toString(),user4);
            }else {
                res=new Result("666",null,user4);
            }
        }
        return res;
    }

    @Override
    public Result build(ExRequestDto request) {
        //???????????????   ?????????????????????
        Booking bo=accountRepository.findByNumber(request.getBookNum());

        //??????????????????
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number",bo.getProductId());
        Product pro = productDao.selectOne(productWrapper);
        if(pro.getStatus()==0){
            pro.setStatus(5);
        }
        productDao.updateById(pro);
        ExchangeInfo ex=new ExchangeInfo();

        //???????????????
        String pronum=OrderCodeUtils.createCode("DT");
        ex.setNumber(pronum);
        ex.setOrdersNum(bo.getOrdersNum());
        ex.setPrice(request.getPrice());
        ex.setPayment(transferUTF8.CtoUTF8(request.getPayment()));
        Date date=new Date();
        ex.setBuildTime(date);
        ex.setProductNum(bo.getProductId());
        ex.setName(pro.getName());
        ex.setExpressDelivery(transferUTF8.CtoUTF8(request.getExpressDelivery()));
        ex.setFreight(6.00);
        ex.setDiscounts(request.getDiscounts());
        ex.setIsDelete(0);
        if(Objects.equals(request.getExpressDelivery(), "??????")){
            ex.setStatus(transferUTF8.CtoUTF8("?????????"));
        }
        else{
            ex.setStatus(transferUTF8.CtoUTF8("?????????"));
        }
        exRepository.save(ex);

        //???????????????
        Buyer buyer=new Buyer();
        String address=request.getMyAddress().getReceiverPhone()+"%"
                +request.getMyAddress().getReceiverName()+"%"
                +request.getMyAddress().getRegion()+"%"
                +request.getMyAddress().getAddressDetail();
        buyer.setAddress(transferUTF8.CtoUTF8(address));
        buyer.setRemark(transferUTF8.CtoUTF8(request.getRemark()));
        buyer.setExchangeId(pronum);  //??????
        buyer.setPhone(bo.getBuyerId());
        buy.save(buyer);

        //???????????????
        Seller seller=new Seller();
        seller.setAddress(transferUTF8.CtoUTF8(pro.getAddress()));
        if(Objects.equals(request.getExpressDelivery(), "??????")){
            seller.setShippingTime(date);
        }
        seller.setExchangeId(pronum);  //??????
        seller.setPhone(pro.getSPhone());
        sel.save(seller);

        //?????????????????????
        accountRepository.delete(bo);
        Notification notification= new OrderNotePacking().deliverAwait(seller.getPhone(),buyer.getPhone(),pro,bo);
        notificationDao.insert(notification);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result toBeReceived(String number, String deliveryId) {
        //??????
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);
        ex.setDeliveryId(deliveryId);
        ex.setStatus(transferUTF8.CtoUTF8("?????????"));
        exRepository.save(ex);

        //??????
        Seller seller;
        seller=sel.findByExchangeId(number);
        seller.setShippingTime(new Date());
        sel.save(seller);

        System.out.println("??????"+seller.getPhone()+"??????");
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result received(String number) {
        //??????
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);
        ex.setStatus(transferUTF8.CtoUTF8("?????????"));
        exRepository.save(ex);
        //??????
        Buyer buyer=buy.findByExchangeId(number);
        buyer.setReceiptTime(new Date());
        buy.save(buyer);
        System.out.println("??????"+buyer.getPhone()+"??????");
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result after(RefundDto refundDto) {
        //??????
        ExchangeInfo ex;
        ex=exRepository.findByNumber(refundDto.getNumber());
        ex.setStatus(transferUTF8.CtoUTF8("?????????"));
        exRepository.save(ex);

        //??????
        Buyer buyer=buy.findByExchangeId(refundDto.getNumber());
        String remark=refundDto.getState()+"%"+
                refundDto.getRefundRequest().getGoodsState()+"%"+
                refundDto.getRefundRequest().getRefundReason();
        if(refundDto.getRefundRequest().getDescription()==null||refundDto.getRefundRequest().getDescription().isEmpty()){
            remark=remark+"%"+"???";
        }
        else
            remark=remark+"%"+refundDto.getRefundRequest().getDescription();
        buyer.setRemark(transferUTF8.CtoUTF8(remark));
        buyer.setRefundTime(new Date());
        buy.save(buyer);


        Seller seller=sel.findByExchangeId(refundDto.getNumber());
        System.out.println("??????"+buyer.getPhone()+"??????");
        Notification notification= new OrderNotePacking().refundRequest(seller.getPhone(),buyer.getPhone(),ex.getName(),ex.getNumber());
        notificationDao.insert(notification);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result sellerAfter(String number) {
        //??????????????????
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);
        ex.setStatus(transferUTF8.CtoUTF8("?????????"));
        exRepository.save(ex);

        //??????
        Seller seller;
        seller=sel.findByExchangeId(number);
        seller.setRefundTime(new Date());


        //??????????????????
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

        seller.setAddress(product.getAddress());
        sel.save(seller);

        Buyer buyer=buy.findByExchangeId(number);

        Notification notification= new OrderNotePacking().refundSuccess(seller.getPhone(),buyer.getPhone(),ex);
        notificationDao.insert(notification);
        System.out.println("??????"+seller.getPhone()+"????????????");
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result cancelAfter(String number) {
        //??????
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);
        //??????
        Seller seller=sel.findByExchangeId(number);
        //??????
        Buyer buyer=buy.findByExchangeId(number);
        String remark=transferUTF8.UTF8toC(buyer.getRemark());
        String[] remark_parts = remark.split("%");
        ex.setStatus(transferUTF8.CtoUTF8(remark_parts[0]));
        exRepository.save(ex);

        System.out.println("??????"+seller.getPhone()+"????????????");
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result noAfter(String number) {
        //??????
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);

        //??????
        Buyer buyer=buy.findByExchangeId(number);
        String remark=transferUTF8.UTF8toC(buyer.getRemark());
        String[] remark_parts = remark.split("%");
        ex.setStatus(transferUTF8.CtoUTF8(remark_parts[0]));
        exRepository.save(ex);

        Seller seller=sel.findByExchangeId(number);

        Notification notification= new OrderNotePacking().disagreeRefund(seller.getPhone(),buyer.getPhone(),ex.getName(),ex.getNumber());
        notificationDao.insert(notification);
        res=new Result(ResultCode.SUCCESS,remark_parts[0]);
        return res;
    }

    @Override
    public Result afterReason(String number) {
        //??????
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);

        AfterReason  reason=new AfterReason();
        //??????
        Buyer buyer=buy.findByExchangeId(number);
        String remark=transferUTF8.UTF8toC(buyer.getRemark());
        String[] remark_parts = remark.split("%");
        reason.setState(remark_parts[0]);
        reason.setGoodsState(remark_parts[1]);
        reason.setRefundReason(remark_parts[2]);
        reason.setDescription(remark_parts[3]);

        reason.setTotal(ex.getPrice());
        reason.setRefundTime(sdf.format(buyer.getRefundTime()));
        res=new Result(ResultCode.SUCCESS,reason);
        return res;
    }

    @Override
    public Result delete(String number) {
        exRepository.delete(exRepository.findByNumber(number));
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result sellerDelete(String number) {
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);
        System.out.println(ex);
        System.out.println(number);
        if(ex.getIsDelete()==2){
            exRepository.delete(ex);
        }
        else{
            ex.setIsDelete(1);
            exRepository.save(ex);
        }
        sel.delete(sel.findByExchangeId(number));
        System.out.println("??????????????????");
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result buyerDelete(String number) {
        ExchangeInfo ex;
        ex=exRepository.findByNumber(number);
        if(ex.getIsDelete()==1){
            exRepository.delete(ex);
        }
        else{
            ex.setIsDelete(2);
            exRepository.save(ex);
        }
        buy.delete(buy.findByExchangeId(number));
        System.out.println("??????????????????");
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result selectAllOrder(String status) {
        List<Seller> sell=sel.findAll();
        List<ExchangeDto> bookLst=new ArrayList<>();
        ProductDetailVo pro;
        ExchangeInfo ex;
        for (Seller value : sell) {

            //??????????????????
            if (Objects.equals(status, "??????")) {
                ex = exRepository.findByNumber(value.getExchangeId());
            } else {
                ex = exRepository.findByNumberAndStatus(value.getExchangeId(), transferUTF8.CtoUTF8(status));
            }
            //??????????????????
            pro = service.getProductDetail(ex.getProductNum());
            ExchangeDto bookDto=new ExchangeDto();

            bookDto.setName(pro.getName());
            bookDto.setState(transferUTF8.UTF8toC(ex.getStatus()));
            bookDto.setCount(ex.getOrdersNum());
            //????????????
            String path = "";
//            if (pro.getPicture_count() == 0) {
//                bookDto.setCoverPic(path);
//            } else {
//                List<String> pictures = readFile.getPicturesBase64(ex.getProductNum(), pro.getPictures().size());
//                bookDto.setCoverPic(pictures.get(0));
//            }
            //????????????
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
        List<Seller> sell=sel.findByPhone(phone);
        List<ExchangeDto> bookLst=new ArrayList<>();
        for (Seller value : sell) {
            //??????????????????
            ExchangeInfo ex;
            if (Objects.equals(status, "??????")) {
                ex = exRepository.findByNumber(value.getExchangeId());
            } else {
                ex = exRepository.findByNumberAndStatus(value.getExchangeId(), transferUTF8.CtoUTF8(status));
            }
            //??????????????????
            if(ex!=null&&ex.getIsDelete()!=1){
                //??????????????????
                //??????????????????
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",ex.getProductNum());
                Product pro = productDao.selectOne(productWrapper);
                ExchangeDto bookDto=new ExchangeDto();

                //????????????
                String path = "";
                if(pro.getPicture() > 0){ // ????????????????????????????????????????????????????????????
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
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result selectBuyerOrder(String phone,String status) {
        //????????????????????????
        List<Buyer> bu=buy.findByPhone(phone);
        List<ExchangeDto> bookLst=new ArrayList<>();
        for (Buyer value : bu) {
            //??????????????????
            ExchangeInfo ex;
            if (Objects.equals(status, "??????")) {
                ex = exRepository.findByNumber(value.getExchangeId());
            } else {
                ex = exRepository.findByNumberAndStatus(value.getExchangeId(), transferUTF8.CtoUTF8(status));
            }
            if(ex!=null&&ex.getIsDelete()!=2){
                //??????????????????
                //??????????????????
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",ex.getProductNum());
                Product pro = productDao.selectOne(productWrapper);
                ExchangeDto bookDto=new ExchangeDto();

                //????????????
                String path = "";
                if(pro.getPicture() > 0){ // ????????????????????????????????????????????????????????????
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
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result addAppointments(BoReDto request) {
        String proNum=OrderCodeUtils.createCode("YY");
        Booking booking =new Booking();
        booking.setStatus(transferUTF8.CtoUTF8("?????????"));
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
        Notification notification= new OrderNotePacking().newOrder(booking.getSellerId(),booking.getBuyerId(),booking.getName(),booking);
        notificationDao.insert(notification);

        //??????????????????
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", booking.getProductId());
        Product product = productDao.selectOne(productWrapper);
        product.setStatus(5);
        productDao.updateById(product);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result deleteAppointments(String number) {
        Booking book=accountRepository.findByNumber(number);
        if(Objects.equals(book.getStatus(), transferUTF8.CtoUTF8("?????????"))){
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
        if(Objects.equals(book.getStatus(), transferUTF8.CtoUTF8("?????????"))){
            if(isbuyer==1){
                accountRepository.delete(book);
            }
            else{
                book.setIsDelete(1);
                book.setStatus(transferUTF8.CtoUTF8("?????????"));
                accountRepository.save(book);
            }
            res=new Result(ResultCode.SUCCESS);
        }
        else if(Objects.equals(book.getStatus(), transferUTF8.CtoUTF8("?????????"))){
            if(isbuyer==1){
                //??????????????????
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
                book.setIsDelete(1);
                book.setStatus(transferUTF8.CtoUTF8("?????????"));
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
        //????????????????????????
        List<BookingDto> bookLst=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "??????")){
            bidList=accountRepository.findByBuyerId(phone);
        }
        else{
            bidList=accountRepository.findByBuyerIdAndStatus(phone,transferUTF8.CtoUTF8(status));
        }
        for (Booking booking : bidList) {

            //??????????????????
            //??????????????????
            if(booking.getIsDelete()!=2){
                QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                productWrapper.eq("number",booking.getProductId());
                Product pro = productDao.selectOne(productWrapper);
                BookingDto bookDto=new BookingDto();
                //????????????
                String path = " ";
                if(pro.getPicture() > 0){ // ????????????????????????????????????????????????????????????
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
    public Result selectAllSeller(String phone, String status) {
        //????????????????????????
        List<BookingDto> bookLst=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "??????")){
            bidList=accountRepository.findBySellerId(phone);
        }
        else{
            bidList=accountRepository.findBySellerIdAndStatus(phone,transferUTF8.CtoUTF8(status));
        }
        for (Booking booking : bidList) {
            if (!Objects.equals(transferUTF8.UTF8toC(booking.getStatus()), "?????????")) {
                BookingDto bookDto=new BookingDto();
                //??????????????????
                //??????????????????
                if(booking.getIsDelete()!=1){
                    QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                    productWrapper.eq("number",booking.getProductId());
                    Product pro = productDao.selectOne(productWrapper);

                    //????????????
                    String path = "";
                    if(pro.getPicture() > 0){ // ????????????????????????????????????????????????????????????
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
        }
        res=new Result(ResultCode.SUCCESS, bookLst);
        return res;
    }

    @Override
    public Result selectAll(String status) {
        List<BookingDto> bookLst=new ArrayList<>();
        List<Booking> bidList;
        if(Objects.equals(status, "??????")){
            bidList=accountRepository.findByStatus(transferUTF8.CtoUTF8(status));
        }
        else{
            bidList=accountRepository.findAll();
        }
        ProductDetailVo pro;
        for (Booking booking : bidList) {
            //??????????????????
            BookingDto bookDto=new BookingDto();
            pro = service.getProductDetail(booking.getProductId());

            bookDto.setName(pro.getName());
            bookDto.setState(transferUTF8.UTF8toC(booking.getStatus()));
            //????????????
//            String path = "";
//            if (pro.getPicture_count() == 0) {
//                bookDto.setCoverPic(path);
//            } else {
//                List<String> pictures = readFile.getPicturesBase64(booking.getProductId(), pro.getPictures().size());
//                bookDto.setCoverPic(pictures.get(0));
//            }
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
    public Result selectAllByName(String name, String phone, Integer type, Integer isbuyer) {
        name=transferUTF8.CtoUTF8(name);
        if(type==1){
            //????????????
            String sql;
            List<ExchangeInfo> ei;
            if(isbuyer==1){
                sql="select * from exchange_info a inner" +
                        " join buyer_info b on a.number=b.exchange_id where a.name like '%" + name+ "%' and b.phone = '" + phone+ "'";
            }
            else{
                sql="select * from exchange_info a inner " +
                        "join seller_info b on a.number=b.exchange_id where a.name like '%" + name+ "%' and b.phone = '" + phone+ "'";;
            }
            ei= jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(ExchangeInfo.class));
            List<ExchangeDto> bidList = new ArrayList<>();
            for (ExchangeInfo booking : ei) {
                if((isbuyer==1&&booking.getIsDelete()!=2)
                    ||(isbuyer==0&&booking.getIsDelete()!=1)){
                    ExchangeDto ed=new ExchangeDto();
                    //??????????????????
                    //??????????????????
                    QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                    productWrapper.eq("number",booking.getProductNum());
                    Product pro = productDao.selectOne(productWrapper);

                    //????????????
                    String path = "";
                    if(pro.getPicture() > 0){ // ????????????????????????????????????????????????????????????
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
            //????????????
            List<Booking> bi;
            if(isbuyer==1){
                bi=accountRepository.findByNameLikeAndBuyerId("%"+name+"%",phone);
            }
            else{
                bi=accountRepository.findByNameLikeAndSellerId("%"+name+"%",phone);
            }
            List<BookingDto> bidList = new ArrayList<>();

            for (Booking booking : bi) {
                if((isbuyer==1&&booking.getIsDelete()!=2)
                        ||(isbuyer==0&&booking.getIsDelete()!=1)) {
                    //??????????????????
                    QueryWrapper<Product> productWrapper = new QueryWrapper<>();
                    productWrapper.eq("number",booking.getProductId());
                    Product pro = productDao.selectOne(productWrapper);
                    BookingDto bok=new BookingDto();

                    //????????????
                    String path = "";
                    if(pro.getPicture() > 0){ // ????????????????????????????????????????????????????????????
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
        if(Objects.equals(state, "??????")){
            bookLst=accountRepository.findByProductId(number);
            exList=exRepository.findByProductNum(number);
        }
        if(Objects.equals(state, "?????????")){
            bookLst=accountRepository.findByProductIdAndStatus(number,transferUTF8.CtoUTF8("?????????"));
        }
        if(Objects.equals(state, "?????????")){
            exList=exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8("?????????"));
        }
        if(Objects.equals(state, "?????????")||Objects.equals(state, "?????????")){
            exList=exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8(state));
        }
        if(Objects.equals(state, "??????")){
            bookLst=accountRepository.findByProductIdAndStatus(number,transferUTF8.CtoUTF8("?????????"));
            exList=exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8("?????????"));
            exList.addAll(exRepository.findByProductNumAndStatus(number,transferUTF8.CtoUTF8("?????????")));
        }
        List<BookDetails> bidList=new ArrayList<>();
        for (Booking value : bookLst) {
            if(!Objects.equals(value.getStatus(), transferUTF8.CtoUTF8("?????????"))){
                BookDetails bookDto=new BookDetails();
                User user2=userRepository.findByPhone(value.getBuyerId()).get();
                bookDto.setPhone(user2.getPhone());
                bookDto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
                bookDto.setNumber(value.getNumber());
                String status=transferUTF8.UTF8toC(value.getStatus());
                if(Objects.equals(status, "?????????")){
                    bookDto.setState("?????????");
                }
                else
                    bookDto.setState(transferUTF8.UTF8toC(value.getStatus()));
                //????????????
                if(user2.getAvatar()==null||user2.getAvatar().isEmpty()){
                    //????????????
                    if(readFile.getDefaultAvatarPic()!=null){   // ????????????????????????????????????
                        Map<String, Object> map = readFile.getDefaultAvatarPic();
                        bookDto.setTypes(map.get("format").toString());
                        bookDto.setAvatar(map.get("picture").toString());
                    }
                }
                else{
                    if(readFile.getUserAvatarPic(user2.getPhone())!=null){   // ????????????????????????????????????
                        Map<String, Object> map = readFile.getUserAvatarPic(user2.getPhone());
                        bookDto.setTypes(map.get("format").toString());
                        bookDto.setAvatar(map.get("picture").toString());
                    }
                }
                bookDto.setCount(value.getOrdersNum());
                bidList.add(bookDto);
            }
        }
        for (ExchangeInfo value : exList) {
            BookDetails bookDto=new BookDetails();
            Buyer buyer=buy.findByExchangeId(value.getNumber());
            User user2=userRepository.findByPhone(buyer.getPhone()).get();
            bookDto.setPhone(user2.getPhone());
            bookDto.setNickName(transferUTF8.UTF8toC(user2.getUserName()));
            bookDto.setNumber(value.getNumber());
            String status=transferUTF8.UTF8toC(value.getStatus());
            if(Objects.equals(status, "?????????")){
                bookDto.setState("?????????");
            }
            else
                bookDto.setState(transferUTF8.UTF8toC(value.getStatus()));
            //????????????
            String picture1;
            if(user2.getAvatar()==null||user2.getAvatar().isEmpty()){
                //????????????
                picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                bookDto.setAvatar(picture1);
            }
            else{
                picture1 = readFile.readAvatarPicture(user2.getPhone());
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
            //????????????
            String picture1;
            if (user2.getAvatar() == null || user2.getAvatar().isEmpty()) {
                //????????????
                picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                bookDto.setAvatar(picture1);
            } else {
                picture1 = readFile.readAvatarPicture(user2.getPhone());
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
            //????????????
            String picture1;
            if (user2.getAvatar() == null || user2.getAvatar().isEmpty()) {
                //????????????
                picture1 = ReadFile.getBaseFile(FileDirector.AVATAR_URL);
                bookDto.setAvatar(picture1);
            } else {
                picture1 = readFile.getAvatarPicture(user2.getPhone());
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
            res=new Result("666", "?????????",exLst.size());
        }
        else
            res=new Result(ResultCode.SUCCESS, "?????????");
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
            accountRepository.save(book);
        }
        res=new Result(ResultCode.SUCCESS,true);
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
            accountRepository.save(book);
        }
        res=new Result(ResultCode.SUCCESS,true);
        return res;
    }

    @Override
    public Result orderProductPic(String number) {
        //??????????????????
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", number);
        Product pro = productDao.selectOne(productWrapper);
        if(pro.getPicture() > 0 && readFile.getProductCoverPic(pro.getNumber())!=null){   // ????????????????????????????????????
            Map<String, Object> map = readFile.getProductCoverPic(pro.getNumber());
            res=new Result("666",map.get("picture").toString(),map.get("format").toString());
        }else {
            res=new Result("666",null,null);
        }
        return res;
    }

    @Override
    public Result acquireAppointments(String number) {
        Booking bo=accountRepository.findByNumber(number);
        bo.setStatus(transferUTF8.CtoUTF8("?????????"));
        bo.setConfirmTime(new Date());
        accountRepository.save(bo);

        //??????????????????
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
        Notification notification= new OrderNotePacking().agreeOrder(bo.getSellerId(),bo.getBuyerId(),product);
        notificationDao.insert(notification);
        return res;

    }

    @Override
    public Result allAllDetails(String number) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Buyer bu=buy.findByExchangeId(number);
        Seller se=sel.findByExchangeId(number);
        ExchangeInfo bidList=exRepository.findByNumber(number);
        if(bu!=null&&se!=null&&bidList!=null){
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
            bookDto.setConsignee(address_parts[1]);
            bookDto.setPhone(address_parts[0]);
            String add=address_parts[2]+address_parts[3];
            bookDto.setAddress(add);
            bookDto.setTotal(bidList.getPrice());
            if(bu.getReceiptTime()!=null){
                bookDto.setConfirmTime(sdf.format(bu.getReceiptTime()));
            }
            if(bu.getRefundTime()!=null){
                bookDto.setApplyTime(sdf.format(bu.getRefundTime()));
            }
            if(se.getShippingTime()!=null){
                bookDto.setDeliveryTime(sdf.format(se.getShippingTime()));
            }
            if(bidList.getBuildTime()!=null){
                bookDto.setPayTime(sdf.format(bidList.getBuildTime()));
            }
            if(se.getRefundTime()!=null){
                bookDto.setRefundTime(sdf.format(se.getRefundTime()));
            }
            String remark=transferUTF8.UTF8toC(bu.getRemark());
            if(Objects.equals(remark, "?????????")){
                String[] remark_parts = remark.split("%");
                bookDto.setRemark(remark_parts[1]+","+remark_parts[2]);
            }
            if(remark!=null){
                bookDto.setRemark(transferUTF8.UTF8toC(bu.getRemark()));
            }
            res=new Result(ResultCode.SUCCESS, bookDto);
        }
        else
            res=new Result(ResultCode.SUCCESS);
        return res;
    }
}
