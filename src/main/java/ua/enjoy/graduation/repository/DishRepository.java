package ua.enjoy.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.enjoy.graduation.model.Dish;

import java.math.BigDecimal;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    Dish save(Dish dish);

    List<Dish> findAll();

    Dish findById(int id);

    List<Dish> findAllByRestaurantId(int id);

    List<Dish> findAllByRestaurantName(String name);

    Dish findByName(String name);

    List<Dish> findAllByPrice(BigDecimal price);

    @Transactional
    void deleteById(int id);
}
