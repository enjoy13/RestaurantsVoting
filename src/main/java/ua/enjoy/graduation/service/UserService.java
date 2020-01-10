package ua.enjoy.graduation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.enjoy.graduation.AuthorizedUser;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.repository.UserRepository;
import ua.enjoy.graduation.to.UserTo;

import java.util.List;

import static ua.enjoy.graduation.util.UserUtil.prepareToSave;
import static ua.enjoy.graduation.util.UserUtil.updateFromTo;
import static ua.enjoy.graduation.util.ValidationUtil.*;

@Service("userService")
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        log.info("method getAll from users : {}", allUsers);
        return checkEmptyList(allUsers);
    }

    public User findById(int id) {
        Assert.notNull(id, "id must not be null");
        User userById = userRepository.findById(id);
        log.info("method findById id = {}, User = {}", id, userById);
        return checkNotFound(userById, "id = " + id);
    }

    public User findByName(String name) {
        Assert.notNull(name, "name must not be null");
        User userByName = userRepository.findByName(name);
        log.info("method findByName name = {}, user = {}", name, userByName);
        return checkNotFound(userByName, "name = " + name);
    }

    public User findByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        User userByEmail = userRepository.findByEmail(email);
        log.info("method findByEmail user email = {}, User = {}", email, userByEmail);
        return checkNotFound(userByEmail, "email = " + email);
    }

    public void delete(int id) {
        Assert.notNull(id, "id must not be null");
        log.info("method delete by id = {}", id);
        checkNotFound(userRepository.findById(id), "id = " + id);
        userRepository.deleteById(id);
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        log.info("method create user = {}", user);
        return prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    public void update(User user, int id) {
        Assert.notNull(user, "user must not be null");
        log.info("method update user = {} by id = {}", user, id);
        assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    public void update(UserTo userTo, int id) {
        Assert.notNull(userTo, "userTo must not be null");
        log.info("method update userTo = {} by id = {}", userTo, id);
        assureIdConsistent(userTo, id);
        User user = findById(userTo.id());
        prepareAndSave(updateFromTo(user, userTo));
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
