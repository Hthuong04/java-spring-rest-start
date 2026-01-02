package vn.hoidanit.todo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import vn.hoidanit.todo.entity.ApiResponse;
import vn.hoidanit.todo.entity.User;
import vn.hoidanit.todo.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        ApiResponse<User> result = new ApiResponse<User>(HttpStatus.CREATED, "createUser", created, null);
        // hoặc
        // var result = new ApiResponse<>(HttpStatus.CREATED, "createUser", created,
        // null);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        ApiResponse<List<User>> result = new ApiResponse<List<User>>(HttpStatus.OK, "getAllUsers",
                userService.getAllUsers(), null);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id).map(user -> {
            var response = new ApiResponse<>(HttpStatus.OK, "getUserById", user, null);
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        ApiResponse<User> result = new ApiResponse<User>(HttpStatus.OK, "updateUser", updated, null);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NoSuchElementException ex) {
        var result = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "handleNotFound", null, ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        ApiResponse<User> result = new ApiResponse<User>(HttpStatus.NO_CONTENT, "deleteUser", null, null);

        return ResponseEntity.ok(result);
    }
}