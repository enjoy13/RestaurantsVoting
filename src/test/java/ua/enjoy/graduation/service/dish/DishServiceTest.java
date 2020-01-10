package ua.enjoy.graduation.service.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import ua.enjoy.graduation.model.Dish;
import ua.enjoy.graduation.service.AbstractServiceTest;
import ua.enjoy.graduation.service.DishService;
import ua.enjoy.graduation.util.exception.NotFoundException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.enjoy.graduation.DishTestData.*;
import static ua.enjoy.graduation.RestaurantTestData.*;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService dishService;

    @Test
    void findById() {
        getTestMatchers(Dish.class).assertMatch(dishService.findById(getDishFirst().getId()), getDishFirst());
    }

    @Test
    void findByIdNoFound() {
        assertThrows(NotFoundException.class, () -> dishService.findById(0));
    }

    @Test
    void getAllByRestaurantToday() {
        getTestMatchers(Dish.class).assertMatch(dishService.getAllByRestaurantIdToday(getRestaurantThree().getId()), getDishes());
    }

    @Test
    void getAllByNotFoundRestaurantToday() {
        assertThrows(NotFoundException.class, () -> dishService.getAllByRestaurantIdToday(0));
    }

    @Test
    void getAllByRestaurantNameToday() {
        getTestMatchers(Dish.class)
                .assertMatch(dishService.getAllByRestaurantNameToday(getRestaurantThree().getName()), getDishes());
    }

    @Test
    void getAllByNotFoundRestaurantNameToday() {
        assertThrows(NotFoundException.class, () -> dishService.getAllByRestaurantNameToday("sfkdshfjkshdf"));
    }

    @Test
    void findByName() {
        getTestMatchers(Dish.class).assertMatch(dishService.findByName(getDishFirst().getName()), getDishFirst());
    }

    @Test
    void findByNameNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.findByName("sfkdshfjkshdf"));
    }

    @Test
    void findByPrice() {
        getTestMatchers(Dish.class).assertMatch(dishService.findByPrice(getDishSecond().getPrice()), getDishSecond());
    }

    @Test
    void findByPriceNoFound() {
        assertThrows(NotFoundException.class, () -> dishService.findByPrice(BigDecimal.valueOf(0)));
    }

    @Test
    void deleteById() {
        dishService.deleteById(getDishFirst().getId());
        assertThrows(NotFoundException.class, () -> dishService.findById(getDishFirst().getId()));
    }

    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> dishService.deleteById(0));
    }

    @Test
    void create() {
        Dish newDish = getNewDish();
        dishService.create(newDish, getRestaurantFive().getId());
        getTestMatchers(Dish.class).assertMatch(dishService.findById(newDish.getId()), newDish);
    }

    @Test
    void duplicateNameCreate() {
        assertThrows(DataAccessException.class, () ->
                dishService.create(getDishWithDuplicateName(), getRestaurantOne().getId()));
    }

    @Test
    void createWithNotFoundByRestaurantId() {
        assertThrows(DataIntegrityViolationException.class, () -> dishService.create(getNewDish(), 0));
    }

    @Test
    void update() {
        Dish updateDish = getUpdateDish();
        dishService.update(updateDish, getDishFirst().getId(), getRestaurantOne().getId());
        getTestMatchers(Dish.class).assertMatch(dishService.findById(updateDish.getId()), updateDish);
    }

    @Test
    void updateWithNotFoundDishId() {
        assertThrows(NotFoundException.class, () ->
                dishService.update(getUpdateDish(), 0 , getRestaurantOne().getId()));
    }

    @Test
    void updateWithNotFoundRestaurantId() {
        assertThrows(NotFoundException.class, () ->
                dishService.update(getUpdateDish(), getDishFirst().getId(), 0));
    }
}
