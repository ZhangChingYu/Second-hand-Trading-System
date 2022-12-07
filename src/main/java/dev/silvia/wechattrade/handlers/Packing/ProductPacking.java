package dev.silvia.wechattrade.handlers.Packing;

import dev.silvia.wechattrade.entity.Product;
import dev.silvia.wechattrade.entity.User;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.fileHandler.FileDirector;
import dev.silvia.wechattrade.handlers.fileHandler.ReadFile;
import dev.silvia.wechattrade.vo.product.MyProductVo;
import dev.silvia.wechattrade.vo.product.ProductDetailVo;
import dev.silvia.wechattrade.vo.product.ProductLikeVo;
import dev.silvia.wechattrade.vo.product.ProductOutlineVo;
import dev.silvia.wechattrade.vo.request.product.UploadRequestDetailVo;
import dev.silvia.wechattrade.vo.request.product.UploadRequestOutlineVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductPacking {
    private String picture_url = FileDirector.PRODUCT_URL;
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

    // 將Product類包裝成ProductOutlineVo的方法
    public ProductOutlineVo ProductToOutline(Product product){
        ProductOutlineVo outline = new ProductOutlineVo();
        outline.setName(transferUTF8.UTF8toC(product.getName()));
        outline.setNumber(product.getNumber());
        outline.setPrice(product.getPrice());
        outline.setCoverPic(getCoverPic(product));
        return outline;
    }

    // 將List<Product>包裝成List<ProductLikeVo>的方法
    public List<ProductLikeVo> ProductToProductLike(List<Product> products){
        List<ProductLikeVo> productLikeVos = new ArrayList<>();
        for(int i = 0; i < products.size(); i ++){
            Product product = products.get(i);
            ProductLikeVo productLikeVo = ProductToProductLike(product);
            productLikeVos.add(productLikeVo);
        }
        return productLikeVos;
    }

    // 將Product包裝成ProductLikeVo的方法
    public ProductLikeVo ProductToProductLike(Product product){
        ProductLikeVo likeVo = new ProductLikeVo();
        likeVo.setName(transferUTF8.UTF8toC(product.getName()));
        likeVo.setNumber(product.getNumber());
        likeVo.setPrice(product.getPrice());
        likeVo.setCoverPic(getCoverPic(product));
        likeVo.setIntro(transferUTF8.UTF8toC(product.getIntro()));
        return likeVo;
    }

    // 將Product類和User類封裝成ProductDetailVo類的方法
    public ProductDetailVo ProductUserToDetail(Product product, User seller, String seller_pic, List<String> pictures){
        ProductDetailVo detail = new ProductDetailVo();
        // 開始準備商品信息
        detail.setName(transferUTF8.UTF8toC(product.getName()));
        detail.setSeller_name(transferUTF8.UTF8toC(seller.getUserName()));
        detail.setSeller_pic(seller_pic);
        detail.setAddress(transferUTF8.UTF8toC(product.getAddress()));
        detail.setDate(getDate(product.getNumber()));     // 通過商品編號可解析出時間信息
        detail.setPrice(product.getPrice());
        detail.setStorage(product.getStorage());
        detail.setIntro(transferUTF8.UTF8toC(product.getIntro()));
        detail.setLike_count(product.getLikeCount());
        detail.setPicture_count(product.getPicture());
        detail.setPictures(pictures);
        return detail;
    }

    // 將Product包裝成MyProductVo的方法
    public MyProductVo ProductToMyProduct(Product product){
        MyProductVo myProduct = new MyProductVo();
        // 開始準備商品信息
        myProduct.setNumber(product.getNumber());
        myProduct.setName(transferUTF8.UTF8toC(product.getName()));
        myProduct.setPrice(product.getPrice());
        myProduct.setStatus(product.getStatus());
        myProduct.setCoverPic(getCoverPic(product));
        return myProduct;
    }

    // 將Product包裝成UploadRequestOutlineVo的方法
    public UploadRequestOutlineVo ProductToRequestOutline(Product product){
        UploadRequestOutlineVo outlineVo = new UploadRequestOutlineVo();
        outlineVo.setName(transferUTF8.UTF8toC(product.getName()));
        outlineVo.setNumber(product.getNumber());
        outlineVo.setDate(getDate(product.getNumber()));
        outlineVo.setCatalog(product.getCatalog());
        outlineVo.setSPhone(product.getSPhone());
        outlineVo.setStatus(product.getStatus());
        return outlineVo;
    }
    // 將Product包裝成UploadRequestDetailVo的方法
    public UploadRequestDetailVo ProductToRequestDetail(Product product, User seller, List<String> pictures){
        UploadRequestDetailVo detailVo = new UploadRequestDetailVo();
        detailVo.setNumber(product.getNumber());
        detailVo.setName(transferUTF8.UTF8toC(product.getName()));
        detailVo.setStatus(product.getStatus());
        detailVo.setCatalog(product.getCatalog());
        detailVo.setSellerName(transferUTF8.UTF8toC(seller.getRealName()));
        detailVo.setAddress(transferUTF8.UTF8toC(product.getAddress()));
        detailVo.setDate(getDate(product.getNumber()));
        detailVo.setPrice(product.getPrice());
        detailVo.setStorage(product.getStorage());
        detailVo.setIntro(transferUTF8.UTF8toC(product.getIntro()));
        detailVo.setPicture_count(product.getPicture());
        detailVo.setPictures(pictures);
        return detailVo;
    }

    // 從商品編碼中解析出商品發布時間Date的方法
    public String getDate(String number){  // 通過商品編碼解析出發布時間
        long time = Long.parseLong(number.substring(1));    // 將類型編號去掉
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormat = sdf.format(time);
        return dateFormat;
    }

    // 返回封面
    private String getCoverPic(Product product){
        if(product.getPicture() > 0){ // 檢查是否有圖片，若有則用第一張照片做封面
            String url = picture_url+product.getCatalog()+"/"+product.getNumber()+"/"+product.getNumber()+"_0.jpg";
            return ReadFile.getBaseFile(url);
        }   // 若無照片則ProductOutlineDto中的picture=null
        return null;
    }
}
