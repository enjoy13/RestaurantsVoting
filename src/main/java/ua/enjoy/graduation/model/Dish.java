package ua.enjoy.graduation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ua.enjoy.graduation.util.DateTimeUtil.DATE_TIME_PATTERN;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "dishes_unique_name_idx")})
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 1000)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "created_date", nullable = false, columnDefinition = "timestamp default now()")
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdDate;

    public Dish(Integer id, String name, BigDecimal price, Restaurant restaurant, LocalDateTime createdDate) {
        super(id, name);
        setPrice(price);
        this.restaurant = restaurant;
        setCreatedDate(createdDate);
    }

    public Dish(String name, BigDecimal price, Restaurant restaurant, LocalDateTime createdDate) {
        super(null, name);
        setPrice(price);
        this.restaurant = restaurant;
        setCreatedDate(createdDate);
    }

    private void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate == null ? LocalDateTime.now() : createdDate;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2);
    }
}
