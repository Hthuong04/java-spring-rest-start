package vn.hoidanit.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.repository.UserRepository;
import vn.hoidanit.todo.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

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

    @Test
    public void getAllUser_shouldReturnAllUsers() {
        // arrage: chuẩn bị
        List<User> outputUsers = new ArrayList<>();
        outputUsers.add(new User(1L, "thuong", "thuong@gmail.com"));
        outputUsers.add(new User(2L, "thuong2", "thuong2@gmail.com"));

        when(this.userRepository.findAll()).thenReturn(outputUsers);
        // act : hành động
        List<User> result = this.userService.getAllUsers();

        // assert : so sánh
        assertEquals(2, result.size());
        assertEquals("thuong@gmail.com", result.get(0).getEmail());
    }

    @Test
    public void getUserById_shouldReturnOptionalUser() {
        // arrage: chuẩn bị
        Long inputId = 1L;
        User inputUser = new User(1L, "thuong", "thuong@gmail.com");
        Optional<User> userOptionalOutput = Optional.of(inputUser);

        when(this.userRepository.findById(inputId)).thenReturn(userOptionalOutput);
        // act : hành động

        Optional<User> result = this.userService.getUserById(inputId);
        // assert : so sánh
        assertEquals(true, result.isPresent());

    }

    @Test
    public void deleteUser_shouldReturnVoid_whenUserExist() {
        // arrage: chuẩn bị
        Long inputId = 1L;
        when(this.userRepository.existsById(inputId)).thenReturn(true);
        // act : hành động
        this.userService.deleteUser(inputId);
        // assert : so sánh
        verify(this.userRepository).deleteById(inputId);
    }

    @Test
    public void deleteUser_shouldReturnException_whenUserNotExist() {
        // arrage: chuẩn bị
        Long inputId = 1L;
        when(this.userRepository.existsById(inputId)).thenReturn(false);
        // act : hành động
        Exception ex = assertThrows(NoSuchElementException.class, () -> {
            this.userService.deleteUser(inputId);
        });

        // assert : so sánh
        assertEquals("User not found", ex.getMessage());

    }

    @Test
    public void update_shouldReturnUser_whenValid() {
        // arrage: chuẩn bị
        Long inputId = 1L;
        User inputUser = new User(1L, "oldName", "oldEmail@gmail.com");
        User outputUser = new User(1L, "newName", "newEmail@gmail.com");
        when(this.userRepository.findById(inputId)).thenReturn(Optional.of(inputUser));
        when(this.userRepository.save(any())).thenReturn(outputUser);

        // act : hành động
        User result = this.userService.updateUser(inputId, inputUser);
        // assert : so sánh
        assertEquals("newName", result.getName());
    }
}
