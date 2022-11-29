package dev.silvia.wechattrade.handlers;

import dev.silvia.wechattrade.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CheckUserAuthority {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean isAuthorized(String phone){
        String findUser = "select * from user_info where phone='"+phone+"'";
        User user = jdbcTemplate.queryForObject(findUser, new BeanPropertyRowMapper<>(User.class));
        boolean authorization = false;
        if(user.getAuthority() == 0){
            authorization = true;
        }
        return authorization;
    }
}
