package dev.silvia.wechattrade.handlers.fileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFile { // 將Products文件結構更改

    public void moveFile(String src, String dest, String root){
        File catalog_file = new File(root);
        Path result = null;
        try{
            result = Files.move(Paths.get(src), Paths.get(dest));
        } catch (IOException e){
            System.out.println("Exception while moving file: " + e.getMessage());
        }
        if(result != null){
            System.out.println("Files moving success!");
        } else {
            System.out.println("File moving failed!");
        }
    }
}
