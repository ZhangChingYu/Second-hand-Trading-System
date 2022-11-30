package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRespository extends JpaRepository<Booking, String> {

    void deleteByNumber(String number);


    List<Booking> findByBuyerId(String buyerId);

    Booking findByNumber(String number);

    Booking findByProductId(String productId);

    List<Booking> findAllBySellerId(String phone);

    List<Booking> findAllByBuyerId(String phone);
}
