package vn.hoidanit.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser_shouldReturnUser_whenEmailVaild() {
        // arrage: chuẩn bị
        User inputUser = new User(null, "thuong", "thuong@gmail.com");
        User outputUser = new User(1L, "thuong", "thuong@gmail.com");
        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(false);
        when(this.userRepository.save(any())).thenReturn(outputUser);

        // act : hành động
        User result = this.userService.createUser(inputUser);

        // assert : so sánh
        assertEquals(1L, result.getId());
    }

    @Test
    public void createUser_shouldThrowException_whenEmailInvaild() {
        // arrage: chuẩn bị
        User inputUser = new User(null, "thuong", "thuong@gmail.com");
        when(this.userRepository.existsByEmail(inputUser.getEmail())).thenReturn(true);

        // act : hành động
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            this.userService.createUser(inputUser);
        });

        // assert : so sánh
        assertEquals("Email already exists", ex.getMessage());
    }
}
