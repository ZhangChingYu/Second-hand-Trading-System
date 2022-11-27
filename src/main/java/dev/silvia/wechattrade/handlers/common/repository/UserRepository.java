package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // SELECT * FROM User WHERE phone = ${phone}
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
}
