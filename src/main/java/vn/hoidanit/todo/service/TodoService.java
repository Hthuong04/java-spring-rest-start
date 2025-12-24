package vn.hoidanit.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.todo.entity.Todo;
import vn.hoidanit.todo.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo handleCreateTodo(Todo todo) {
        Todo createdTodo = this.todoRepository.save(todo);
        return createdTodo;
    }

    public Todo getTodoById(Long id) {
        Optional<Todo> todoOptional = this.todoRepository.findById(id);
        return todoOptional.isPresent() ? todoOptional.get() : null;
    }

    public List<Todo> handleGetTodo() {
        return this.todoRepository.findAll();
    }

    public void handleUpdateTodo(Long id, Todo inputTodo) {
        Optional<Todo> todoOptional = this.todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            Todo currentTodo = todoOptional.get();
            currentTodo.setUsername(inputTodo.getUsername());
            currentTodo.setCompleted(inputTodo.isCompleted());
            this.todoRepository.save(currentTodo);
        }
    }

    public void handleDeleteTodo(Long id) {
        Optional<Todo> todoOptional = this.todoRepository.findById(id);
        if (todoOptional.isPresent()) {
            Todo currentTodo = todoOptional.get();
            this.todoRepository.deleteById(currentTodo.getId());
        }
    }
}
