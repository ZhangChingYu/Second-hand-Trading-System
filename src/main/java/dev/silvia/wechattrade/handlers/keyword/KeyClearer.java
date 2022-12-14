package dev.silvia.wechattrade.handlers.keyword;

import dev.silvia.wechattrade.entity.HotKey;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
@Component
public class KeyClearer {   // 清理HotKey的工具

    // 返回需要刪除的HotKey List，若沒有需要刪除則為null
    public List<HotKey> getClearList(List<HotKey> hotKeys){
        List<HotKey> targets = new ArrayList<>();
        if(hotKeys.size()<=1000){   // 不需要刪除
            return null;
        }
        // 若超過1000則選出500個做刪除
        while (targets.size()<500){
            Map<String, Object> map = clearKey(hotKeys);
            targets.add((HotKey) map.get("key"));     // 刪除列隊中加入hotkey
            hotKeys.remove((int) map.get("index"));  // 將加入的hotkey從原對列中移除以免重複
        }
        return targets;
    }

    private static Map<String, Object> clearKey(List<HotKey> hotKeys){
        Map<String, Object> map = new TreeMap<>();
        HotKey target = hotKeys.get(0);
        int index = 0;
        for(int i = 1 ; i < hotKeys.size() ; i ++){ // 最近點擊日期最久的key要被刪除
            if(target.getRecentDate().getTime() > hotKeys.get(i).getRecentDate().getTime()) {
                target = hotKeys.get(i);
                index = i;
            }
        }
        map.put("key", target);
        map.put("index", index);
        return map;
    }
}
