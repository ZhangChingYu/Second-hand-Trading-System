package dev.silvia.wechattrade.handlers.fileHandler;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ReadFile {
    private String picture_url = FileDirector.PRODUCT_PICTURE_URL;
    private String auth_url = FileDirector.AUTH_URL;
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
            String url = picture_url+catalog+"/"+number+"/"+number+"_"+i+".jpg";
            String base64 = ReadFile.getBaseFile(url);
            pictures.add(base64);
        }
        return pictures;
    }

    // 將某一商品的所有圖片依序轉換成Base64編碼輸出的方法
    public List<String> getPictureBase64(String sort,String phone, Integer size){
        // 通過商品編碼和照片數返回圖片的base64編碼表
        List<String> pictures = new ArrayList<>();
        if(size == 0){
            return null;    // 如果該商品沒有照片，則返回null
        }
        for(int i = 0; i < size; i++){
            String url = auth_url+sort+"/"+phone+"/"+phone+"_"+i+".jpg";
            pictures.add(url);
        }
        System.out.println(pictures);   // 一次讀一行
        return pictures;
    }

    public String readHelpFile(String filePath){
        File file = new File(filePath);
        BufferedReader in = null;
        String out = "";
        try {   // 用UTF-8讀取會出否則會出現亂碼
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = "";
            while ((line = in.readLine())!=null){
                System.out.println(line);   // 一次讀一行
                out += line + "\n";
            }
        } catch (FileNotFoundException e){
            return "File Not Fond!";
        } catch (IOException e){
            return "Read Exception!";
        } finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e){
                    return e.getMessage();
                }
            }
        }
        return out;
    }

    public List<String> getSubFileNames(String root){
        File file = new File(root); // root 根目錄
        File[] subFiles = file.listFiles(); // 根據root獲取子目錄
        List<String> fileNames = new ArrayList<>();
        if(subFiles != null){
            for (int i = 0; i < subFiles.length; i++){
                fileNames.add(subFiles[i].getName());
            }
        }
        return fileNames;
    }
}