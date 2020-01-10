package ua.enjoy.graduation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.enjoy.graduation.model.Vote;
import ua.enjoy.graduation.repository.VoteRepository;
import ua.enjoy.graduation.util.exception.VotingSaveExceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ua.enjoy.graduation.util.DateTimeUtil.*;
import static ua.enjoy.graduation.util.ValidationUtil.*;

@Service("voteService")
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;

    private final RestaurantService restaurantService;

    private final UserService userService;

    public VoteService(VoteRepository voteRepository, RestaurantService restaurantService, UserService userService) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    public Vote findById(int id) {
        Assert.notNull(id, "id must not be null");
        log.info("method findById: id = {}", id);
        return checkNotFound(voteRepository.findById(id), "id = " + id);
    }

    public List<Vote> findAllByUserId(int userId) {
        Assert.notNull(userId, "userId must not be null");
        List<Vote> getAllByUserId = voteRepository.findAllByUserId(userId);
        log.info("method findByUserId: userId = {}, vote = {}", userId, getAllByUserId);
        return checkEmptyList(getAllByUserId, "userId = " + userId);
    }

    public List<Vote> findAllByRestaurantId(int restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        List<Vote> getAllByRestaurantId = voteRepository.findAllByRestaurantId(restaurantId);
        log.info("method findAllByRestaurantId: restaurantId = {}, getAllByRestaurantId = {}", restaurantId, getAllByRestaurantId);
        return checkEmptyList(getAllByRestaurantId, "restaurantId = " + restaurantId);
    }

    public List<Vote> findAllByCreateDate(LocalDate ld) {
        Assert.notNull(ld, "LocalDate must not be null");
        List<Vote> voteByCreateDate = voteRepository.findAllByCreatedDateBetween(getStartInclusive(ld), getEndExclusive(ld));
        log.info("method findByCreateDate: ldt = {}, vote = {}", ld, voteByCreateDate);
        return checkEmptyList(voteByCreateDate, "ldt = " + ld);
    }

    public List<Vote> findAllBetween(LocalDate startDate, LocalDate endDate) {
        List<Vote> voteByBetweenDate = voteRepository.findAllByCreatedDateBetween(getStartInclusive(startDate), getEndExclusive(endDate));
        log.info("method findAllBetween: startDate = {}, endDate = {}, voteByBetweenDate = {}", startDate, endDate, voteByBetweenDate);
        return checkEmptyList(voteByBetweenDate, "startDate = " + startDate + " endDate = " + endDate);
    }

    public List<Vote> findAllBetweenWithUserId(LocalDate startDate, LocalDate endDate, int userId) {
        List<Vote> voteByBetweenDateWithUserId = getAllByCreatedDateBetweenAndUserId(startDate, endDate, userId);
        log.info("method findAllBetweenWithUserId: startDate = {}, endDate = {}, voteByBetweenDate = {}", startDate, endDate, voteByBetweenDateWithUserId);
        return checkEmptyList(voteByBetweenDateWithUserId, "startDate = " + startDate + " endDate = " + endDate + " userId = " + userId);
    }

    private List<Vote> getAllByCreatedDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, int userId) {
        return voteRepository.findAllByCreatedDateBetweenAndUserId(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }

    public Vote getVoteByCreatedDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, int userId) {
        return voteRepository.findFirstByCreatedDateBetweenAndUserId(getStartInclusive(startDate), getEndExclusive(endDate), userId);
    }

    public void delete(int id) {
        Assert.notNull(id, "id must not be null");
        log.info("method delete: id = {}", id);
        checkNotFound(voteRepository.findById(id), "id = " + id);
        voteRepository.deleteById(id);
    }

    public Vote create(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        log.info("method create vote = {}", vote);
        return voteRepository.save(vote);
    }

    public Vote voting(String name, int userId) {
        Assert.notNull(name, "name must not be null");
        Vote voteByUserId = getVoteByCreatedDateBetweenAndUserId(LocalDate.now(), LocalDate.now(), userId);

        Vote vote= null;

        if (Objects.isNull(voteByUserId)) {
            vote = Vote.builder()
                    .user(userService.findById(userId))
                    .restaurant(restaurantService.findByName(name))
                    .build();
            return create(vote);
        } else if (isBefore(voteByUserId.getCreatedDate())) {
            vote = Vote.builder().id(voteByUserId.getId())
                    .restaurant(voteByUserId.getRestaurant())
                    .user(voteByUserId.getUser())
                    .build();
            vote.setCreatedDate(LocalDateTime.now());
            return create(vote);
        } else throw new VotingSaveExceptions("Your voice is already saved! Re-voting is impossible today!");
    }
}
