package dev.silvia.wechattrade.handlers.promote;

import dev.silvia.wechattrade.entity.ProductCatalog;
import lombok.Data;

@Data
public class CatalogWeight {
    private ProductCatalog catalog;
    private Double weight;
}
