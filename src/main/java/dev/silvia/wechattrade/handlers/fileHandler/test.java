package dev.silvia.wechattrade.handlers.fileHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test {

    public List<String> testComment(String filePath){
        File file = new File(filePath);
        BufferedReader in = null;
        List<String> out = new ArrayList<>();
        try {   // 用UTF-8讀取會出否則會出現亂碼
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = "";
            while ((line = in.readLine())!=null){
                out.add(line);
            }
        } catch (FileNotFoundException e){
            return Collections.singletonList("File Not Fond!");
        } catch (IOException e){
            return Collections.singletonList("Read Exception!");
        } finally {
            if(null != in){
                try {
                    in.close();
                } catch (IOException e){
                    return Collections.singletonList(e.getMessage());
                }
            }
        }
        return out;
    }
}
