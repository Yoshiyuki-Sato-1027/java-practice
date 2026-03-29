package com.example.javapractice.service;

import com.example.javapractice.entity.Todo;
import com.example.javapractice.repository.TodoDao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {

    private final TodoDao todoDao;

    public TodoService(TodoDao todoDao) {
        this.todoDao = todoDao;
    }

    public List<Todo> findAll() {
        return todoDao.selectAll();
    }

    public Optional<Todo> findById(Long id) {
        return todoDao.selectById(id);
    }

    @Transactional
    public Todo create(Todo todo) {
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
        todoDao.insert(todo);
        return todo;
    }

    @Transactional
    public Optional<Todo> update(Long id, boolean completed) {
        return todoDao.selectById(id).map(todo -> {
            todo.setCompleted(completed);
            todo.setUpdatedAt(LocalDateTime.now());
            todoDao.update(todo);
            return todo;
        });
    }

    @Transactional
    public void delete(Long id) {
        todoDao.selectById(id).ifPresent(todoDao::delete);
    }
}
