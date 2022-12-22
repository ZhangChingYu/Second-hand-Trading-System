package dev.silvia.wechattrade.handlers.picSize;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class UploadUtil {

    //保存图片为 webp
    public static String saveImage(MultipartFile mFile, String path) throws IOException {
        System.out.println(path);
        var file = saveFile(mFile,path);//先将图片保存至本地
        File webpFile = new File(path+file+".webp");
        //注: BufferedImage 返回 null 是未在 pom.xml 中添加依赖
        BufferedImage bufferedImage = ImageIO.read(new File(path+file));
        ImageIO.write(bufferedImage,"webp", webpFile);
        System.out.println(file);
        new File(path+file).deleteOnExit();//删除原文件
        return file+".webp";
    }

    //保存文件至本地
    public static String saveFile(MultipartFile file,String path) throws IOException {

        String[] fileStr = file.getOriginalFilename().split("\\.");
        String suffix =  fileStr[fileStr.length - 1];

        String fileName = UUID.randomUUID().toString().replace("-","");
        String filePath = path+fileName+"."+suffix;
        File desFile = new File(filePath);

        if(!desFile.getParentFile().exists()){
            desFile.mkdirs();
        }
        System.out.println(filePath+suffix);
        file.transferTo(desFile);
        return fileName+"."+suffix;
    }
}
