package ua.enjoy.graduation.controller.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.enjoy.graduation.UserTestData;
import ua.enjoy.graduation.controller.AbstractControllerTest;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.service.UserService;
import ua.enjoy.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.enjoy.graduation.RestaurantTestData.getRestaurantOne;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;
import static ua.enjoy.graduation.TestUtil.readFromJson;
import static ua.enjoy.graduation.UserTestData.*;
import static ua.enjoy.graduation.controller.AdminUsersRestController.ADMIN_USERS_URL;

class AdminUsersRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    AdminUsersRestControllerTest() {
        super(ADMIN_USERS_URL);
    }

    @Test
    void getAll() throws Exception {
        perform(doGet()
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(User.class)
                        .assertMatch(userService.getAll(), UserTestData.getUsers()));
    }

    @Test
    void getById() throws Exception {
        perform(doGet(getADMIN().getId())
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(result -> getTestMatchers(User.class).contentJson(getADMIN()));
    }

    @Test
    void findByIdNotFound() throws Exception {
        perform(doGet(0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findByName() throws Exception {
        perform(doGet("byName")
                .basicAuth(getADMIN())
                .unwrap()
                .param("name", getADMIN().getName()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(User.class).contentJson(getADMIN()));
    }

    @Test
    void findByEmail() throws Exception {
        perform(doGet("byEmail")
                .basicAuth(getADMIN())
                .unwrap()
                .param("email", getADMIN().getEmail()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(User.class).contentJson(getADMIN()));
    }

    @Test
    void getUnauth() throws Exception {
        perform(doGet(getRestaurantOne().getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(doGet(getRestaurantOne().getId())
                .basicAuth(getUSER()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteById() throws Exception {
        perform(doDelete(getUSER().getId())
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> userService.findById(getUSER().getId()));
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        perform(doDelete(0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void create() throws Exception {
        User newUser = getNewUser();
        ResultActions action = perform(doPost()
                .jsonBody(newUser)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        User created = readFromJson(action, User.class);
        Integer newId = created.getId();
        newUser.setId(newId);

        getTestMatchers(User.class).assertMatch(created, newUser);
        getTestMatchers(User.class).assertMatch(userService.findById(newId), newUser);
    }

    @Test
    void duplicateEmailCreate() throws Exception {
        perform(doPost()
                .jsonBody(getUserWithDuplicateEmail())
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        perform(doPut(getUSER().getId())
                .jsonBody(getUpdateUser())
                .basicAuth(getADMIN())
                .unwrap())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> getTestMatchers(User.class)
                        .assertMatch(userService.findById(getUSER().getId()), getUpdateUser()));
    }

    @Test
    void updateWithNotFoundId() throws Exception {
        perform(doPut(0)
                .jsonBody(getUpdateUser())
                .basicAuth(getADMIN())
                .unwrap())
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}