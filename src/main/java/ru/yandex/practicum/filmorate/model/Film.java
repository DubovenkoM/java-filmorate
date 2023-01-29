package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.validator.MovieBirthday;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Film {
    final String FIRST_FILM_RELEASE = "1895-12-28";
    Long id;
    @NotBlank(message = "Название не может быть пустым")
    String name;
    @Size(max = 200, message = "Максимальная длина более 200 символов")
    String description;
    @MovieBirthday(value = FIRST_FILM_RELEASE)
    LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность фильма менее нуля")
    long duration;
}
