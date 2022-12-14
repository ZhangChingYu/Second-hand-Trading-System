package dev.silvia.wechattrade.handlers.keyword;

import dev.silvia.wechattrade.entity.HotKey;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimilarityFilter { // 過濾相似度過高的關鍵字

    // 若與其他關鍵字相似度都低於0.9則需要添加該關鍵字返回true，反之返回false
    public boolean Filter(List<HotKey> hotKeys, String target){
        boolean flag = true;
        for(HotKey hotKey : hotKeys){
            if(levenshtein(hotKey.getContent(), target) >= 0.9){
                flag = false;
            }
        }
        return flag;
    }

    // 返回相似度[0~1]，大於0.9則視為相同關鍵字需近一步篩選
    public static Float levenshtein(String str1, String str2){
        // 1. 計算兩個字符串的長度
        int len1 = str1.length();
        int len2 = str2.length();
        // 2. 建立數組，比字符長度大一個空間
        int[][] matrix = new int[len1+1][len2+1];
        // 3. 分別為兩個字符串賦值到矩陣中
        for(int i = 0 ; i <= len1 ; i++){
            matrix[i][0] = i;
        }
        for(int i = 0 ; i <= len2 ; i++){
            matrix[0][i] = i;
        }
        // 4. 計算兩個字符是否一樣，計算左上的值
        int temp;
        for(int i = 1; i <= len1 ; i ++){
            for(int j = 1; j <= len2 ; j ++){
                // 判斷兩個字符是否一致
                if(str1.charAt(i-1) == str2.charAt(j - 1)){
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 取三個值中最小的
                matrix[i][j] = min(matrix[i-1][j-1] + temp, matrix[i][j-1]+1, matrix[i-1][j]+1);
            }
        }
        //System.out.println("str \""+str1+"\" and \""+str2+"\" compare");
        // 5. 取數組右下角的值，同樣不同位置代表不同字符串的比較
        //System.out.println("different steps : " + matrix[len1][len2]);
        // 6. 計算相似度
        float similarity = 1 - (float) matrix[len1][len2]/Math.max(str1.length(), str2.length());
        //System.out.println("similarity : " + similarity);
        return similarity;
    }

    private static int min(int... is){
        // int... 獲取未知長度的int
        int min = Integer.MAX_VALUE;
        for(int i : is){
            if(min > i){
                min = i;
            }
        }
        return min;
    }
}
