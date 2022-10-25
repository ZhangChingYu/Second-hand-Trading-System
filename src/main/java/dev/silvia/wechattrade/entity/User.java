package dev.silvia.wechattrade.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {
    private static final long serialVersionUID=1L;
    private int id;

}
