package ua.enjoy.graduation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ua.enjoy.graduation.model.Role;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.to.UserTo;

import java.util.Collections;
import java.util.List;

import static ua.enjoy.graduation.util.UserUtil.asTo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserTestData {

    private static final User ADMIN = new User(1, "David", "david@gmail.com", "123456", Role.ROLE_ADMIN);
    private static final User USER = new User(2, "Lee", "lee.grant@gmail.com", "654321", Role.ROLE_USER);
    private static final User SERGIO = new User(3, "Sergio", "sergio.romero@gmail.com", "qwerty", Role.ROLE_USER);
    private static final User JOEL = new User(4, "Joel", "joel.pereira@gmail.com", "ytrewq", Role.ROLE_USER);
    private static final User VICTOR = new User(5, "Victor", "victor.lindelof@gmail.com", "asdfgh", Role.ROLE_USER);
    private static final User ERIC = new User(6, "Eric", "eric.bailly@gmail.com", "hgfdsa", Role.ROLE_USER);
    private static final User PHIL = new User(7, "Phil", "phil.jones@gmail.com", "zxcvbn", Role.ROLE_USER);
    private static final User MARCOS = new User(8, "Marcos", "marcos.rojo@gmail.com", "nbvcxz", Role.ROLE_USER);
    private static final User DIOGO = new User(9, "Diogo", "diogo.dalot@gmail.com", "hgfdsa", Role.ROLE_USER);
    private static final User HARRY = new User(10, "Harry", "harry.maguire@gmail.com", "hgfdsa", Role.ROLE_USER);
    private static final User newUser = new User("New", "new@gmail.com", "newPass", Collections.singleton(Role.ROLE_USER));
    private static final User userWithDuplicateEmail = new User("Duplicate", USER.getEmail(), "newPass",  Collections.singleton(Role.ROLE_USER));
    private static final User updateUser = new User(USER.getId(), "UpdatedName", "lee.grant@gmail.com", "newPass",  Collections.singleton(Role.ROLE_USER));

    private static final List<User> users = List.of(ADMIN, USER, SERGIO, JOEL, VICTOR, ERIC, PHIL, MARCOS, DIOGO, HARRY);

    public static User getADMIN() {
        return ADMIN;
    }

    public static User getUSER() {
        return USER;
    }

    public static User getSERGIO() {
        return SERGIO;
    }

    public static User getJOEL() {
        return JOEL;
    }

    public static User getVICTOR() {
        return VICTOR;
    }

    public static User getERIC() {
        return ERIC;
    }

    public static User getPHIL() {
        return PHIL;
    }

    public static User getMARCOS() {
        return MARCOS;
    }

    public static User getDIOGO() {
        return DIOGO;
    }

    public static User getHARRY() {
        return HARRY;
    }

    public static User getNewUser() {
        return newUser;
    }

    public static UserTo getNewUserTo() {
        return asTo(getNewUser());
    }

    public static UserTo getUpdateUserTo() {
        return asTo(getUpdateUser());
    }

    public static User getUserWithDuplicateEmail() {
        return userWithDuplicateEmail;
    }

    public static User getUpdateUser() {
        return updateUser;
    }

    public static List<User> getUsers() {
        return users;
    }
}
