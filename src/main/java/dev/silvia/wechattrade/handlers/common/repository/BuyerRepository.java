package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, String> {
    Buyer findByExchangeId(String number);
    List<Buyer> findAllByPhone(String phone);

    List<Buyer> findByExchangeIdAndPhone(String number, String phone);
}
