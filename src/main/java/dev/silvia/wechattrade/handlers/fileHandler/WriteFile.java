package dev.silvia.wechattrade.handlers.fileHandler;
import dev.silvia.wechattrade.dto.product.CommentDto;
import dev.silvia.wechattrade.vo.FeedbackVo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Component
public class WriteFile {
    private String feedback_url = FileDirector.FEEDBACK_URL;
    private String picture_url = FileDirector.PRODUCT_URL;
    private String auth_url = FileDirector.AUTH_URL;

    public Integer writeCommentFile(CommentDto comment){

        return null;
    }
    public Integer writeFeedbackFile(FeedbackVo feedback){  // // 寫入feedback文件
        // C://Users/Sunny/Desktop/Feedback/(Year)/(Month)/(Time+Phone).txt
        String filePath = feedback_url;
        if(createDir(filePath, feedback.getYear())!=null){
            filePath = createDir(filePath, feedback.getYear());
            if(createDir(filePath, feedback.getMonth())!=null){
                filePath = createDir(filePath, feedback.getMonth());
                File file = new File(filePath+"/"+feedback.getTime()+feedback.getPhone()+".txt");
                BufferedWriter out = null;
                try {
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
                    out.write("From: "+feedback.getFrom()+"\r\n");
                    out.write("Date: "+feedback.getDate()+"\r\n");
                    out.write(feedback.getContent()+"\r\n"); // 寫入"換行"時一定要用\r\n否則無效
                } catch (FileNotFoundException e) {
                    System.out.println("File is not fond!");
                    return 422;
                } catch (IOException e) {
                    System.out.println("Read or Write Exception!");
                    return 422;
                } finally {     // BufferedWriter out 一定要close()否則不會寫入
                    if(null != out){
                        try {
                            out.close();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
                return 200;
            }
            System.out.println("Create file path failed on month!");
            return 422;
        }
        System.out.println("Create file path failed on year!");
        return 422;
    }

    public void test(){ // C:\Users\Sunny\Desktop\Help
        File file = new File("C://Users/Sunny/Desktop/help.txt");
        File file1 = new File("C://Users/Sunny/Desktop/help1.txt");
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1),"gbk"));
            String line = "";
            while ((line = in.readLine())!=null){
                System.out.println(line);
                out.write(line+"\r\n"); // 寫入"換行"時一定要用\r\n否則無效
            }
        } catch (FileNotFoundException e) {
            System.out.println("File is not fond!");
        } catch (IOException e) {
            System.out.println("Read or Write Exception!");
        } finally {     // BufferedWriter out 一定要close()否則不會寫入
            if (null != in){
                try {
                    in.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(null != out){
                try {
                    out.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private String createDir(String root, String dir){
        File url = new File(root+"/"+dir);
        if(!url.isDirectory()){
            if(!url.mkdirs()){
                return null;
            }
        }
        return root+"/"+dir;
    }

    // 將圖片文件寫入磁盤
    public int storePictures(String catalog, String number, List<MultipartFile> pictures){
        // C:/Users/Sunny/Desktop/Products/catalog/number/Pictures
        String pathName = picture_url + catalog + "/" + number+"/Pictures";
        Integer length = pictures.size();   // 獲取照片數
        File folder = new File(pathName);
        if(!folder.isDirectory()){
            if(!folder.mkdirs()){
                return 808; // 路徑創建失敗
            }
        }
        for(int i = 0; i < length; i++){
            String oldName = pictures.get(i).getOriginalFilename();
            assert oldName != null;
            // 已馬克杯為例: 編號_0.jpg 編號_1.jpg
            String newName = number+ "_" + i + oldName.substring(oldName.lastIndexOf("."));
            try {
                pictures.get(i).transferTo(new File(folder, newName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = pathName + "/" + newName;
            System.out.println(path);
        }
        return 201;
    }

    public int storeravatarPictures(String catalog,String phone, List<MultipartFile> pictures) {
        // C:/Users/Sunny/Desktop/Products/catalog/number
        String pathName = auth_url + catalog +"/" + phone;
        Integer length = pictures.size();   // 獲取照片數
        File folder = new File(pathName);
        if(!folder.isDirectory()){
            if(!folder.mkdirs()){
                return 808; // 路徑創建失敗
            }
        }
        for(int i = 0; i < length; i++){
            String oldName = pictures.get(i).getOriginalFilename();
            assert oldName != null;
            // 已馬克杯為例: 編號_0.jpg 編號_1.jpg
            String newName = phone+ "_" + i + oldName.substring(oldName.lastIndexOf("."));
            try {
                pictures.get(i).transferTo(new File(folder, newName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = pathName + "/" + newName;
            System.out.println(path);
        }
        return 201;
    }
}


