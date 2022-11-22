package dev.silvia.wechattrade.handlers;

import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserNameGenerator {    // 隨機生成用戶暱稱工具
    public static final Integer ENGLISH_LENGTH_UPPER_BOUND = 6;
    public static final Integer ENGLISH_LENGTH_LOWER_BOUND = 4;
    public static final Integer NUMBER_LENGTH_UPPER_BOUND = 4;
    public static final Integer NUMBER_LENGTH_LOWER_BOUND = 2;
    public static final char[] STR = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
    'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public String generateRandomName(String userName, List<String> nameList){
        if(userName.isEmpty()){     // 若用戶沒有暱稱
            // 隨機生成的暱稱由4~6個英文字母(有大小寫之分)+2~4個數字隨機組成
            boolean isRepeat = true;
            while (isRepeat){   // 當生成的隨機暱稱與數據庫中數據重複時
                // 1. 決定英文字母位數
                int e_size = (int)(Math.random()*(ENGLISH_LENGTH_UPPER_BOUND - ENGLISH_LENGTH_LOWER_BOUND + 1)) + ENGLISH_LENGTH_LOWER_BOUND;
                // 2. 決定數字位數
                int n_size = (int)(Math.random()*(NUMBER_LENGTH_UPPER_BOUND - NUMBER_LENGTH_LOWER_BOUND + 1)) + NUMBER_LENGTH_LOWER_BOUND;
                // 3. 根據決定好的位數生成相應長度的隨機字串
                String english_part = "";
                String number_part = "";
                for(int i = 0; i < e_size; i++){ // 0 ~ 51
                    int position = (int)(Math.random()*(51 - 0 + 1)) + 0;
                    english_part += STR[position];
                }
                for(int i = 0; i< n_size; i++){ // 0 ~ 9
                    int n = (int)(Math.random()*(9 - 0 + 1)) + 0;
                    number_part += n;
                }
                userName = english_part + number_part;
                english_part = "";
                number_part = "";
                isRepeat = checkRepeatName(userName, nameList);
            }
            return userName;
        }
        return userName;
    }
    public boolean checkRepeatName(String userName, List<String> nameList){
        for(int i = 0; i<nameList.size(); i++){
            if(userName == nameList.get(i)){
                return true;
            }
        }
        return false;
    }
}
