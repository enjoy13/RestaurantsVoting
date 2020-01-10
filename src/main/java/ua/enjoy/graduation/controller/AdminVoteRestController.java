package ua.enjoy.graduation.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.enjoy.graduation.model.Vote;
import ua.enjoy.graduation.service.VoteService;

import java.time.LocalDate;
import java.util.List;

import static ua.enjoy.graduation.controller.AdminVoteRestController.ADMIN_VOTE_URL;
import static ua.enjoy.graduation.util.DateTimeUtil.DATE_PATTERN;

@RestController("adminVoteRestController")
@RequestMapping(value = ADMIN_VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
public class AdminVoteRestController {

     public static final String ADMIN_VOTE_URL = "/rest/admin/vote";

     private final VoteService voteService;

     public AdminVoteRestController(VoteService voteService) {
          this.voteService = voteService;
     }

     @GetMapping("/{id}")
     @ResponseStatus(value = HttpStatus.OK)
     public Vote findById(@PathVariable int id) {
          return voteService.findById(id);
     }

     @GetMapping("/user/{userId}")
     @ResponseStatus(value = HttpStatus.OK)
     public List<Vote> findAllByUserId(@PathVariable int userId) {
          return voteService.findAllByUserId(userId);
     }

     @GetMapping("/restaurant/{restaurantId}")
     @ResponseStatus(value = HttpStatus.OK)
     public List<Vote> findAllByRestaurantId(@PathVariable int restaurantId) {
          return voteService.findAllByRestaurantId(restaurantId);
     }

     @GetMapping("/byDate")
     @ResponseStatus(value = HttpStatus.OK)
     public List<Vote> findByCreateDate(@RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate date) {
          return voteService.findAllByCreateDate(date);
     }

     @GetMapping("/today")
     @ResponseStatus(value = HttpStatus.OK)
     public List<Vote> findByToday() {
          return voteService.findAllByCreateDate(LocalDate.now());
     }

     @GetMapping("/filter")
     @ResponseStatus(value = HttpStatus.OK)
     public List<Vote> findAllBetween(@RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate startDate,
                                      @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate endDate) {
          return voteService.findAllBetween(startDate, endDate);
     }

     @GetMapping("/filterId")
     @ResponseStatus(value = HttpStatus.OK)
     public List<Vote> findAllBetweenWithUserId(@RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate startDate,
                                                @RequestParam @DateTimeFormat(pattern = DATE_PATTERN) LocalDate endDate,
                                                @RequestParam int userId) {
          return voteService.findAllBetweenWithUserId(startDate, endDate, userId);
     }

     @DeleteMapping("/{id}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void deleteById(@PathVariable int id) {
          voteService.delete(id);
     }
}
