package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.ValidationException;
import java.time.LocalDate;

@Slf4j
public class UserValidator {
    public static boolean isValidUser(User user) throws ValidationException{
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.warn("Email пустой или не содержат символ @");
            throw new ValidationException("Email пустой или не содержат символ @");
        } else if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.warn("LOGIN пустой или содержит пробелы");
            throw new ValidationException("LOGIN пустой или содержит пробелы");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("DATE превышает текущую дату");
            throw new ValidationException("DATE превышает текущую дату");
        } else {
            return true;
        }
    }
}
