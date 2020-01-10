package ua.enjoy.graduation;

import org.springframework.test.web.servlet.ResultMatcher;
import ua.enjoy.graduation.model.Dish;
import ua.enjoy.graduation.model.User;
import ua.enjoy.graduation.model.Vote;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.enjoy.graduation.TestUtil.readListFromJsonMvcResult;

public class TestMatchers<T> {
    private final Class<T> clazz;
    private final String[] fieldsToIgnore;
    private final boolean useEquals;

    public static <T> TestMatchers<T> useFieldsComparator(Class<T> clazz, String... fieldsToIgnore) {
        return new TestMatchers<>(clazz, false, fieldsToIgnore);
    }

    public static <T> TestMatchers<T> getTestMatchers(Class<T> tClass) {
         if (tClass.getSimpleName().equals(Dish.class.getSimpleName())) {
            return useFieldsComparator(tClass, "restaurant", "createdDate");
        } else if (tClass.getSimpleName().equals(User.class.getSimpleName())) {
            return useFieldsComparator(tClass, "registered", "password");
        } else if (tClass.getSimpleName().equals(Vote.class.getSimpleName())) {
            return useFieldsComparator(tClass, "createdDate");
        }
        return useEquals(tClass);
    }

    public static <T> TestMatchers<T> useEquals(Class<T> clazz) {
        return new TestMatchers<>(clazz, true);
    }

    private TestMatchers(Class<T> clazz, boolean useEquals, String... fieldsToIgnore) {
        this.clazz = clazz;
        this.useEquals = useEquals;
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public void assertMatch(T actual, T expected) {
        if (useEquals) {
            assertThat(actual).isEqualTo(expected);
        } else {
            assertThat(actual).isEqualToIgnoringGivenFields(expected, fieldsToIgnore);
        }
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        if (useEquals) {
            assertThat(actual).isEqualTo(expected);
        } else {
            assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
        }
    }

    public ResultMatcher contentJson(T expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, clazz), expected);
    }

    public ResultMatcher contentJson(T... expected) {
        return contentJson(List.of(expected));
    }

    public ResultMatcher contentJson(Iterable<T> expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, clazz), expected);
    }
}
