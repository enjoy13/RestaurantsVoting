package ua.enjoy.graduation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.enjoy.graduation.model.Restaurant;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestaurantTestData {

    private static final Restaurant RESTAURANT_ONE = new Restaurant(1, "Il Etait Un Square");
    private static final Restaurant RESTAURANT_TWO = new Restaurant(2, "Epicure");
    private static final Restaurant RESTAURANT_THREE = new Restaurant(3, "Jean Yves of Chef Jean-Yves' Table");
    private static final Restaurant RESTAURANT_FOUR = new Restaurant(4, "La MiN");
    private static final Restaurant RESTAURANT_FIVE = new Restaurant(5, "Signature Montmartre");
    private static final Restaurant RESTAURANT_SIX = new Restaurant(6, "Jean-Francois Rouquette");
    private static final Restaurant RESTAURANT_SEVEN= new Restaurant(7, "Boutary");
    private static final Restaurant RESTAURANT_EIGHT = new Restaurant(8, "Pianovins");
    private static final Restaurant RESTAURANT_NINE = new Restaurant(9, "La Creperie");
    private static final Restaurant RESTAURANT_TEN = new Restaurant(10, "JJ Beaumarchais");
    private static final Restaurant updateRestaurant = new Restaurant("updateRestaurant");

    private static final List<Restaurant> restaurans = List.of(RESTAURANT_ONE, RESTAURANT_TWO, RESTAURANT_THREE, RESTAURANT_FOUR,
            RESTAURANT_FIVE, RESTAURANT_SIX, RESTAURANT_SEVEN, RESTAURANT_EIGHT, RESTAURANT_NINE, RESTAURANT_TEN);

    private static final String restaurantName = RESTAURANT_THREE.getName();

    public static String getRestaurantName() {
        return restaurantName;
    }

    public static Restaurant getRestaurantOne() {
        return RESTAURANT_ONE;
    }

    public static Restaurant getRestaurantTwo() {
        return RESTAURANT_TWO;
    }

    public static Restaurant getRestaurantThree() {
        return RESTAURANT_THREE;
    }

    public static Restaurant getRestaurantFour() {
        return RESTAURANT_FOUR;
    }

    public static Restaurant getRestaurantFive() {
        return RESTAURANT_FIVE;
    }

    public static Restaurant getRestaurantSix() {
        return RESTAURANT_SIX;
    }

    public static Restaurant getRestaurantTen() {
        return RESTAURANT_TEN;
    }

    public static List<Restaurant> getRestaurans() {
        return restaurans;
    }

    public static Restaurant getUpdateRestaurant() {
        return updateRestaurant;
    }
}
