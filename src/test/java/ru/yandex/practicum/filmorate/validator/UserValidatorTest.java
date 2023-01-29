package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import javax.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {
    UserController userController = new UserController();
    User userQwerty;

    @BeforeEach
    public void beforeEach() {
        userQwerty = User.builder()
                .id(1L)
                .email("qwerty@email.com")
                .login("qwerty")
                .name("qwerty")
                .birthday(LocalDate.of(2003, 3, 3))
                .build();

    }

    @Test
    public void returnUsers() {
        userController.createUser(userQwerty);
        assertTrue(userController.getUsers().contains(userQwerty));

    }
    @Test
    public void createNameFromLogin() {
        userQwerty.setName("");
        User user = userController.createUser(userQwerty);
        assertEquals(user.getName(), userQwerty.getLogin());
    }
    @Test
    public void exceptionsEmail() {
        userQwerty.setEmail("");
        Throwable exception = assertThrows(ValidationException.class, () -> userController.createUser(userQwerty));
        assertEquals("Email пустой или не содержат символ @", exception.getMessage());
        userQwerty.setEmail("eMail");
        assertEquals("Email пустой или не содержат символ @", exception.getMessage());

    }
    @Test
    public void exceptionsLogin() {
        userQwerty.setLogin("");
        Throwable exception = assertThrows(ValidationException.class, () -> userController.createUser(userQwerty));
        assertEquals("LOGIN пустой или содержит пробелы", exception.getMessage());
        userQwerty.setLogin("qwerty qwerty");
        assertEquals("LOGIN пустой или содержит пробелы", exception.getMessage());
        User userQwerty2 = User.builder()
                .id(2L)
                .email("qwerty@email.com")
                .login("qwerty")
                .name("qwerty")
                .birthday(LocalDate.of(2003, 3, 3))
                .build();
        Throwable exceptionUpdate = assertThrows(ValidationException.class, () -> userController.updateUser(userQwerty2));
        assertEquals("Пользователь с ID " + userQwerty2.getId() + " не найден", exceptionUpdate.getMessage());

    }
    @Test
    public void exceptionDate() {
        userQwerty.setBirthday(LocalDate.of(3333, 3, 3));
        Throwable exception = assertThrows(ValidationException.class, () -> userController.createUser(userQwerty));
        assertEquals("DATE превышает текущую дату", exception.getMessage());
    }

}
