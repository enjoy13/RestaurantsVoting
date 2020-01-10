package ua.enjoy.graduation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.enjoy.graduation.model.Dish;
import ua.enjoy.graduation.service.DishService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

import static ua.enjoy.graduation.controller.AdminDishRestController.ADMIN_DISHES_URL;
import static ua.enjoy.graduation.util.ValidationUtil.getBody;
import static ua.enjoy.graduation.util.ValidationUtil.getUri;

@RestController("adminDishRestController")
@RequestMapping(value = ADMIN_DISHES_URL, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class AdminDishRestController {

    public static final String ADMIN_DISHES_URL = "/rest/admin/dishes";

    private final DishService dishService;

    public AdminDishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/restaurant/{restaurantId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Dish> getAllByRestaurantIdToday(@PathVariable int restaurantId) {
        return dishService.getAllByRestaurantIdToday(restaurantId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Dish findById(@PathVariable int id) {
        return dishService.findById(id);
    }

    @GetMapping("/byName")
    @ResponseStatus(value = HttpStatus.OK)
    public Dish findByName(@RequestParam String name) {
        return dishService.findByName(name);
    }

    @GetMapping("/byPrice")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Dish> findByPrice(@RequestParam BigDecimal price) {
        return dishService.findByPrice(price);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        dishService.deleteById(id);
    }

    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Dish> createWithLocationByRestaurantId(@Valid @RequestBody Dish dish , @PathVariable int restaurantId) {
        Dish created = dishService.create(dish, restaurantId);
        return getBody(created, getUri(created.getId(), ADMIN_DISHES_URL));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @RequestParam int restaurantId) {
        dishService.update(dish, id, restaurantId);
    }
}
