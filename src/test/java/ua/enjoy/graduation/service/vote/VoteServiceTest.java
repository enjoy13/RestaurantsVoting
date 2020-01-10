package ua.enjoy.graduation.service.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.enjoy.graduation.model.Vote;
import ua.enjoy.graduation.service.AbstractServiceTest;
import ua.enjoy.graduation.service.VoteService;
import ua.enjoy.graduation.util.exception.NotFoundException;
import ua.enjoy.graduation.util.exception.VotingSaveExceptions;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;
import static ua.enjoy.graduation.UserTestData.getERIC;
import static ua.enjoy.graduation.VoteTestData.*;
import static ua.enjoy.graduation.util.DateTimeUtil.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService voteService;

    @Test
    void getById() {
        getTestMatchers(Vote.class)
                .assertMatch(voteService.findById(getVoteTwo().getId()), getVoteTwo());
    }

    @Test
    void findAllByUserId() {
        getTestMatchers(Vote.class)
                .assertMatch(voteService.findAllByUserId(getVoteTwo().getUser().getId()), getAllByUserId());
    }

    @Test
    void findByUserIdNoFound() {
        assertThrows(NotFoundException.class, () ->
                voteService.findAllByUserId(0));
    }

    @Test
    void findAllByRestaurantId() {
        getTestMatchers(Vote.class)
                .assertMatch(voteService.findAllByRestaurantId(getRestaurantId()), getAllByRestaurantId());
    }

    @Test
    void findByRestaurantIdNoFound() {
        assertThrows(NotFoundException.class, () ->
                voteService.findAllByRestaurantId(0));
    }

    @Test
    void findAllByCreateDate() {
        List.of(getVoteOne(), getVoteTwo()).forEach(v -> voteService.create(v));
        getTestMatchers(Vote.class)
                .assertMatch(voteService.findAllByCreateDate(getLocalDateStart()), getAllByCreatedData());
    }

    @Test
    void findAllByCreateDateNotFound() {
        assertThrows(NotFoundException.class, () ->
                voteService.findAllByCreateDate(getInvalidLocalDate()));
    }

    @Test
    void findAllBetween() {
        List.of(getVoteOne(), getVoteTwo(), getVoteThree(), getVoteFour()).forEach(v -> voteService.create(v));
        getTestMatchers(Vote.class)
                .assertMatch(voteService.findAllBetween(getLocalDateStart(), getLocalDateEnd()),
                getAllByCreatedDataBetween());
    }

    @Test
    void findAllBetweenNotFound() {
        assertThrows(NotFoundException.class, () ->
                voteService.findAllBetween(getInvalidLocalDate(), getInvalidLocalDate()));
    }

    @Test
    void findAllBetweenWithUserId() {
        List.of(getVoteFive(), getVoteSix()).forEach(v -> voteService.create(v));
        getTestMatchers(Vote.class)
                .assertMatch(voteService.findAllBetweenWithUserId(getLocalDateStart(), getLocalDateEnd(),
                        getERIC().getId()), getAllByCreatedDataBetweenWithUserId());
    }

    @Test
    void findAllBetweenWithUserIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                voteService.findAllBetweenWithUserId(getInvalidLocalDate(), getInvalidLocalDate(), 0));
    }

    @Test
    void deleteById() {
        voteService.delete(getVoteOne().getId());
        assertThrows(NotFoundException.class, () ->
                voteService.findById(getVoteOne().getId()));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                voteService.delete(0));
    }

    @Test
    void create() {
        Vote newVote = getNewVote();
        Vote created = voteService.create(newVote);
        Integer newId = created.getId();
        newVote.setId(newId);
        getTestMatchers(Vote.class).assertMatch(created, newVote);
        getTestMatchers(Vote.class).assertMatch(voteService.findById(newId), newVote);
    }

    @Test
    void votingVotingSaveExceptions() {
        Vote newVote = getNewVote();
        newVote.setCreatedDate(getElevenLDT());
        voteService.create(newVote);
        assertThrows(VotingSaveExceptions.class, () ->
                voteService.voting(newVote.getRestaurant().getName(), newVote.getUser().getId()));
    }

    @Test
    void votingBeforeEleven() {
        Vote newVote = getNewVote();
        newVote.setCreatedDate(getBeforeElevenLDT());
        voteService.create(newVote);
        getTestMatchers(Vote.class).assertMatch(
                voteService.voting(newVote.getRestaurant().getName(), newVote.getUser().getId()), getNewVote());
    }

    @Test
    void votingFirstTime() {
        getTestMatchers(Vote.class).assertMatch(
                voteService.voting(getNewVote().getRestaurant().getName(), getNewVote().getUser().getId()),
                voteService.getVoteByCreatedDateBetweenAndUserId(LocalDate.now(), LocalDate.now(), getNewVote().getUser().getId()));
    }
}