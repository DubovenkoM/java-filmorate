package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.validator.MovieBirthday;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class Film {
    final String FIRST_FILM_RELEASE = "1895-12-28";
    private Long id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина более 200 символов")
    private String description;
    @MovieBirthday(value = FIRST_FILM_RELEASE)
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность фильма менее нуля")
    private long duration;
}
