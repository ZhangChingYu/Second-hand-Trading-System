package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    Booking findByNumber(String number);

    List<Booking> findByProductId(String productId);

    List<Booking> findByBuyerIdAndStatus(String sellerId, String status);

    List<Booking> findBySellerIdAndStatus(String sellerId, String status);

    List<Booking> findByStatus(String status);

    List<Booking> findBySellerId(String sellerId);

    List<Booking> findByBuyerId(String phone);

    List<Booking> findByNameLike(String name);

    Booking findByProductIdAndSellerId(String number, String phone);

    List<Booking> findByProductIdAndStatus(String number,String status);
}
