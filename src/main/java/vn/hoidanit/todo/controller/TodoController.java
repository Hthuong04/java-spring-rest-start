package vn.hoidanit.todo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
import vn.hoidanit.todo.entity.Todo;
import vn.hoidanit.todo.service.TodoService;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodo(@RequestBody Todo input) {
        Todo newTodo = this.todoService.handleCreateTodo(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Todo todoData = this.todoService.getTodoById(id);

        return ResponseEntity.ok().body(todoData);
    }

    @GetMapping("todos")
    public ResponseEntity<List<Todo>> getTodos() {
        // Todo myTodo = new Todo("thuong", true);
        // Todo newTodo = this.todoService.handleCreateTodo(myTodo);
        List<Todo> listTodo = this.todoService.handleGetTodo();
        return ResponseEntity.ok().body(listTodo);
    }

    @PutMapping("todos/{id}")
    public ResponseEntity<String> updateTodo(@PathVariable Long id, @RequestBody Todo input) {
        this.todoService.handleUpdateTodo(id, input);
        return ResponseEntity.ok().body("updated");
    }

    @DeleteMapping("todos/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        this.todoService.handleDeleteTodo(id);
        return ResponseEntity.ok().body("deleted");
    }
}
