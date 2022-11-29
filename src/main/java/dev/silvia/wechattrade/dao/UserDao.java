package dev.silvia.wechattrade.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dev.silvia.wechattrade.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

@Mapper
@Primary
public interface UserDao extends BaseMapper<User> {
}
