package dev.silvia.wechattrade.handlers.keyword;

import dev.silvia.wechattrade.entity.HotKey;
import lombok.Data;

@Data
public class KeySet {   // 暫存關鍵詞和頻率的對應
    private HotKey key;
    private Double frequency;
}
