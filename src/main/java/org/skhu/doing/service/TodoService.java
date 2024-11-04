package org.skhu.doing.service;

import org.skhu.doing.entity.Todo;

import java.util.List;

public interface TodoService {
    Todo findById(Long id);
    List<Todo> findAll();
    Todo create(Todo todo);
    Todo update(Long id, Todo todo);
    void delete(Long id);
}

