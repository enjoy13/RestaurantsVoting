package ua.enjoy.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.enjoy.graduation.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    Vote save(Vote vote);

    Vote findById(int id);

    List<Vote>findAllByUserId(int userId);

    List<Vote> findAllByRestaurantId(int restaurantId);

    List<Vote> findAllByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Vote> findAllByCreatedDateBetweenAndUserId(LocalDateTime startDate, LocalDateTime endDate, int userId);

    Vote findFirstByCreatedDateBetweenAndUserId(LocalDateTime startDate, LocalDateTime endDate, int userId);

    @Transactional
    void deleteById(int id);
}
