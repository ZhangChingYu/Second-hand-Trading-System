package dev.silvia.wechattrade.handlers.fileHandler;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

@Component
public class ReadFile {
    private String picture_url = FileDirector.PRODUCT_URL;
    private String auth_url = FileDirector.AUTH_URL;
    private String auth_temp_url = FileDirector.AUTHENTICATION_TEMP_URL;
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
    // 依次獲取某商品文件目錄下圖片的格式
    public List<String> getProductPicturesFormat(String number, Integer size){
        List<String> formats = new ArrayList<>();
        if(size == 0){
            return null;
        }
        List<String> picture_urls = getProductPictureURL(number);
        for(String picture_url : picture_urls){
            Integer index = picture_url.lastIndexOf(".");
            String format = picture_url.substring(index).substring(1);
            formats.add(format);
        }
        return formats;
    }

    // 將某一商品的所有圖片依序轉換成Base64編碼輸出的方法
    public List<String> getProductPictures(String number, Integer size){
        // 通過商品編碼和照片數返回圖片的base64編碼表
        List<String> pictures = new ArrayList<>();
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
    public Map<String, Object> getAuthTempPic(String phone){
        Map<String, Object> map = new HashMap<>();
        String picture = "";
        String format = "";
        File folder = new File(auth_temp_url);
        File[] picture_files = folder.listFiles();
        for(File file : picture_files){
            if(file.getName().contains(phone)){ // 找到相同手機號的文件
                String file_path = file.getPath();
                format = file.getName().substring(file.getName().lastIndexOf(".")).substring(1);
                picture = getBaseFile(file_path);
            }
        }
        map.put("format", format);
        map.put("picture", picture);
        return map;
    }
    public Map<String, Object> readAuthPicture(String phone){
        Map<String, Object> map = new HashMap<>();
        File folder = new File(auth_url+phone+"/Authentication");
        File[] pic_file = folder.listFiles();
        String pic_url = pic_file[0].getPath(); // 只有一張
        Integer index = pic_url.lastIndexOf(".");
        String format = pic_url.substring(index+1);
        map.put("picture", getBaseFile(pic_url));
        map.put("format", format);
        return map;
    }

    // 獲取商品的封面圖片
    public Map<String, Object> getProductCoverPic(String number){
        Map<String, Object> cover_pic = new HashMap<>();
        List<String> picture_urls = getProductPictureURL(number);
        String cover_url = picture_urls.get(0);
        Integer index = cover_url.lastIndexOf(".");
        String format = cover_url.substring(index).substring(1);
        cover_pic.put("picture", getBaseFile(cover_url));
        cover_pic.put("format", format);
        return cover_pic;
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