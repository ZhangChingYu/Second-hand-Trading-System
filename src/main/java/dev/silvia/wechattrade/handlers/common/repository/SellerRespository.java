package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRespository extends JpaRepository<Seller, String> {

    Seller findByExchangeId(String number);

    List<Seller> findAllByPhone(String phone);

    void deleteByExchangeId(String number);
}

