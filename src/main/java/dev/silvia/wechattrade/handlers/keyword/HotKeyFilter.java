package dev.silvia.wechattrade.handlers.keyword;

import dev.silvia.wechattrade.entity.HotKey;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class HotKeyFilter {  // 熱門搜索關鍵詞工具
    // 返回通過篩選的關鍵詞
    public List<HotKey> ChosenKeys(List<HotKey> hotKeys){
        List<HotKey> chosenKeys = new ArrayList<>();
        List<KeySet> keySets = new ArrayList<>();
        for(HotKey hotKey: hotKeys){
            KeySet keySet = new KeySet();
            keySet.setKey(hotKey);
            keySet.setFrequency(getClickFrequency(hotKey.getCreateDate(),hotKey.getRecentDate(),hotKey.getClickCount()));
            keySets.add(keySet);
        }
        // 將KeySet打散(為了讓其他高頻關鍵字也有機會)
        Collections.shuffle(keySets);
        // 按照frequency大到小排序
        keySets = keySets.stream().sorted(Comparator.comparingDouble(KeySet::getFrequency).reversed()).collect(Collectors.toList());
        if(keySets.size()<=5){  // 若不大於5直接返回
            for(KeySet keySet: keySets){
                chosenKeys.add(keySet.getKey());
            }
        }else {
            for(int i = 0 ; i < 5 ; i++){
                chosenKeys.add(keySets.get(i).getKey());
            }
        }
        return chosenKeys;
    }
    // 計算時間間隔(天)
    public static int betweenDays(Date start, Date end) {
        Long start_time = start.getTime();
        Long end_time = end.getTime();
        int days = (int) ((end_time-start_time)/(1000*60*60*24));
        return days+1;  // 以免出現0(當天生成的關鍵字)
    }
    // 計算點擊頻率(click frequency)
    public static Double getClickFrequency(Date start, Date end, Integer clicks){
        int days = betweenDays(start, end);
        Double frequency = (double) clicks / (double) days;  // 數字越大，表示越頻繁
        return frequency;
    }

}
