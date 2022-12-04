package dev.silvia.wechattrade;

import dev.silvia.wechattrade.handlers.fileHandler.test;

import java.util.List;

public class RunTest {
    public static void main(String[] args) {
        test test = new test();
        List<String> out = test.testComment("C:/Users/Sunny/Desktop/test.txt");
        String father = out.get(0).substring(7);
        String content = out.get(1).substring(8);
        if(father.isEmpty()){
            System.out.println("equal");
        }
        System.out.println(father);
        System.out.println(content);
    }
}
