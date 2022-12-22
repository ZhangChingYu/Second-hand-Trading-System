package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.ExchangeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeInfoRepository extends JpaRepository<ExchangeInfo, String> {
    ExchangeInfo findByNumber(String number);


    ExchangeInfo findByNumberAndStatus(String exchangeId, String status);

    List<ExchangeInfo> findByNameLike(String name);

    List<ExchangeInfo> findByProductNum(String number);

    List<ExchangeInfo> findByProductNumAndStatus(String number, String status);
}
