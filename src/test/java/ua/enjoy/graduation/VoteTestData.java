package ua.enjoy.graduation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.enjoy.graduation.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ua.enjoy.graduation.RestaurantTestData.*;
import static ua.enjoy.graduation.UserTestData.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteTestData {

    private static final Vote VOTE_ONE = new Vote(1, getADMIN(), getRestaurantTwo());
    private static final Vote VOTE_TWO = new Vote(2, getUSER(), getRestaurantFive());
    private static final Vote VOTE_THREE = new Vote(3, getUSER(), getRestaurantSix());
    private static final Vote VOTE_FOUR = new Vote(4, getSERGIO(), getRestaurantSix());
    private static final Vote VOTE_FIVE = new Vote(5, getERIC(), getRestaurantSix());
    private static final Vote VOTE_SIX = new Vote(6, getERIC(), getRestaurantFour());
    private static final Vote newVote = new Vote(getHARRY(), getRestaurantTen());

    private static final List<Vote> allByUserId = List.of(VOTE_TWO, VOTE_THREE);
    private static final List<Vote> allByRestaurantId = List.of(VOTE_THREE, VOTE_FOUR, VOTE_FIVE);
    private static final List<Vote> allByCreatedData = List.of(VOTE_ONE, VOTE_TWO);
    private static final List<Vote> allByCreatedDataBetween = List.of(VOTE_ONE, VOTE_TWO, VOTE_THREE, VOTE_FOUR);
    private static final List<Vote> allByCreatedDataBetweenWithUserId = List.of(VOTE_FIVE, VOTE_SIX);

    private static final LocalDate localDateStart = getVoteOne().getCreatedDate().toLocalDate();
    private static final LocalDate localDateEnd = getVoteFour().getCreatedDate().toLocalDate();

    private static final Integer restaurantId = VOTE_THREE.getRestaurant().getId();

    public static Integer getRestaurantId() {
        return restaurantId;
    }

    private static final Integer userId = VOTE_TWO.getUser().getId();

    public static Vote getVoteOne() {
        VOTE_ONE.setCreatedDate(LocalDateTime.of(2020, 01, 01, 01,00));
        return VOTE_ONE;
    }

    public static Vote getVoteTwo() {
        VOTE_TWO.setCreatedDate(LocalDateTime.of(2020, 01, 01, 02,00));
        return VOTE_TWO;
    }

    public static Vote getVoteThree() {
        VOTE_THREE.setCreatedDate(LocalDateTime.of(2020, 01, 02, 03,00));
        return VOTE_THREE;
    }

    public static Vote getVoteFour() {
        VOTE_FOUR.setCreatedDate(LocalDateTime.of(2020, 01, 02, 04,00));
        return VOTE_FOUR;
    }

    public static Vote getVoteFive() {
        VOTE_FIVE.setCreatedDate(LocalDateTime.of(2020, 01, 01, 04,00));
        return VOTE_FIVE;
    }

    public static Vote getVoteSix() {
        VOTE_SIX.setCreatedDate(LocalDateTime.of(2020, 01, 02, 04,00));
        return VOTE_SIX;
    }

    public static Vote getNewVote() {
        return newVote;
    }

    public static List<Vote> getAllByUserId() {
        return allByUserId;
    }

    public static List<Vote> getAllByRestaurantId() {
        return allByRestaurantId;
    }

    public static List<Vote> getAllByCreatedData() {
        return allByCreatedData;
    }

    public static List<Vote> getAllByCreatedDataBetween() {
        return allByCreatedDataBetween;
    }

    public static List<Vote> getAllCreateToday() {
        List<Vote> votes = List.of(VOTE_ONE, VOTE_TWO);
        votes.forEach(v -> v.setCreatedDate(LocalDateTime.now()));
        return votes;
    }

    public static List<Vote> getAllByCreatedDataBetweenWithUserId() {
        return allByCreatedDataBetweenWithUserId;
    }

    public static LocalDate getLocalDateStart() {
        return localDateStart;
    }

    public static LocalDate getLocalDateEnd() {
        return localDateEnd;
    }

    public static Integer getUserId() {
        return userId;
    }
}
