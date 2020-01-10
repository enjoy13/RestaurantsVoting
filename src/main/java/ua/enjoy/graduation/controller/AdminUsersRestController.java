package ua.enjoy.graduation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static ua.enjoy.graduation.controller.AdminUsersRestController.ADMIN_USERS_URL;
import static ua.enjoy.graduation.util.ValidationUtil.getBody;
import static ua.enjoy.graduation.util.ValidationUtil.getUri;

@RestController("adminUsersRestController")
@RequestMapping(value = ADMIN_USERS_URL, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class AdminUsersRestController {

    public static final String ADMIN_USERS_URL = "/rest/admin/users";

    private final UserService userService;

    public AdminUsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public User findById(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping("/byName")
    @ResponseStatus(value = HttpStatus.OK)
    public User findByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @GetMapping("/byEmail")
    @ResponseStatus(value = HttpStatus.OK)
    public User findByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int id) {
        userService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User created = userService.create(user);
        return getBody(created, getUri(created.getId(), ADMIN_USERS_URL));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@Valid @RequestBody User user, @PathVariable int id) {
        userService.update(user, id);
    }
}
