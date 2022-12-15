package dev.silvia.wechattrade.handlers.promote;

import dev.silvia.wechattrade.entity.Product;
import lombok.Data;

@Data
public class GradeProduct {
    private Product product;
    private Double grade;
}
