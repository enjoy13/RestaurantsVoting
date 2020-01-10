package ua.enjoy.graduation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.enjoy.graduation.model.Dish;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static ua.enjoy.graduation.RestaurantTestData.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DishTestData {

    private static final Dish DISH_FIRST = new Dish(1, "Львівська мізерія", new BigDecimal("94.00"), getRestaurantOne(), LocalDateTime.now());
    private static final Dish DISH_SECOND = new Dish(2, "Салатка з помідором", new BigDecimal("89.00"), getRestaurantTwo(), LocalDateTime.now());
    private static final Dish DISH_THIRD = new Dish(3, "Найясніший пане", new BigDecimal("125.00"), getRestaurantThree(), LocalDateTime.now());
    private static final Dish DISH_FOUR = new Dish(4, "Салатка пані Телічкової", new BigDecimal("140.00"), getRestaurantThree(), LocalDateTime.now());
    private static final Dish DISH_FIVE = new Dish(5, "Салат Старольвівський", new BigDecimal("93.00"), getRestaurantThree(), LocalDateTime.now());
    private static final Dish newDish = new Dish("newDish", new BigDecimal("55.00"), getRestaurantFive(), LocalDateTime.now());
    private static final Dish updateDish = new Dish("updateDish", new BigDecimal("66.00"), getRestaurantFour(), LocalDateTime.now());
    private static final Dish dishWithDuplicateName = new Dish(DISH_FIRST.getName(), new BigDecimal("55.00"), getRestaurantOne(), LocalDateTime.now());
    private static final List<Dish> dishes = List.of(DISH_THIRD, DISH_FOUR, DISH_FIVE);

    public static Dish getDishFirst() {
        return DISH_FIRST;
    }

    public static Dish getDishSecond() {
        return DISH_SECOND;
    }

    public static Dish getNewDish() {
        return newDish;
    }

    public static Dish getUpdateDish() {
        return updateDish;
    }

    public static Dish getDishWithDuplicateName() {
        return dishWithDuplicateName;
    }

    public static List<Dish> getDishes() {
        return dishes;
    }
}
