package ua.enjoy.graduation.controller.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ua.enjoy.graduation.controller.AbstractControllerTest;
import ua.enjoy.graduation.model.Dish;
import ua.enjoy.graduation.service.DishService;
import ua.enjoy.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.enjoy.graduation.DishTestData.*;
import static ua.enjoy.graduation.RestaurantTestData.*;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;
import static ua.enjoy.graduation.TestUtil.readFromJson;
import static ua.enjoy.graduation.UserTestData.getADMIN;
import static ua.enjoy.graduation.UserTestData.getUSER;
import static ua.enjoy.graduation.controller.AdminDishRestController.ADMIN_DISHES_URL;

class AdminDishRestControllerTest extends AbstractControllerTest {

    @Autowired
    private DishService dishService;

    AdminDishRestControllerTest() {
        super(ADMIN_DISHES_URL);
    }

    @Test
    void findById() throws Exception {
        perform(doGet(getDishFirst().getId())
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Dish.class).contentJson(getDishFirst()));
    }

    @Test
    void findByIdNotFound() throws Exception {
        perform(doGet(0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllByRestaurantIdToday() throws Exception {
        perform(doGet("restaurant/" + getRestaurantThree().getId())
                .basicAuth(getADMIN()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Dish.class).contentJson(getDishes()));
    }

    @Test
    void getAllByNotFoundRestaurantIdToday() throws Exception {
        perform(doGet("restaurant/" + 0)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnauth() throws Exception {
        perform(doGet(getDishFirst().getId()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(doGet(getDishFirst().getId())
                .basicAuth(getUSER()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void findByName() throws Exception {
        perform(doGet("byName")
                .basicAuth(getADMIN())
                .unwrap()
                .param("name", getDishFirst().getName()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Dish.class).contentJson(getDishFirst()));
    }

    @Test
    void findByNameNotFound() throws Exception {
        perform(doGet("byName")
                .basicAuth(getADMIN())
                .unwrap()
                .param("name", "sdfsdfsdfsdf"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void findByPrice() throws Exception {
        perform(doGet("byPrice")
                .basicAuth(getADMIN())
                .unwrap()
                .param("price", getDishSecond().getPrice().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> getTestMatchers(Dish.class).contentJson(getDishSecond()));
    }

    @Test
    void findByPriceNotFound() throws Exception {
        perform(doGet("byPrice")
                .basicAuth(getADMIN())
                .unwrap()
                .param("price", "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteById() throws Exception {
        perform(doDelete(getDishFirst().getId())
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.findById(getDishFirst().getId()));
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
        Dish newDish = getNewDish();
        ResultActions action = perform(doPost(getRestaurantFive().getId())
                .jsonBody(newDish)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        Integer newId = created.getId();
        newDish.setId(newId);

        getTestMatchers(Dish.class).assertMatch(created, newDish);
        getTestMatchers(Dish.class).assertMatch(dishService.findById(newId), newDish);
    }

    @Test
    void duplicateNameCreate() throws Exception {
        perform(doPost(getRestaurantOne().getId())
                .jsonBody(getDishWithDuplicateName())
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void createWithNotFoundByRestaurantId() throws Exception {
        Dish newDish = getNewDish();
        perform(doPost(0)
                .jsonBody(newDish)
                .basicAuth(getADMIN()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void update() throws Exception {
        perform(doPut(getDishFirst().getId())
                .jsonBody(getUpdateDish())
                .basicAuth(getADMIN())
                .unwrap()
                .param("restaurantId", getRestaurantOne().getId().toString()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(result -> getTestMatchers(Dish.class).contentJson(getUpdateDish()));
    }

    @Test
    void updateWithNotFoundDishId() throws Exception {
        perform(doPut(0)
                .jsonBody(getUpdateDish())
                .basicAuth(getADMIN())
                .unwrap()
                .param("restaurantId", getRestaurantOne().getId().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWithNotFoundRestaurantId() throws Exception {
        perform(doPut(getDishFirst().getId())
                .jsonBody(getUpdateDish())
                .basicAuth(getADMIN())
                .unwrap()
                .param("restaurantId", "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}