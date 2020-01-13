package ua.enjoy.graduation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.enjoy.graduation.AuthorizedUser;
import ua.enjoy.graduation.model.Restaurant;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.model.Vote;
import ua.enjoy.graduation.service.DishService;
import ua.enjoy.graduation.service.RestaurantService;
import ua.enjoy.graduation.service.UserService;
import ua.enjoy.graduation.service.VoteService;
import ua.enjoy.graduation.to.MenuTo;
import ua.enjoy.graduation.to.UserTo;
import ua.enjoy.graduation.util.MenuToUtil;
import ua.enjoy.graduation.util.UserUtil;

import javax.validation.Valid;
import java.util.List;

import static ua.enjoy.graduation.controller.SecurityUtil.authUserId;
import static ua.enjoy.graduation.controller.UserRestController.USERS_REST_URL;
import static ua.enjoy.graduation.util.ValidationUtil.getBody;
import static ua.enjoy.graduation.util.ValidationUtil.getUri;

@RestController("userRestController")
@RequestMapping(value = USERS_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
@Slf4j
public class UserRestController {

    static final String USERS_REST_URL = "/rest/users";

    private final RestaurantService restaurantService;

    private final VoteService voteService;

    private final DishService dishService;

    private final UserService userService;

    public UserRestController(RestaurantService restaurantService, VoteService voteService, DishService dishService, UserService userService) {
        this.restaurantService = restaurantService;
        this.voteService = voteService;
        this.dishService = dishService;
        this.userService = userService;
    }

    @GetMapping("/restaurants")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAll();
    }

    @GetMapping("/restaurants/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Restaurant findById(@PathVariable int id) {
        return restaurantService.findById(id);
    }

    @GetMapping("/restaurants/byName")
    @ResponseStatus(value = HttpStatus.OK)
    public Restaurant findByName(@RequestParam String name) {
        return restaurantService.findByName(name);
    }

    @GetMapping("/menu/{name}")
    @ResponseStatus(HttpStatus.OK)
    public MenuTo getMenuByRestaurantNameToday(@PathVariable String name) {
        return MenuToUtil.getMenuTo(restaurantService.findByName(name),
                dishService.getAllByRestaurantNameToday(name));
    }

    @PostMapping(value = "/voting/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> voting (@PathVariable int restaurantId){
        Vote created = voteService.voting(restaurantId, authUserId());
        return getBody(created, getUri(created.getId(), USERS_REST_URL));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = userService.create(UserUtil.createNewFromTo(userTo));
        return getBody(created, getUri(created.getId(), USERS_REST_URL));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthorizedUser authUser) {
        userService.update(userTo, authUser.getId());
    }
}