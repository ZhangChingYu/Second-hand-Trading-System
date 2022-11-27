package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRespository extends JpaRepository<Booking, String> {

}
