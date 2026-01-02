package vn.hoidanit.todo.service;

import java.util.List;
import java.util.Optional;

import vn.hoidanit.todo.entity.User;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    Optional<User> getUserById(Long id);

    User updateUser(Long id, User updatedUser);

    void deleteUser(Long id);
}
