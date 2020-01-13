package ua.enjoy.graduation.controller.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.enjoy.graduation.controller.AbstractControllerTest;
import ua.enjoy.graduation.model.Restaurant;
import ua.enjoy.graduation.service.RestaurantService;
import ua.enjoy.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.enjoy.graduation.RestaurantTestData.*;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;
import static ua.enjoy.graduation.TestUtil.readFromJson;
import static ua.enjoy.graduation.UserTestData.getADMIN;
import static ua.enjoy.graduation.UserTestData.getUSER;
import static ua.enjoy.graduation.controller.AdminsRestaurantsRestController.ADMIN_RESTOURANTS_URL;

class AdminsRestaurantsRestControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    AdminsRestaurantsRestControllerTest() {
        super(ADMIN_RESTOURANTS_URL);
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
        perform(doDelete(getRestaurantOne().getId())
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.findById(getRestaurantOne().getId()));
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
        Restaurant newRestaurant = new Restaurant("newRestaurant");
        ResultActions action = perform(doPost()
                .jsonBody(newRestaurant)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        Integer newId = created.getId();
        newRestaurant.setId(newId);

        getTestMatchers(Restaurant.class).assertMatch(created, newRestaurant);
        getTestMatchers(Restaurant.class).assertMatch(restaurantService.findById(newId), newRestaurant);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        perform(doPost()
                .jsonBody(new Restaurant(getRestaurantOne().getName()))
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        perform(doPut(getRestaurantOne().getId())
                .jsonBody(getUpdateRestaurant())
                .basicAuth(getADMIN())
                .unwrap())
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(result -> getTestMatchers(Restaurant.class).contentJson(getUpdateRestaurant()));
    }

    @Test
    void updateWithNotFoundRestaurantId() throws Exception {
        perform(doPut(0)
                .jsonBody(getUpdateRestaurant())
                .basicAuth(getADMIN())
                .unwrap())
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}