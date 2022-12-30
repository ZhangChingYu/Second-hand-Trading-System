package dev.silvia.wechattrade.handlers.picSize;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Component
public class PicUtil {
    public static String resizeImageToSize(String filePath,int max_wi,int max_he) {
        try {
            float rate = 1;
            /*图片的原始宽 120*/
            int oldwi;
            /*图片的原始高 120*/
            int oldhe;
            /*图片修改后的宽 0*/
            int new_wi = 0;
            /*图片修改后的高 0*/
            int new_he = 0;
            /*拿到byte图片*/
            InputStream is = new FileInputStream(filePath);
            BufferedImage bufImg = ImageIO.read(is);
            /*图片的原始宽度*/
            oldwi = bufImg.getWidth();
            /*图片的原始高度*/
            oldhe = bufImg.getHeight();

            rate = (float) oldwi / (float) oldhe;
            /*如果图片的原宽大于最大宽度，并且原高小于等于最大高度。则证明图片过宽了，将图片宽度设置为最大宽度，此时需要等比例减小高度*/
            if (oldwi > max_wi && oldhe <= max_he) {
                new_wi = max_wi;
                new_he = new Float((float) new_wi / rate).intValue();
                /*如果图片的原宽和原高都大于或者都小于其所对应的最大值，则以任意一方为主(此处以最大高度为主)*/
            } else if (oldwi >= max_wi && oldhe >= max_he || oldwi <= max_wi && oldhe <= max_he) {
                new_he = max_he;
                new_wi = new Float(new_he * rate).intValue();
                /*如果图片的原宽小于于最大宽度，并且原高大于等于最大高度。则证明图片过高了，将图片宽度设置为最大高度，此时需要等比例减小宽度*/
            } else if (oldwi <= max_wi && oldhe > max_he) {
                new_he = max_he;
                new_wi = new Float(new_he * rate).intValue();
            }
            /*开始改变大小*/
            BufferedImage bf = new BufferedImage(new_wi, new_he, BufferedImage.TYPE_INT_RGB);
            bf.getGraphics().drawImage(bufImg, 0, 0, new_wi, new_he, null);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //转换编码格式JPEG
            ImageIO.write(bf, "jpg", out);
            byte[] re = out.toByteArray();
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(re);
        } catch (Exception e) {
            return "false";
        }
    }
    // 对字节数组字符串进行Base64解码并生成图片
    //imgFilePath 待保存的本地路径
    public static boolean GenerateImage(String base64Str, String imgFilePath) {
        if (base64Str == null) {
            // 图像数据为空
            System.out.println("12313");
            return false;
        }

        Base64.Decoder decoder = Base64.getDecoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decode(base64Str);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
