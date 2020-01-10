package ua.enjoy.graduation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ua.enjoy.graduation.util.DateTimeUtil.DATE_TIME_PATTERN;

@Entity
@Table(name = "voiting")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Vote extends AbstractBaseEntity {

    @Column(name = "created_date", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @NotNull
    private Restaurant restaurant;

    @Builder
    public Vote(Integer id, User user, Restaurant restaurant) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
    }

    public Vote(User user, Restaurant restaurant) {
        super(null);
        this.user = user;
        this.restaurant = restaurant;
    }
}
