package ua.enjoy.graduation;

import lombok.Getter;
import lombok.ToString;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.to.UserTo;
import ua.enjoy.graduation.util.UserUtil;

@ToString
@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public int getId() {
        return userTo.id();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }
}