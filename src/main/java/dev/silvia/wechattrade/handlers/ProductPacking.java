package dev.silvia.wechattrade.handlers;

import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;
import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductPacking {

    TransferUTF8 transferUTF8 = new TransferUTF8();

    // 將List<Product>類包裝成List<ProductOutlineVo>的方法
    public List<ProductOutlineVo> ProductToOutline(List<Product> products){
        List<ProductOutlineVo> productOutlines = new ArrayList<>();
        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            ProductOutlineVo outline = ProductToOutline(product);
            productOutlines.add(outline);
        }
        return productOutlines;
    }

    // 將Product類包裝成ProductOutlineDto的方法
    public ProductOutlineVo ProductToOutline(Product product){
        ProductOutlineVo outline = new ProductOutlineVo();
        outline.setName(transferUTF8.UTF8toC(product.getName()));
        outline.setNumber(product.getNumber());
        outline.setPrice(product.getPrice());
        if(product.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
            String url = "C:/Users/Sunny/Desktop/Products/"+product.getCatalog()+"/"+product.getNumber()+"/"+product.getNumber()+"_0.jpg";
            outline.setCoverPic(ReadFile.getBaseFile(url));
        }   // 若無照片則ProductOutlineDto中的picture=null
        return outline;
    }

    // 將Product類和User類封裝成ProductDetailDto類的方法
    public ProductDetailVo ProductUserToDetail(Product product, User seller, List<String> pictures){
        ProductDetailVo detail = new ProductDetailVo();
        // 開始準備商品信息
        detail.setName(transferUTF8.UTF8toC(product.getName()));
        detail.setSeller_name(transferUTF8.UTF8toC(seller.getUserName()));
        detail.setAddress(transferUTF8.UTF8toC(product.getAddress()));
        detail.setDate(getDate(product.getNumber()));     // 通過商品編號可解析出時間信息
        detail.setPrice(product.getPrice());
        detail.setIntro(transferUTF8.UTF8toC(product.getIntro()));
        detail.setLike_count(product.getLikeCount());
        detail.setPicture_count(product.getPicture());
        detail.setPictures(pictures);
        return detail;
    }

    // 從商品編碼中解析出商品發布時間Date的方法
    public String getDate(String number){  // 通過商品編碼解析出發布時間
        long time = Long.parseLong(number.substring(1));    // 將類型編號去掉
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormat = sdf.format(time);
        return dateFormat;
    }
}
