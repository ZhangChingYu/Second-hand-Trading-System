package dev.silvia.wechattrade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import dev.silvia.wechattrade.dao.HotKeyDao;
import dev.silvia.wechattrade.dao.ProductDao;
import dev.silvia.wechattrade.entity.FavoriteInfo;
import dev.silvia.wechattrade.entity.HotKey;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.Packing.ProductPacking;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.keyword.HotKeyFilter;
import dev.silvia.wechattrade.handlers.keyword.KeyClearer;
import dev.silvia.wechattrade.handlers.keyword.SimilarityFilter;
import dev.silvia.wechattrade.service.IProductService;
import dev.silvia.wechattrade.vo.HotKeyVo;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private HotKeyDao hotKeyDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private HotKeyFilter hotKeyFilter;
    @Autowired
    private SimilarityFilter similarityFilter;
    @Autowired
    private KeyClearer keyClearer;
    @Autowired
    private TransferUTF8 transferUTF8 = new TransferUTF8();
    @Autowired
    private ProductPacking productPacking = new ProductPacking();
    @Autowired
    private ReadFile readFile = new ReadFile();


    @Override
    public ProductDetailVo getProductDetail(String number) {
        ProductDetailVo detail = new ProductDetailVo();
        // ??????product_manage???
        QueryWrapper<Product> productWrapper = new QueryWrapper<>();
        productWrapper.eq("number", number);
        Product product = productDao.selectOne(productWrapper);
        // ??????user???
        String sql = "select * from user_info where phone='"+product.getSPhone()+"'";
        User seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
        // ????????????????????????
        List<String> pictures = readFile.getProductPictures(product.getNumber(), product.getPicture());
        List<String> picturesFormat = readFile.getProductPicturesFormat(product.getNumber(), product.getPicture());
        String seller_pic = readFile.readAvatarPicture(seller.getPhone());
        detail = productPacking.ProductUserToDetail(product, seller, seller_pic, pictures, picturesFormat);
        return detail;
    }

    @Override
    public int ProductStatus(String number) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("number", number);
        Product product = productDao.selectOne(wrapper);
        return product.getStatus();
    }

    @Override
    public List<ProductOutlineVo> getProductByCatalog(String c_number) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog", c_number);
        wrapper.eq("status", 0);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineVo> productOutlines = productPacking.ProductToOutline(products);
        return productOutlines;
    }

    @Override
    public List<ProductOutlineVo> homepageProducts() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        //wrapper.between("id", 10, 20);
        wrapper.eq("status", 0);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineVo> productOutlines = productPacking.ProductToOutline(products);
        return productOutlines;
    }

    @Override
    public List<ProductOutlineVo> homepageProductOrder(String type) {
        List<ProductOutlineVo> products = homepageProducts();
        List<ProductOutlineVo> temp = new ArrayList<>();
        switch (type){
            case "all":
                return products;
            case "promote":
                return products;
            case "new":
                for(int i = products.size()-1; i >=0 ; i--){
                    temp.add(products.get(i));
                }
                return temp;
            case "like":
                QueryWrapper<Product> wrapper = new QueryWrapper<>();
                wrapper.orderByDesc("like_count");
                List<Product> product = productDao.selectList(wrapper);
                List<ProductOutlineVo> outline = productPacking.ProductToOutline(product);
                return outline;
        }
        return null;
    }

    @Override
    public List<ProductOutlineVo> homepageProductPromote(String number) {
        return null;
    }

    @Override
    public List<ProductOutlineVo> homepageProductNew() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.eq("status",0);
        List<Product> products = productDao.selectList(wrapper);
        return get10Outline(products);
    }

    @Override
    public List<ProductOutlineVo> homepageProductLike() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("like_count");
        wrapper.eq("status", 0);
        List<Product> products = productDao.selectList(wrapper);
        return get10Outline(products);
    }

    @Override
    public List<ProductOutlineVo> searchProductByKey(String keyword) {
        String key = transferUTF8.CtoUTF8(keyword);
        updateHotKey(key);  // ???key??????????????????????????????HotKey??????
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        wrapper.like("name", key);
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineVo> productOutlines = productPacking.ProductToOutline(products);
        return productOutlines;
    }

    @Override
    public List<HotKeyVo> showHotKey() {
        QueryWrapper<HotKey> wrapper = new QueryWrapper<>();
        List<HotKey> hotKeys = hotKeyDao.selectList(wrapper);
        if(hotKeys.isEmpty()){
            return null;
        }
        // ????????????HotKey?????????????????????????????????
        deleteHotKey(keyClearer.getClearList(hotKeys));
        // ?????????(frequency)
        List<HotKey> chosenKeys = hotKeyFilter.ChosenKeys(hotKeys);
        List<HotKeyVo> hotKeyVos = new ArrayList<>();
        for(HotKey key : chosenKeys){
            HotKeyVo keyVo = new HotKeyVo();
            keyVo.setId(key.getId());
            keyVo.setContent(transferUTF8.UTF8toC(key.getContent()));
            hotKeyVos.add(keyVo);
        }
        return hotKeyVos;
    }

    @Override
    public List<ProductOutlineVo> clickHotKey(Integer id) {
        // click_count?????????
        HotKey hotKey = hotKeyDao.selectById(id);
        hotKey.setClickCount(hotKey.getClickCount()+1);
        // recent_date?????????
        hotKey.setRecentDate(new Date());
        if(hotKeyDao.updateById(hotKey) > 0){
            System.out.println("Hot Key update success.");
        }
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);
        wrapper.like("name", hotKey.getContent());
        List<Product> products = productDao.selectList(wrapper);
        List<ProductOutlineVo> productOutlines = productPacking.ProductToOutline(products);
        return productOutlines;
    }

    private void deleteHotKey(List<HotKey> targets){    // ???hot key??????1000????????????????????????
        if(targets != null && !targets.isEmpty()){
            for(HotKey target : targets){
                hotKeyDao.deleteById(target);
            }
        }
    }

    // ???????????????????????????????????????0.9?????????????????????????????????true???????????????false
    public boolean Filter(List<HotKey> hotKeys, String target){
        boolean flag = true;
        for(HotKey hotKey : hotKeys){
            if(similarityFilter.levenshtein(hotKey.getContent(), target) >= 0.9){
                // ????????????????????????click_count+1
                hotKey.setClickCount(hotKey.getClickCount()+1);
                hotKeyDao.updateById(hotKey);
                flag = false;
            }
        }
        return flag;
    }

    private void updateHotKey(String keyword){  // ?????????????????????HotKey?????????????????????
        QueryWrapper<HotKey> wrapper = new QueryWrapper<>();
        List<HotKey> hotKeys = hotKeyDao.selectList(wrapper);
        // keyword???UTF-8
        if(Filter(hotKeys, keyword)){  // ???????????????
            HotKey hotKey = new HotKey();
            hotKey.setContent(keyword);
            hotKey.setCreateDate(new Date());
            hotKey.setRecentDate(new Date());
            hotKey.setClickCount(1);    // ??????????????????????????????
            if(hotKeyDao.insert(hotKey) > 0){
                System.out.println("one hot key has been inserted.");
            }
        }
    }

    private List<ProductOutlineVo> get10Outline(List<Product> products){
        List<ProductOutlineVo> outlines = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            outlines.add(productPacking.ProductToOutline(products.get(i)));
        }
        return outlines;
    }

    private void promoteAlgorithm(){    // ?????????????????????
        // ??????????????????5???:
        // 1. ????????????;
        // 2. ????????????;
        // 3. ????????????;
        // 4. ????????????;
        // 5. ????????????????????????
    }
}
