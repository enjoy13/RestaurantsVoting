package ua.enjoy.graduation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.enjoy.graduation.model.Dish;
import ua.enjoy.graduation.repository.DishRepository;
import ua.enjoy.graduation.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ua.enjoy.graduation.util.ValidationUtil.*;

@Service("dishService")
@Slf4j
public class DishService {
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Dish findById(int id) {
        Assert.notNull(id, "id must not be null");
        Dish dishById = dishRepository.findById(id);
        log.info("method findById: id = {}, dish = {}", id, dishById);
        return checkNotFound(dishById, "id = " + id);
    }

    public List<Dish> getAllByRestaurantIdToday(int restaurantId) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        List<Dish> dishesByRestaurantId = dishRepository.findAllByRestaurantId(restaurantId).stream()
                .filter(dish -> dish.getCreatedDate().toLocalDate().isEqual(LocalDate.now())).collect(Collectors.toList());
        log.info("method getAllByRestaurantToday: name = {}, dishes = {}", restaurantId, dishesByRestaurantId);
        return checkEmptyList(dishesByRestaurantId, "restaurantId" + restaurantId);
    }

    public List<Dish> getAllByRestaurantNameToday(String name) {
        Assert.notNull(name, "name must not be null");
        List<Dish> dishesByRestaurantName = dishRepository.findAllByRestaurantName(name).stream()
                .filter(dish -> dish.getCreatedDate().toLocalDate().isEqual(LocalDate.now())).collect(Collectors.toList());
        log.info("method getAllByRestaurantNameToday: name = {}, dishes = {}", name, dishesByRestaurantName);
        return checkEmptyList(dishesByRestaurantName, "name" + name);
    }

    public Dish findByName(String name) {
        Assert.notNull(name, "name must not be null");
        Dish dishByName = dishRepository.findByName(name);
        log.info("method findByName: name = {}, dish = {}", name, dishByName);
        return checkNotFound(dishByName, "name=" + name);
    }

    public List<Dish> findByPrice(BigDecimal price) {
        Assert.notNull(price, "price must not be null");
        List<Dish> allDishesByPrice = dishRepository.findAllByPrice(price);
        log.info("method findByPrice: price = {}, dish = {}", price, allDishesByPrice);
        return checkEmptyList(allDishesByPrice, "price=" + price);
    }

    public void deleteById(int id) {
        Assert.notNull(id, "id must not be null");
        log.info("method deleteById: id = {}", id);
        checkNotFound(dishRepository.findById(id), "id = " + id);
        dishRepository.deleteById(id);
    }

    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        dish.setRestaurant(restaurantRepository.findById(restaurantId));
        log.info("method create: dish = {}, restaurantId = {}", dish, restaurantId);
        return dishRepository.save(dish);
    }

    public void update(Dish dish, int id, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        Assert.notNull(id, "id must not be null");
        Assert.notNull(restaurantId, "restaurantId must not be null");
        checkNotFound(restaurantRepository.findById(restaurantId), "restaurantId = " + restaurantId);
        checkNotFound(dishRepository.findById(id), "id = " + id);
        assureIdConsistent(dish, id);
        Dish updated = new Dish(id, dish.getName(), dish.getPrice(), restaurantRepository.findById(restaurantId), dish.getCreatedDate());
        log.info("method update: dish = {}, id = {},  restaurantId = {}", updated, id, restaurantId);
        dishRepository.save(updated);
    }
}
