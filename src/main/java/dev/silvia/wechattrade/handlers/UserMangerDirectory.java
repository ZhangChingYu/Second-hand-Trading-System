package dev.silvia.wechattrade.handlers;

import dev.silvia.wechattrade.dto.feedback.DirectoryDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMangerDirectory {
    public static List<Integer> createTrade() {
        List<Integer> directory=new ArrayList<>();
        directory.add(5);       //交易目录长度
        directory.add(10);       //交易步长
        return directory;
    }
    public static List<Integer> createViolation() {
        List<Integer> directory=new ArrayList<>();
        directory.add(8);       //违规目录长度
        directory.add(10);        //违规步长
        return directory;
    }
    //生成directory
    //length：目录长度  step:步长  即1~10
    public static List<DirectoryDto> createDirectory(int length,int step) {
        List<DirectoryDto> directoryDto=new ArrayList<>();
        for(int i=0;i<length;i++){
            DirectoryDto directoryDto1=new DirectoryDto();
            List<Integer> list = new ArrayList<>();
            if(i==0){
                list.add(0);
                list.add((i+1)*step);
            }
            else{
                list.add(i*step+1);
                list.add((i+1)*step);
            }
            directoryDto1.setNumber(i);
            directoryDto1.setRange(list);
            directoryDto.add(directoryDto1);
        }
        return directoryDto;
    }
}
