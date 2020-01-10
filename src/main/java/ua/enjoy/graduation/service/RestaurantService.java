package ua.enjoy.graduation.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ua.enjoy.graduation.model.Restaurant;
import ua.enjoy.graduation.repository.RestaurantRepository;

import java.util.List;

import static ua.enjoy.graduation.util.ValidationUtil.*;

@Service("restaurantService")
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAll() {
        List<Restaurant> getAll = restaurantRepository.findAll();
        log.info("method getAll: restaurants = {}", getAll);
        return checkEmptyList(getAll);
    }

    public Restaurant findById(int id) {
        Assert.notNull(id, "id must not be null");
        Restaurant restaurantById = restaurantRepository.findById(id);
        log.info("method findById: restaurants = {}", restaurantById);
        return checkNotFound(restaurantById, "id = " + id);
    }

    public Restaurant findByName(String name) {
        Assert.notNull(name, "name must not be null");
        Restaurant restaurantByName = restaurantRepository.findByName(name);
        log.info("method findByName: restaurant name = {}, restaurant = {}", name, restaurantByName);
        return checkNotFound(restaurantByName, "name=" + name);
    }

    public void deleteById(int id) {
        Assert.notNull(id, "id must not be null");
        log.info("method deleteById: restaurant by id = {}", id);
        checkNotFound(restaurantRepository.findById(id), "id = " + id);
        restaurantRepository.deleteById(id);
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        log.info("method create: restaurant {}", restaurant);
        checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(restaurant, "dish must not be null");
        checkNotFound(restaurantRepository.findById(id), "id = " + id);
        assureIdConsistent(restaurant, id);
        log.info("method update: restaurant {} by id = {}", restaurant, id);
        restaurantRepository.save(restaurant);
    }
}
