package ua.enjoy.graduation.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.enjoy.graduation.HasId;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseTo implements HasId {
    protected Integer id;
}
