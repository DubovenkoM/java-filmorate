package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import static ru.yandex.practicum.filmorate.validator.FilmValidator.isValidFilm;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    protected Long id = 1L;

    @PostMapping()
    public Film createFilm(@RequestBody Film film) {
        if (isValidFilm(film)) {
            film.setId(generatorId());
            films.put(film.getId(), film);
            log.debug("Добавлен фильм: {}", film.getName());
            return film;
        }
        throw new ValidationException("Фильм не может быть добавлен");
    }


    @PutMapping()
    public Film updateFilm(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Фильм с ID {} - не найден", film.getId());
            throw new ValidationException("Фильм с ID " + film.getId() + " не найден");
        }
        films.put(film.getId(), film);
        log.debug("Фильм {} изменён", film.getName());

        return film;
    }

    @GetMapping()
    public List<Film> getFilms() {
        log.debug("Запрос на список фильмов");
        return new ArrayList<>(films.values());
    }

    private Long generatorId() {
        return id++;
    }

}
