package dev.silvia.wechattrade.handlers.common.repository;

import dev.silvia.wechattrade.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    void deleteByNumber(String number);
}
