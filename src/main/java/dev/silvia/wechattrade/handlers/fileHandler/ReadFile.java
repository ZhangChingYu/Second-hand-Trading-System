package dev.silvia.wechattrade.handlers.fileHandler;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class ReadFile {
    private String picture_url = FileDirector.PRODUCT_URL;
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
    public List<String> getProductPicturesBase64(String number, Integer size){
        // 通過商品編碼和照片數返回圖片的base64編碼表
        List<String> pictures = new ArrayList<>();
        Character catalog = number.charAt(0);   // 商品類型編碼
        if(size == 0){
            return null;    // 如果該商品沒有照片，則返回null
        }
        List<String> pictures_url = getProductPictureURL(number);   // 獲取該商品文件目錄下所有圖片路徑
        for(String pic_url : pictures_url){
            String url = pic_url;
            String base64 = ReadFile.getBaseFile(url);
            pictures.add(base64);
        }
        return pictures;
    }
    // 獲取商品的封面圖片
    public String getProductCoverPic(String number){
        List<String> picture_urls = getProductPictureURL(number);
        String cover_url = picture_urls.get(0);
        return getBaseFile(cover_url);
    }
    public String readAvatarPicture(String phone){  // 根據用戶手機號返回頭像的base64編碼
        File avatar_file = new File(auth_url+phone+"/Avatar");
        File[] picture_url = avatar_file.listFiles();   // 拿第一張就行
        String filePath = picture_url[0].getPath();
        return getBaseFile(filePath);
    }

    public String getAvatarPicture(String phone){
        String filePath = auth_url+phone+"/Avatar/"+phone+".jpg";
        return filePath;
    }

    public String getAuthPicture(String phone){
        String filePath = auth_url+phone+"/Authentication/"+phone+".jpg";
        return filePath;
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

    public List<String> getProductPictureURL(String number){    // 獲取商品文件目錄下所有圖片的地址
        List<String> pictures = new ArrayList<>();
        String catalog = number.substring(0,1);
        String root = picture_url + catalog + "/" + number;
        File product_file = new File(root);
        File[] pic_url = product_file.listFiles();
        for(File pic : pic_url){
            String picture = pic.getPath();
            pictures.add(picture);
        }
        return pictures;
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