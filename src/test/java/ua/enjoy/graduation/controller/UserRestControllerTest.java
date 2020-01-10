package ua.enjoy.graduation.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.enjoy.graduation.model.Restaurant;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.model.Vote;
import ua.enjoy.graduation.service.RestaurantService;
import ua.enjoy.graduation.service.UserService;
import ua.enjoy.graduation.service.VoteService;
import ua.enjoy.graduation.to.MenuTo;
import ua.enjoy.graduation.to.UserTo;
import ua.enjoy.graduation.util.UserUtil;
import ua.enjoy.graduation.util.exception.VotingSaveExceptions;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.enjoy.graduation.DishTestData.getDishes;
import static ua.enjoy.graduation.RestaurantTestData.*;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;
import static ua.enjoy.graduation.TestUtil.readFromJson;
import static ua.enjoy.graduation.UserTestData.*;
import static ua.enjoy.graduation.VoteTestData.getNewVote;
import static ua.enjoy.graduation.controller.UserRestController.USERS_REST_URL;
import static ua.enjoy.graduation.util.DateTimeUtil.getBeforeElevenLDT;
import static ua.enjoy.graduation.util.DateTimeUtil.getElevenLDT;
import static ua.enjoy.graduation.util.MenuToUtil.getMenuTo;

class UserRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    UserRestControllerTest() {
        super(USERS_REST_URL);
    }

    @Test
    void getAllRestaurants() throws Exception {
        perform(doGet("restaurants")
                .basicAuth(getUSER()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Restaurant.class)
                        .assertMatch(restaurantService.getAll(), getRestaurans()));
    }

    @Test
    void getMenuByRestaurantNameToday() throws Exception {
        perform(doGet("menu/" + getRestaurantName())
                .basicAuth(getUSER()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(MenuTo.class)
                        .contentJson(getMenuTo(getRestaurantThree(), getDishes())));
    }

    @Test
    void getMenuByRestaurantNameNotFoundToday() throws Exception {
        perform(doGet("menu/skfsdflsjdf")
                .basicAuth(getUSER()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void votingBeforeEleven() throws Exception {
        Vote newVote = getNewVote();
        newVote.setCreatedDate(getBeforeElevenLDT());

        ResultActions action = perform(doPost("voting/")
                .jsonBody(newVote)
                .basicAuth(getUSER())
                .unwrap()
                .param("restaurantName", newVote.getRestaurant().getName()))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        newVote.setId(readFromJson(action, Vote.class).getId());
        getTestMatchers(Vote.class).assertMatch(newVote, getNewVote());
    }

    @Test
    void votingFirstTime() throws Exception {
        perform(doPost("voting/")
                .jsonBody(getNewVote())
                .basicAuth(getUSER())
                .unwrap()
                .param("restaurantName", getNewVote().getRestaurant().getName()))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result ->  getTestMatchers(Vote.class).assertMatch(
                        voteService.voting(getNewVote().getRestaurant().getName(), getNewVote().getUser().getId()),
                        voteService.getVoteByCreatedDateBetweenAndUserId(LocalDate.now(), LocalDate.now(), getNewVote().getUser().getId())));
    }

    @Test
    void votingVotingSaveExceptions() throws Exception {
        Vote newVote = getNewVote();
        newVote.setCreatedDate(getElevenLDT());

        perform(doPost("voting/")
                .jsonBody(newVote)
                .basicAuth(getHARRY())
                .unwrap()
                .param("restaurantName", newVote.getRestaurant().getName()))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        assertThrows(VotingSaveExceptions.class, () ->
                voteService.voting(newVote.getRestaurant().getName(), newVote.getUser().getId()));
    }

    @Test
    void registerWithAnonymous() throws Exception {
        UserTo newUserTo = getNewUserTo();
        ResultActions action = perform(doPost("register")
                .jsonBody(newUserTo))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        User user = UserUtil.createNewFromTo(newUserTo);
        Integer newId = created.getId();
        user.setId(newId);

        getTestMatchers(User.class).assertMatch(created, user);
        getTestMatchers(User.class).assertMatch(userService.findById(newId), user);
    }

    @Test
    void registerWithUser() throws Exception {
        perform(doPost("register")
                .basicAuth(getUSER())
                .jsonBody(getNewUserTo()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void registerWithAdmin() throws Exception {
        perform(doPost("register")
                .basicAuth(getADMIN())
                .jsonBody(getNewUserTo()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getUnauth() throws Exception {
        perform(doGet())
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}