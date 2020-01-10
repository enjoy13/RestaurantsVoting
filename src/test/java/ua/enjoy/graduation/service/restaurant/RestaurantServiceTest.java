package ua.enjoy.graduation.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ua.enjoy.graduation.model.Restaurant;
import ua.enjoy.graduation.service.AbstractServiceTest;
import ua.enjoy.graduation.service.RestaurantService;
import ua.enjoy.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.enjoy.graduation.DishTestData.getDishFirst;
import static ua.enjoy.graduation.DishTestData.getUpdateDish;
import static ua.enjoy.graduation.RestaurantTestData.*;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getAll() {
        getTestMatchers(Restaurant.class)
                .assertMatch(restaurantService.getAll(), getRestaurans());
    }

    @Test
    void findById() {
        getTestMatchers(Restaurant.class)
                .assertMatch(restaurantService.findById(getRestaurantOne().getId()), getRestaurantOne());
    }

    @Test
    void findByIdNoFound() {
        assertThrows(NotFoundException.class, () ->
                restaurantService.findById(0));
    }

    @Test
    void findByName() {
        getTestMatchers(Restaurant.class)
                .assertMatch(restaurantService.findByName(getRestaurantTwo().getName()), getRestaurantTwo());
    }

    @Test
    void findByNameNotFound() {
        assertThrows(NotFoundException.class, () ->
                restaurantService.findByName("jskdfhjksdfh"));
    }

    @Test
    void deleteById() {
        restaurantService.deleteById(getRestaurantOne().getId());
        assertThrows(NotFoundException.class, () ->
                restaurantService.findById(getRestaurantOne().getId()));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                restaurantService.deleteById(0));
    }

    @Test
    void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                restaurantService.create(new Restaurant(getRestaurantOne().getName())));
    }

    @Test
    void create() {
        Restaurant newRestaurant = new Restaurant("newRestaurant");
        restaurantService.create(newRestaurant);
        getTestMatchers(Restaurant.class).assertMatch(restaurantService.findByName(newRestaurant.getName()), newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = new Restaurant("updatesRestaurant");
        restaurantService.update(updated, getRestaurantOne().getId());
        getTestMatchers(Restaurant.class)
                .assertMatch(restaurantService.findById(updated.getId()), updated);
    }

    @Test
    void updateWithNotFoundRestaurantId() {
        assertThrows(NotFoundException.class, () ->
                restaurantService.update(new Restaurant("updatesRestaurant"), 0));
    }
}