package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.ExchangeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ExchangeInfoRespository extends JpaRepository<ExchangeInfo, String> {
    Optional<ExchangeInfo> findById(Integer id);
    Optional<ExchangeInfo> deleteById(Integer id);

    List<ExchangeInfo> findAllById(Integer id);

    Optional<ExchangeInfo> findByNumber(String number);

    void deleteByNumber(String number);
}