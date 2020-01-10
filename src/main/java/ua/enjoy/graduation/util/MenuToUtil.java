package ua.enjoy.graduation.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.enjoy.graduation.model.Dish;
import ua.enjoy.graduation.model.Restaurant;
import ua.enjoy.graduation.to.MenuTo;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuToUtil {

    public static MenuTo getMenuTo(Restaurant restaurant, List<Dish> dishes) {
        return new MenuTo(restaurant, dishes);
    }
}
