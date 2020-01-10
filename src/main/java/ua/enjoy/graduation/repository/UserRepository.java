package ua.enjoy.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.enjoy.graduation.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();

    @Transactional
    User save(User user);

    User findById(int id);

    User findByEmail(String email);

    User findByName(String name);

    @Transactional
    void deleteById(int id);
}
