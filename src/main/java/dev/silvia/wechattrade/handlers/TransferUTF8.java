package dev.silvia.wechattrade.handlers;


import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class TransferUTF8 {
    // 將中文字串轉為UTF-8格式字串
    // 將字串轉為UTF-8格式
    public String CtoUTF8(String chinese){
        if(chinese == null || chinese.equals("")){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try{
            char c;
            for(int i = 0; i < chinese.length(); i++){
                c = chinese.charAt(i);
                byte[] b;
                b = Character.toString(c).getBytes("utf-8");
                for(int j = 0; j < b.length; j++){
                    int k = b[j];
                    // 轉換為unsigned integer無符號integer
                    k = k < 0 ? k+256 : k;
                    // 返回整數參數的字符串表示形式，作為16進制(base16)中的無符號整數
                    //該值以16進制(base16)轉為ASCII數字的字符串
                    sb.append(Integer.toHexString(k).toUpperCase());
                    // url轉置形式
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // 將UTF-8格式字串轉為中文字串
    public String UTF8toC(String utf8){
        if(utf8 == null || utf8.equals("")){
            return null;
        }
        try{
            utf8 = utf8.toUpperCase();
            int total = utf8.length()/2;
            // 標示字節長度
            int pos = 0;
            byte[] buffer = new byte[total];
            for(int i = 0; i < total; i++){
                int start = i * 2;
                // 將字符串參數解析為第二個參數指定的基數中的有符號整數
                buffer[i] = (byte)Integer.parseInt(utf8.substring(start, start+2),16);
                pos++;
            }
            // 通過使用指定的字符集解碼指定的自節子陣列來構造一個新的字符串
            // 新的字符串的長度是字符集的函數，因此可能不等於子數組的長度
            return new String(buffer, 0, pos, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf8;
    }
}
