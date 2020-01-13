package ua.enjoy.graduation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.enjoy.graduation.model.Restaurant;
import ua.enjoy.graduation.service.RestaurantService;

import javax.validation.Valid;
import java.util.List;

import static ua.enjoy.graduation.controller.AdminsRestaurantsRestController.ADMIN_RESTOURANTS_URL;
import static ua.enjoy.graduation.util.ValidationUtil.getBody;
import static ua.enjoy.graduation.util.ValidationUtil.getUri;

@RestController("adminsRestaurantsRestController")
@RequestMapping(value = ADMIN_RESTOURANTS_URL, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class AdminsRestaurantsRestController {

    public static final String ADMIN_RESTOURANTS_URL = "/rest/admin/restaurants";

    private final RestaurantService restaurantService;

    public AdminsRestaurantsRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        restaurantService.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = restaurantService.create(restaurant);
        return getBody(created, getUri(created.getId(), ADMIN_RESTOURANTS_URL));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        restaurantService.update(restaurant, id);
    }
}
