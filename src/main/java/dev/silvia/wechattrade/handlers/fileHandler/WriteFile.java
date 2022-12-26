package dev.silvia.wechattrade.handlers.fileHandler;
import dev.silvia.wechattrade.vo.FeedbackVo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class WriteFile {
    private String feedback_url = FileDirector.FEEDBACK_URL;
    private String help_url = FileDirector.HELP_URL;
    private String principle_url = FileDirector.USER_PRINCIPLE_URL;
    private String picture_url = FileDirector.PRODUCT_URL;
    private String auth_url = FileDirector.AUTH_URL;
    private String auth_temp_url = FileDirector.AUTHENTICATION_TEMP_URL;
    private String ads_url = FileDirector.ADS_URL;
    public static void storeMultipartFile(String filePath, String newName, MultipartFile file){
        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = null;
        try {
            inputStream = file.getInputStream();
            fileName = newName+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String path = filePath;
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public int storeOnePicture(String catalog, String number, Integer index, MultipartFile picture){
        String pathName = picture_url + catalog + "/" + number;
        File folder = new File(pathName);
        if(!folder.isDirectory()){
            if(!folder.mkdirs()){
                return 808; // 路徑創建失敗
            }
        }
        // 以封面為例: 商品編號_0.jpg
        String new_name = number+ "_" + index;
        storeMultipartFile(pathName, new_name, picture);
        return 201; // 存儲成功
    }

    public int createUserFilePath(String phone){    // 註冊時創建用戶文件路徑
        String avatarPath = FileDirector.AUTH_URL.substring(0,27);
        String authenticationPath = FileDirector.AUTH_URL.substring(0,27);
        Integer[] check = {0,0};
        if(createDir(avatarPath, phone) != null){
            avatarPath = createDir(avatarPath, phone);
            authenticationPath = createDir(authenticationPath, phone);
            if(createDir(avatarPath, "Avatar")!= null){
                // C:/Users/Sunny/Desktop/(Phone)/Avatar
                check[0] = 1;
            }
            if(createDir(authenticationPath, "Authentication") != null){
                // C:/Users/Sunny/Desktop/(Phone)/Authentication
                check[1] = 1;
            }
        }
        if(check[0].equals(check[1]) && check[0].equals(1)){
            return 201; // 路徑創建完成
        }
        return 800;     // 路徑創建失敗
    }

    public int storeAuthPicAtTemp(String phone, MultipartFile picture){
        // 將尚未通過的用戶證件照保存在AuthTemp文件夾內，等通過後再移入用戶文件夾，若不通過則刪除圖片
        // C:/Users/Sunny/Desktop/User/AuthTemp/(Phone).jpg
        String file_path = auth_temp_url;
        String new_name = phone;
        storeMultipartFile(file_path, new_name, picture);
        return 201; // 存儲成功
    }

    public int storeAuthenticationPicture(String phone, MultipartFile picture){
        // C:/Users/Sunny/Desktop/User/12434789874/Authentication
        String pathName = auth_url + phone+"/Authentication";
        File folder = new File(pathName);
        if(!folder.isDirectory()){
            if(!folder.mkdirs()){
                return 808; // 路徑創建失敗
            }
        }
        // C:/Users/Sunny/Desktop/User/12434789874/Avatar/12434789874.jpg
        String new_name = phone;
        storeMultipartFile(pathName, new_name, picture);
        return 201; // 存儲成功
    }

    public int storeAvatarPicture(String phone, MultipartFile picture){
        // C:/Users/Sunny/Desktop/User/12434789874/Avatar
        String pathName = auth_url + phone+"/Avatar";
        File folder = new File(pathName);
        if(!folder.isDirectory()){
            if(!folder.mkdirs()){
                return 808; // 路徑創建失敗
            }
        }
        // C:/Users/Sunny/Desktop/User/12434789874/Authentication/12434789874.jpg
        String newName = phone;
        storeMultipartFile(pathName,newName,picture);
        return 201; // 存儲成功
    }
    // 創建幫助類型文件夾
    public Integer newHelpCatalog(String name){
        if(createDir(help_url, name) != null){
            return 200;
        }
        return 422;
    }
    // 創建幫助文件
    public Integer newHelp(String catalog, String question, String answer) {
        // 創建幫助(問題+答案)
        String file_path = help_url + "/" + catalog + "/" + question + ".txt";
        File file = new File(file_path);
        answer = answer.replaceAll("\n", "\n\r");
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            out.write(answer);
        } catch (FileNotFoundException e) {
            System.out.println("File is not fond!");
            return 404;
        } catch (IOException e) {
            System.out.println("Write Exception!");
            return 422;
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return 800;
                }
            }
        }
        return 200;
    }
    // 更改文件夾名字
    public Integer updateHelpCatalog(String oldName, String newName){
        String filePath = help_url + "/" + oldName;
        File folder = new File(filePath);
        File newFile = new File(help_url+"/"+newName);
        if(folder.renameTo(newFile)){
            return 200;
        }
        return 422;
    }
    // 創建用戶協議
    public Integer createPrinciple(String version, String content){
        String filePath = principle_url;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File(filePath+"/"+version+".txt");
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
            out.write("Version: "+version+"\r\n");
            out.write("Date: "+sdf.format(date)+"\r\n");
            out.write(content+"\r\n"); // 寫入"換行"時一定要用\r\n否則無效
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
                return 200;
            }
            System.out.println("Create file path failed on month!");
            return 422;
        }
    }
    // 上傳輪播圖圖片
    public Integer save_ads_pic(MultipartFile file){
        String file_path = ads_url;
        String file_name = "";
        File folder = new File(file_path);
        File[] files = folder.listFiles();
        if(files!=null&&files.length>=4){
            return 403;
        }
        for(File f : files){    // 避免命名重複
            if(f.getName().equals(file.getOriginalFilename())){
                file_name = String.valueOf(System.currentTimeMillis());
            }
        }
        if(file_name.equals("")){
            file_name = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
        }
        storeMultipartFile(file_path, file_name,file);
        return 200;
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
}


