package dev.silvia.wechattrade.handlers.fileHandler;


import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MoveFile { // 將Products文件結構更改
    private String auth_temp_url = FileDirector.AUTHENTICATION_TEMP_URL;
    private String auth_url = FileDirector.AUTH_URL;
    private static int moveFile(String oldPath, String newPath){   // 移動文件位置
        File oldName = new File(oldPath);
        File newName = new File(newPath);
        if(!oldName.exists()) {
            System.out.println("File not found, ending the system.");
            return 402; // 找不到要移動的文件
        }
        System.out.println("File detected, ready to transfer.");
        if(!oldName.renameTo(newName)) {
            System.out.println("File transfer failed.");
            return 400; // 文件移動失敗
        }
        System.out.println("File transfer complete, end the system.");
        return 200; // 文件移動完成
    }

    public Integer moveAuthTempToUser(String phone){
        File folder = new File(auth_temp_url);
        File[] files = folder.listFiles();
        String oldPath = "";
        String newPath = "";
        for(File file : files){     // 找到要移動的文件
            if(file.getName().contains(phone)){
                oldPath = file.getPath();
                newPath = auth_url + phone + "/Authentication/" + file.getName();
            }
        }
        if(oldPath != "" && newPath != ""){
            return moveFile(oldPath, newPath);
        }
        return 800; // 不存在對應用戶的證件照
    }
}
