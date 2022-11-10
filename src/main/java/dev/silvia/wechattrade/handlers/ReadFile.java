package dev.silvia.wechattrade.handlers;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ReadFile {     // 從指定目錄獲取文件並進行base64編碼後傳給前端
    public static String getBaseFile(String filePath){
        if(filePath==null){
            return null;
        }
        InputStream in;
        byte[] data = null;
        try{
            in = new FileInputStream(filePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 進行Base64編碼
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

    // 將某一商品的所有圖片依序轉換成Base64編碼輸出的方法
    public List<String> getPicturesBase64(String number, Integer size){
        // 通過商品編碼和照片數返回圖片的base64編碼表
        List<String> pictures = new ArrayList<>();
        Character catalog = number.charAt(0);   // 商品類型編碼
        if(size == 0){
            return null;    // 如果該商品沒有照片，則返回null
        }
        for(int i = 0; i < size; i++){
            String url = "C:/Users/Sunny/Desktop/Products/"+catalog+"/"+number+"/"+number+"_"+i+".jpg";
            String base64 = ReadFile.getBaseFile(url);
            pictures.add(base64);
        }
        return pictures;
    }
}
