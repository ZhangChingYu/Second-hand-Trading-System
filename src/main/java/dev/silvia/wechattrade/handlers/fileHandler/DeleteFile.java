package dev.silvia.wechattrade.handlers.fileHandler;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DeleteFile {
    private String product_picture_url = FileDirector.PRODUCT_URL;
    private String auth_temp_url = FileDirector.AUTHENTICATION_TEMP_URL;

    private static boolean deleteOneFile(String filePath){    // 刪除某路徑下單個文件
        boolean flag = false;
        // 根據路徑創建文件對象
        File file = new File(filePath);
        // 路徑下是個文件且文件不為空時刪除文件，注意，路徑下仍有子文件則不能直接刪除
        if(file.isFile() && file.exists()){
            flag = file.delete();
        }
        // 刪除失敗的話flag會是false
        return flag;
    }
    // 刪除用戶暫存圖片
    public boolean deleteAuthTempPicture(String phone){
        String filePath = "";
        File folder = new File(auth_temp_url);
        File[] files = folder.listFiles();
        for(File file : files){
            if(file.getName().contains(phone)){
                filePath = file.getPath();
            }
        }
        if(filePath != ""){
            return deleteOneFile(filePath);
        }
        System.out.println("[DELETE] File not found.");
        return false;
    }

    public boolean deleteProductPictures(String product_number){    // 根據商品編號刪除商品圖片檔
        boolean flag = false;
        String catalog = product_number.substring(0,1);
        String dirPath = product_picture_url+catalog+"/"+product_number;
        System.out.println(dirPath);
        File dir = new File(dirPath);
        if(dir.exists()){
            deleteDirFile(dir);
            if(!dir.exists()){  // 檢查檔案是否還存在，不存在則刪除成功
                flag = true;
            }
            System.out.println("File delete failed!");
        }else {
            System.out.println("File does not exist!");
        }
        return flag;
    }

    private static void deleteDirFile(File dir){   // 刪除文件目錄需遞歸刪除所有子文件後，再刪除該目錄
        // 獲取目錄下所有子文件和目錄
        File[] subFiles = dir.listFiles();
        for(File file : subFiles){
            if(file.isDirectory()){ // 如果該文件是目錄，則繼續遞歸
                deleteDirFile(file);
            }else {
                file.delete();
                //System.out.println(file.getName()+":: file has been deleted.");
            }
        }
        // 最後把目錄也刪除
        dir.delete();
        //System.out.println(dir.getName()+":: directory has been deleted.");
    }
}
