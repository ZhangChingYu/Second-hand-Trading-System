package dev.silvia.wechattrade;

import java.util.ArrayList;
import java.util.List;

public class RunTest {
    public static void main(String[] args) {
        Double result, evaluate_minus = 0.00;
        Integer len = 10;
        List<Integer> score1_list = new ArrayList<>();
        List<Integer> score2_list = new ArrayList<>();
        List<Integer> score3_list = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            score1_list.add(0);
            score2_list.add(0);
            score3_list.add(0);
        }
        Integer total1 = 0, total2 = 0, total3 = 0;
        for(int i = 0 ; i < 10 ; i ++){
            total1 += score1_list.get(i);
            total2 += score2_list.get(i);
            total3 += score3_list.get(i);
        }
        System.out.println("score1: " + total1);
        System.out.println("score2: " + total2);
        System.out.println("score3: " + total3);
        evaluate_minus = (1.00-(((total1.doubleValue()/(len*5))*0.6)+((total2.doubleValue()/(len*5))*0.1)+((total3.doubleValue()/(len*5))*0.3)))*2.00*3.00;
        System.out.println("weight1: "+(total1.doubleValue()/(len*5))*0.6);
        System.out.println("weight2: "+(total2.doubleValue()/(len*5))*0.1);
        System.out.println("weight3: "+(total3.doubleValue()/(len*5))*0.3);
        System.out.println(evaluate_minus);
    }
}
