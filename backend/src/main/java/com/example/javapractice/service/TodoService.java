package com.example.javapractice.service;

import com.example.javapractice.entity.Todo;
import com.example.javapractice.repository.TodoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> update(Long id, boolean completed) {
        return todoRepository.findById(id).map(todo -> {
            todo.setCompleted(completed);
            return todoRepository.save(todo);
        });
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }
}
