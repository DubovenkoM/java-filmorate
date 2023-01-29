package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.StringUtils;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmValidatorTest {
    FilmController filmController = new FilmController();
    Film qwertyFilm;

    @BeforeEach
    public void beforeEach() {
        qwertyFilm = Film.builder()
                .name("qwerty")
                .description("qwerty about qwerty")
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(333L)
                .build();
    }
    @Test
    public void returnFilms() {
        filmController.createFilm(qwertyFilm);
        assertTrue(filmController.getFilms().contains(qwertyFilm));
    }
    @Test
    public void exceptionEmptyName() {
        qwertyFilm.setName("");
        Throwable exception = assertThrows(ValidationException.class, () -> filmController.createFilm(qwertyFilm));
        assertEquals("NAME пустой", exception.getMessage());
    }

    @Test
    public void exceptionCharacterCount() {
        String longDescription = StringUtils.repeat("qwerty", " ", 100);
        qwertyFilm.setDescription(longDescription);
        Throwable exception = assertThrows(ValidationException.class, () -> filmController.createFilm(qwertyFilm));
        assertEquals("maxDescription — 200 символов", exception.getMessage());
    }

    @Test
    public void exceptionDateRelease() {
        qwertyFilm.setReleaseDate(LocalDate.of(333, 3, 3));
        Throwable exception = assertThrows(ValidationException.class, () -> filmController.createFilm(qwertyFilm));
        assertEquals("releaseDATE — не раньше 28 декабря 1895 года", exception.getMessage());
    }
    @Test
    public void exceptionNegativeDuration() {
        qwertyFilm.setDuration(-1L);
        Throwable exception = assertThrows(ValidationException.class, () -> filmController.createFilm(qwertyFilm));
        assertEquals("Duration - отрицательный", exception.getMessage());
    }
    @Test
    public void exceptionFindId() {
        filmController.createFilm(qwertyFilm);
        Film qwertyFilm2 = Film.builder()
                .id(2L)
                .name("qwerty2")
                .description("qwerty about qwerty2")
                .releaseDate(LocalDate.of(2003, 3, 3))
                .duration(33L)
                .build();

        Throwable exception = assertThrows(ValidationException.class, () -> filmController.updateFilm(qwertyFilm2));
        assertEquals("Фильм с ID " + qwertyFilm2.getId() + " не найден", exception.getMessage());
    }

}
