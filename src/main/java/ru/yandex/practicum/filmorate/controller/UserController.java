package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static ru.yandex.practicum.filmorate.validator.UserValidator.isValidUser;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Long, User> users = new HashMap<>();
    private Long id = 1L;

    @PostMapping()
    public User createUser(@RequestBody User user) {
        if (isValidUser(user)) {
            if ((user.getName() == null) || (user.getName().isBlank())) {
                user.setName(user.getLogin());
                log.debug("Имя не указано — будет использован логин");
            }
            user.setId(generatorId());
            users.put(user.getId(), user);
            log.debug("Пользователь {} успешно создан", user.getLogin());
        }
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.warn("Пользователя с ID {} - не найден", user.getId());
            throw new ValidationException("Пользователь с ID " + user.getId() + " не найден");
        }
        if (isValidUser(user)) {
            users.put(user.getId(), user);
            log.debug("Пользователь с {} успешно изменён", user.getLogin());
        }
        return user;
    }

    @GetMapping()
    public List<User> getUsers() {
        log.debug("Запрос на список пользователей");
        return new ArrayList<>(users.values());
    }

    private Long generatorId() {
        return id++;
    }

}

