package ua.enjoy.graduation.service.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ua.enjoy.graduation.TestMatchers;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.service.AbstractServiceTest;
import ua.enjoy.graduation.service.UserService;
import ua.enjoy.graduation.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ua.enjoy.graduation.TestMatchers.getTestMatchers;
import static ua.enjoy.graduation.UserTestData.*;

class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getAll() {
        getTestMatchers(User.class)
                .assertMatch( userService.getAll(), getUsers());
    }

    @Test
    void findById() {
        getTestMatchers(User.class)
                .assertMatch(userService.findById(getADMIN().getId()), getADMIN());
    }

    @Test
    void findByIdNoFound() {
        assertThrows(NotFoundException.class, () ->
                userService.findById(0));
    }

    @Test
    void findByName() {
        getTestMatchers(User.class)
                .assertMatch(userService.findByName(getADMIN().getName()), getADMIN());
    }

    @Test
    void findByNameNotFound() {
        assertThrows(NotFoundException.class, () ->
                userService.findByName("sjfhkldsfklsdf"));
    }

    @Test
    void findByEmail() {
        getTestMatchers(User.class)
                .assertMatch(userService.findByEmail(getADMIN().getEmail()), getADMIN());
    }

    @Test
    void findByEmailNotFound() {
        assertThrows(NotFoundException.class, () ->
                userService.findByEmail("user@sdklfjslkdfksjdf"));
    }

    @Test
    void delete() {
        userService.delete(getUSER().getId());
        assertThrows(NotFoundException.class, () ->
                userService.findById(getUSER().getId()));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                userService.delete(0));
    }

    @Test
    void create() {
        User newUser = getNewUser();
        User created = userService.create(new User(newUser));
        Integer newId = created.getId();
        newUser.setId(newId);
        getTestMatchers(User.class).assertMatch(created, newUser);
        getTestMatchers(User.class).assertMatch(userService.findById(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                userService.create(getUserWithDuplicateEmail()));
    }

    @Test
    void update() {
        User updated = getUpdateUser();
        userService.update(updated, getUSER().getId());
        getTestMatchers(User.class).assertMatch(userService.findById(getUSER().getId()), updated);
    }

    @Test
    void updateWithNotFoundId() {
        User updated = getUpdateUser();
        userService.update(updated, getUSER().getId());
        getTestMatchers(User.class).assertMatch(userService.findById(getUSER().getId()), updated);
    }
}