package ua.enjoy.graduation.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.enjoy.graduation.HasId;
import ua.enjoy.graduation.model.AbstractBaseEntity;
import ua.enjoy.graduation.util.exception.NotFoundException;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtil {

    public static <T> List<T> checkEmptyList(List<T> list, String... msg) {
        if (CollectionUtils.isEmpty(list))
            throw new NotFoundException("List is empty " + Arrays.toString(msg));
        return list;
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id = null)");
        }
    }

    public static void assureIdConsistent(HasId entity, int id) {
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id = " + id);
        }
    }

    public static URI getUri(int id, String url) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url + "/{id}")
                .buildAndExpand(id).toUri();
    }

    public static <T> ResponseEntity<T> getBody(T created, URI uriOfNewResource) {
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}