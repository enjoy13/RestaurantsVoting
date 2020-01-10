package ua.enjoy.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.enjoy.graduation.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    Restaurant save(Restaurant restaurant);

    Restaurant findById(int id);

    List<Restaurant> findAll();

    Restaurant findByName(String name);

    @Transactional
    void deleteById(int id);
}
