package ua.enjoy.graduation.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.enjoy.graduation.model.Dish;
import ua.enjoy.graduation.model.Restaurant;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuTo implements Serializable {

    @NotNull
    private Restaurant restaurant;

    @NotNull
    private List<Dish> dishes;
}
