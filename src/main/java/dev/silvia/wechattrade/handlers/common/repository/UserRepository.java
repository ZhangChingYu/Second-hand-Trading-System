package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // SELECT * FROM User WHERE phone = ${phone}
    Optional<User> findByPhone(String phone);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);
    List<User> findByViolationCountGreaterThanEqual(Integer violationCount);

    List<User> findByRealNameLike(String realName);
    List<User> findByUserNameLike(String userName);

    List<User> findByAuthority(Integer authority);
    List<User> findByAuthority(Integer authority,Sort sort);

    List<User> findByViolationCountBetween(Integer lower, Integer upper);
}
