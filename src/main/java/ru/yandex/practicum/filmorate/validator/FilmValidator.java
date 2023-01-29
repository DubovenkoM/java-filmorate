package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.ValidationException;
import java.time.LocalDate;

@Slf4j
public class FilmValidator {
    public static boolean isValidFilm(Film film) {
        if(film.getName().trim().isEmpty()) {
            log.warn("NAME пустой");
            throw new ValidationException("NAME пустой");
        } else if (film.getDescription().length() > 200) {
            log.warn("maxDescription — 200 символов");
            throw new ValidationException("maxDescription — 200 символов");
        } else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("releaseDATE — не раньше 28 декабря 1895 года");
            throw new ValidationException("releaseDATE — не раньше 28 декабря 1895 года");
        } else if (film.getDuration() < 0) {
            log.warn("Duration - отрицательный");
            throw new ValidationException("Duration - отрицательный");
        }
        return true;
    }
}
