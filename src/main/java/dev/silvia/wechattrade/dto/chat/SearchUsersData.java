package dev.silvia.wechattrade.dto.chat;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchUsersData {
    private Integer uid;
    private String username;
    private String telephone;
    private String email;
    private int sex;
    private String picture;
    private boolean isOnline;
}