package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.dao.UserDao;
import dev.silvia.wechattrade.dto.exchangedto.BoReDto;
import dev.silvia.wechattrade.dto.exchangedto.BookingDto;
import dev.silvia.wechattrade.dto.exchangedto.ExRequestDto;
import dev.silvia.wechattrade.dto.exchangedto.ExchangeDto;
import dev.silvia.wechattrade.dto.response.Result;
import dev.silvia.wechattrade.dto.response.ResultCode;
import dev.silvia.wechattrade.entity.*;
import dev.silvia.wechattrade.handlers.OrderCodeUtils;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.common.repository.*;
import dev.silvia.wechattrade.handlers.fileHandlers.ReadFile;
import dev.silvia.wechattrade.service.IOrderService;
import dev.silvia.wechattrade.service.IProductService;
import dev.silvia.wechattrade.service.IRegisterService;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService extends ServiceImpl<UserDao, User> implements IOrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRespository accountRepository;
    @Autowired
    private ExchangeInfoRespository exRepository;
    @Autowired
    private BuyerRepository buy;
    @Autowired
    private SellerRespository sel;
    @Resource
    private IRegisterService reser;
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
    private BookingDto bto;
    private ExchangeInfo ex;
    private ExRequestDto exdto;
    private Seller seller;
    private Buyer buyer;

    @Autowired
    private ProductDao productDao;

    @Override
    public Result getsellerinfo(String number) {
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", number);
        Product product = productDao.selectOne(productWrapper);
        User user4=userRepository.findByPhone(product.getSPhone()).get();
        user4.setUserName(transferUTF8.UTF8toC(user4.getUserName()));
        res=new Result(ResultCode.SUCCESS,user4);
        return res;
    }

    @Override
    public Result build(ExRequestDto request) {
        //获取商品信息
        ProductDetailVo pro = service.getProductDetail(request.getProjectnubmer());

        //订单信息表
        String pronum=OrderCodeUtils.createCode("DT");
        ex.setNumber(pronum);
        ex.setOrdersNum(request.getOrdersNum());
        ex.setPrice(request.getPrice());
        ex.setPayment(request.getPayment());
        ex.setBuildTime(new Date());
        ex.setProductNum(request.getProjectnubmer());
        ex.setExpressDelivery(transferUTF8.CtoUTF8(request.getExpressDelivery()));
        if(request.getExpressDelivery()=="自取"){
            ex.setStatus(0);
        }
        else{
            ex.setStatus(0);
        }
        exRepository.save(ex);

        //买家信息表
        buyer.setAddress(transferUTF8.CtoUTF8(request.getShippingaddre()));
        buyer.setRemark(transferUTF8.CtoUTF8(request.getRemark()));
        buyer.setExchangeId(pronum);  //外键
        buyer.setPhone(request.getBuyerphone());
        buy.save(buyer);

        //卖家信息表
        seller.setAddress(transferUTF8.CtoUTF8(pro.getAddress()));
        seller.setExchangeId(pronum);  //外键
        seller.setPhone(request.getSellerphone());
        sel.save(seller);

        //预约信息表
        Booking bo=accountRepository.findByProductId(request.getProjectnubmer());
        bo.setStatus(2);
        accountRepository.save(bo);

        res=new Result(ResultCode.SUCCESS,ex);
        return res;
    }

    @Override
    public Result tobereceived(String number,String deliveryId) {
        ex=exRepository.findByNumber(number).get();
        ex.setDeliveryId(deliveryId);
        ex.setStatus(1);
        exRepository.save(ex);

        seller=sel.findByExchangeId(number);
        seller.setShippingTime(new Date());
        sel.save(seller);

        Booking bo=accountRepository.findByNumber(number);
        bo.setStatus(3);
        accountRepository.save(bo);

        res=new Result(ResultCode.SUCCESS,ex);
        return res;
    }

    @Override
    public Result received(String number) {
        ex=exRepository.findByNumber(number).get();
        ex.setStatus(2);
        exRepository.save(ex);

        buyer=buy.findByExchangeId(number);
        buyer.setReceiptTime(new Date());
        buy.save(buyer);

        Booking bo=accountRepository.findByNumber(number);
        bo.setStatus(4);
        accountRepository.save(bo);

        res=new Result(ResultCode.SUCCESS,ex);
        return res;
    }

    @Override
    public Result after(String number) {
        ex=exRepository.findByNumber(number).get();
        ex.setStatus(3);
        exRepository.save(ex);

        buyer=buy.findByExchangeId(number);
        buyer.setRefundTime(new Date());
        buy.save(buyer);

        res=new Result(ResultCode.SUCCESS,ex);
        return res;
    }

    @Override
    public Result sellerafter(String number,Integer count) {
        String pronum = OrderCodeUtils.createCode("TK");
        ex=exRepository.findByNumber(number).get();
        ex.setNumber(pronum);
        ex.setStatus(4);
        exRepository.save(ex);

        seller=sel.findByExchangeId(number);
        seller.setRefundTime(new Date());
        sel.save(seller);

        Booking bo=accountRepository.findByNumber(number);
        bo.setStatus(6);
        accountRepository.save(bo);

        //修改商品状态
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number",ex.getProductNum());
        Product product = productDao.selectOne(productWrapper);
        Integer num=product.getStorage()+count;
        product.setStorage(num);
        productDao.updateById(product);

        res=new Result(ResultCode.SUCCESS,ex);
        return res;
    }


    @Override
    public Result delete(String number) {
        exRepository.deleteByNumber(number);
        res=new Result(ResultCode.SUCCESS);
        return res;
    }

    @Override
    public Result sellerdelete(String number) {
        ex=exRepository.findByNumber(number).get();
        if(ex.getStatus()==4){
            sel.deleteByExchangeId(number);
            res=new Result(ResultCode.SUCCESS);
        }
        else{
            res=new Result(ResultCode.FAIL);
        }
        return res;
    }

    @Override
    public Result buyerdelete(String number) {
        ex=exRepository.findByNumber(number).get();
        if(ex.getStatus()==4){
            buy.deleteByExchangeId(number);
            res=new Result(ResultCode.SUCCESS);
        }
        else{
            res=new Result(ResultCode.FAIL);
        }
        return res;
    }

    @Override
    public Result selectallorder() {
        return null;
    }

    @Override
    public Result selectsellerorder(String phone) {
        List<Seller> sell=sel.findAllByPhone(phone);


        res=new Result(ResultCode.SUCCESS,sell);
        return res;
    }

    @Override
    public Result selectbuyerorder(String phone) {
        List<Buyer> bu=buy.findAllByPhone(phone);


        res=new Result(ResultCode.SUCCESS,bu);
        return res;
    }


    @Override
    public Result addappointments(BoReDto request) {
        String pronum=OrderCodeUtils.createCode("YY");
        Booking booking =new Booking();
        booking.setStatus(0);
        booking.setAppointmentTime(new Date());
        booking.setProductId(request.getProductId());
        booking.setNumber(pronum);
        booking.setPrice(request.getPrice());
        booking.setOrdersNum(request.getOrdersNum());
        booking.setBuyerId(request.getBuyerId());
        booking.setSellerId(request.getSellerId());

        accountRepository.save(booking);
        res=new Result(ResultCode.SUCCESS, booking);
        return res;
    }

    @Override
    public Result deleteappointments(String number) {
        Booking book=accountRepository.findByNumber(number);
        if(book.getStatus()==4||book.getStatus()==5||book.getStatus()==6||book.getStatus()==7){
            accountRepository.deleteByNumber(number);
            res=new Result(ResultCode.SUCCESS);
        }
        else{
            res=new Result(ResultCode.FAIL);
        }
        return res;
    }

    @Override
    public Result cancelappointments(String number,Integer isbuyer) {
        Booking book=accountRepository.findByNumber(number);
        if(book.getStatus()==0){
            if(isbuyer==1){
                book.setStatus(5);
            }
            else{
                book.setStatus(7);
            }
            accountRepository.save(book);
            res=new Result(ResultCode.SUCCESS);
        }
        else{
            res=new Result(ResultCode.FAIL);
        }
        return res;
    }

    @Override
    public Result selectallbuyer(String phone) {
        //获取用户所有预约
        List<BookingDto> booklist=new ArrayList<>();
        BookingDto bookdto=new BookingDto();
        List<Booking> bidList=accountRepository.findByBuyerId(phone);
        ProductDetailVo pro;
        for(int i = 0; i< bidList.size(); i++){
            //获取产品编号
            //获取商品信息
            pro = service.getProductDetail(bidList.get(i).getProductId());

            bookdto.setName(transferUTF8.UTF8toC(pro.getName()));
            bookdto.setStatus(bidList.get(i).getStatus());
            //图片信息
            //图片信息
            String path="";
            if(pro.getPicture_count()==0){
                bookdto.setCoverPic(path);
            }
            else{
                List<String> pictures = readFile.getPicturesBase64(bidList.get(i).getProductId(), pro.getPictures().size());
                bookdto.setCoverPic(pictures.get(0));
            }

            bookdto.setPice(bidList.get(i).getPrice());
            bookdto.setNumber(bidList.get(i).getNumber());
            booklist.set(i,bookdto);
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }


    @Override
    public Result selectallseller(String phone) {
        //获取用户所有预约
        List<BookingDto> booklist=new ArrayList<>();
        BookingDto bookdto=new BookingDto();
        List<Booking> bidList=accountRepository.findBySellerId(phone);
        ProductDetailVo pro;
        for(int i = 0; i< bidList.size(); i++){
            //获取产品编号
            //获取商品信息
            pro = service.getProductDetail(bidList.get(i).getProductId());

            bookdto.setName(transferUTF8.UTF8toC(pro.getName()));
            bookdto.setStatus(bidList.get(i).getStatus());
            //图片信息
            String path="";
            if(pro.getPicture_count()==0){
                bookdto.setCoverPic(path);
            }
            else{
                List<String> pictures = readFile.getPicturesBase64(bidList.get(i).getProductId(), pro.getPictures().size());
                bookdto.setCoverPic(pictures.get(0));
            }

            bookdto.setPice(bidList.get(i).getPrice());
            bookdto.setNumber(bidList.get(i).getNumber());
            booklist.set(i,bookdto);
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }

    @Override
    public Result selectall() {
        List<BookingDto> booklist=new ArrayList<>();
        BookingDto bookdto=new BookingDto();
        List<Booking> bidList=accountRepository.findAll();
        ProductDetailVo pro;
        for(int i = 0; i< bidList.size(); i++){
            //获取商品信息
            pro = service.getProductDetail(bidList.get(i).getProductId());

            bookdto.setName(transferUTF8.UTF8toC(pro.getName()));
            bookdto.setStatus(bidList.get(i).getStatus());
            //图片信息
            String path="";
            if(pro.getPicture_count()==0){
                bookdto.setCoverPic(path);
            }
            else{
                List<String> pictures = readFile.getPicturesBase64(bidList.get(i).getProductId(), pro.getPictures().size());
                bookdto.setCoverPic(pictures.get(0));
            }
            bookdto.setPice(bidList.get(i).getPrice());
            bookdto.setNumber(bidList.get(i).getNumber());
            booklist.set(i,bookdto);
        }
        res=new Result(ResultCode.SUCCESS, booklist);
        return res;
    }


    @Override
    public Result acquireappointments(String number,int count) {
        Booking bo=accountRepository.findByNumber(number);
        bo.setStatus(1);
        bo.setConfirmTime(new Date());
        accountRepository.save(bo);

        //修改商品状态
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", number);
        Product product = productDao.selectOne(productWrapper);
        product.setStorage(product.getStorage()-count);
        if(product.getStorage()-count==0){
            product.setStatus(5);
        }
//      productDao.updateById(product);
        productDao.updateById(product);

        res=new Result(ResultCode.SUCCESS,bo);
        return res;
    }
}
