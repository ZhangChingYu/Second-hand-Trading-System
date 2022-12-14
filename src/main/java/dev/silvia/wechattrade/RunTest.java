package dev.silvia.wechattrade;

import dev.silvia.wechattrade.entity.HotKey;
import dev.silvia.wechattrade.handlers.TransferUTF8;
import dev.silvia.wechattrade.handlers.keyword.SimilarityFilter;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class RunTest {
    public static void main(String[] args) throws ParseException {
/**
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = sdf.parse("2022-12-13");
        Date end_date = new Date();

        Long start_time = start_date.getTime();
        Long end_time = end_date.getTime();
        int days = (int) ((end_time-start_time)/(1000*60*60*24));
        System.out.println("days between 2022-12-12 to 2022-12-13 is : "+days);

        TransferUTF8 transferUTF8 = new TransferUTF8();
        String str1 = "hello world";
        String str2 = "holle world";
        String utf_1 = transferUTF8.CtoUTF8(str1);
        String utf_2 = transferUTF8.CtoUTF8(str2);
        System.out.println("str1 : " + str1 + "\nsrt2 : " + str2);
        System.out.println("similarity between str1 and str2 is : " + SimilarityFilter.levenshtein(str1, str2));
        System.out.println("utf1 : " + utf_1 + "\nutf2 : " + utf_2);
        System.out.println("similarity between utf1 and utf2 is : "+ SimilarityFilter.levenshtein(utf_1, utf_2));
 */
        List<TEST> tests = new ArrayList<>();
        TEST t1 = new TEST(4, 0.4);
        TEST t2 = new TEST(1, 6.3);
        TEST t3 = new TEST(2, 4.5);
        TEST t4 = new TEST(5, 9.0);
        TEST t5 = new TEST(0, 1.1);
        tests.add(t1);
        tests.add(t2);
        tests.add(t3);
        tests.add(t4);
        tests.add(t5);
        List<TEST> targets = new ArrayList<>();
        while (targets.size()<3){
            Map<String, Object> map = clearTest(tests);
            targets.add((TEST) map.get("key"));
            tests.remove((int) map.get("index"));
        }
        System.out.println("tests: "+tests);
        System.out.println("targets: " + targets);
/**
        for(TEST t : tests){
            System.out.println(t.getId()+":"+ t.getValue());
        }
        Collections.shuffle(tests);
        for(TEST t : tests){
            System.out.println(t.getId()+":"+ t.getValue());
        }
        tests = tests.stream().sorted(Comparator.comparingDouble(TEST::getValue).reversed()).collect(Collectors.toList());
        for(TEST t : tests){
            System.out.println(t.getId()+":"+ t.getValue());
        }
        int i = 9;
        int j = 2;
        Double tes = (double) i / (double) j;
        System.out.println(tes);*/

    }

    private static Map<String, Object> clearTest(List<TEST> tests){
        Map<String, Object> map = new TreeMap<>();
        TEST target = tests.get(0);
        int index = 0;
        for(int i = 1 ; i < tests.size() ; i ++){ // 最近點擊日期最久的key要被刪除
            if(target.getValue() > tests.get(i).getValue()) {
                target = tests.get(i);
                index = i;
            }
        }
        map.put("key", target);
        map.put("index", index);
        return map;
    }
}

@Data
class TEST {
    private Integer id ;
    private Double value;

    public TEST(int i, Double v) {
        id = i;
        value = v;
    }
}

