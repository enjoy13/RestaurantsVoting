package ua.enjoy.graduation.controller.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.enjoy.graduation.controller.AbstractControllerTest;
import ua.enjoy.graduation.model.Vote;
import ua.enjoy.graduation.service.VoteService;
import ua.enjoy.graduation.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;
import static ua.enjoy.graduation.UserTestData.*;
import static ua.enjoy.graduation.VoteTestData.*;
import static ua.enjoy.graduation.controller.AdminVoteRestController.ADMIN_VOTE_URL;
import static ua.enjoy.graduation.util.DateTimeUtil.getInvalidLocalDate;
import static ua.enjoy.graduation.util.DateTimeUtil.localDateToString;

class AdminVoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService voteService;

    AdminVoteRestControllerTest() {
        super(ADMIN_VOTE_URL);
    }

    @Test
    void getById() throws Exception {
        perform(doGet(getVoteTwo().getId())
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> getTestMatchers(Vote.class).contentJson(getVoteTwo()));
    }

    @Test
    void findByIdNotFound() throws Exception {
        perform(doGet(0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllByUserId() throws Exception {
        perform(doGet("user/" + getUserId())
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> getTestMatchers(Vote.class).contentJson(getAllByUserId()));
    }

    @Test
    void findAllByUserIdNoFound() throws Exception {
        perform(doGet("user/" + 0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllByRestaurantId() throws Exception {
        perform(doGet("restaurant/" + getRestaurantId())
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> getTestMatchers(Vote.class).contentJson(getAllByRestaurantId()));
    }

    @Test
    void findAllByRestaurantIdNotFound() throws Exception {
        perform(doGet("restaurant/" + 0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findByCreateDate() throws Exception {
        List.of(getVoteOne(), getVoteTwo()).forEach(v -> voteService.create(v));
        perform(doGet("byDate")
                .basicAuth(getADMIN())
                .unwrap()
                .param("date", localDateToString(getLocalDateStart())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Vote.class).contentJson(getAllByCreatedData()));
    }

    @Test
    void findByCreateDateNotFound() throws Exception {
        List.of(getVoteOne(), getVoteTwo()).forEach(v -> voteService.create(v));
        perform(doGet("byDate")
                .basicAuth(getADMIN())
                .unwrap()
                .param("date", localDateToString(getInvalidLocalDate())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findByToday() throws Exception {
        getAllCreateToday().forEach(vote -> voteService.create(vote));
        perform(doGet("today")
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result ->
                        getTestMatchers(Vote.class).contentJson(getAllCreateToday()));
    }

    @Test
    void findByTodayNotFound() throws Exception {
        voteService.findAllByCreateDate(LocalDate.now()).forEach(v -> voteService.delete(v.getId()));
        perform(doGet("today")
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllBetween() throws Exception {
        List.of(getVoteOne(), getVoteTwo(), getVoteThree(), getVoteFour()).forEach(v -> voteService.create(v));
        perform(doGet("filter")
                .basicAuth(getADMIN())
                .unwrap()
                .param("startDate", localDateToString(getLocalDateStart()))
                .param("endDate", localDateToString(getLocalDateEnd())))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Vote.class).contentJson(getAllByCreatedDataBetween()));
    }

    @Test
    void findAllBetweenNotFound() throws Exception {
        perform(doGet("filter")
                .basicAuth(getADMIN())
                .unwrap()
                .param("startDate", localDateToString(getLocalDateStart()))
                .param("endDate", localDateToString(getLocalDateEnd())))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllBetweenWithUserId() throws Exception {
        List.of(getVoteFive(), getVoteSix()).forEach(v -> voteService.create(v));
        perform(doGet("filterId")
                .basicAuth(getADMIN())
                .unwrap()
                .param("startDate", localDateToString(getLocalDateStart()))
                .param("endDate", localDateToString(getLocalDateEnd()))
                .param("userId", getERIC().getId().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Vote.class).contentJson(getAllByCreatedDataBetween()));
    }

    @Test
    void findAllBetweenWithUserIdNotFound() throws Exception {
        List.of(getVoteFive(), getVoteSix()).forEach(v -> voteService.create(v));
        perform(doGet("filterId")
                .basicAuth(getADMIN())
                .unwrap()
                .param("startDate", localDateToString(getLocalDateStart()))
                .param("endDate", localDateToString(getLocalDateEnd()))
                .param("userId", "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauth() throws Exception {
        perform(doGet(getVoteOne().getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(doGet(getVoteOne().getId())
                .basicAuth(getUSER()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteById() throws Exception {
        perform(doDelete(getVoteOne().getId())
                .basicAuth(getADMIN()))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> voteService.findById(getVoteOne().getId()));
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        perform(doDelete(0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}