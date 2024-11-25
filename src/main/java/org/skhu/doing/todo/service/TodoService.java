package org.skhu.doing.todo.service;

import org.skhu.doing.todo.dto.TodoRequestDTO;
import org.skhu.doing.todo.dto.TodoResponseDTO;

public interface TodoService {
    TodoResponseDTO createTodo(TodoRequestDTO requestDto);
    TodoResponseDTO getTodo(Long todoId);
    TodoResponseDTO updateTodo(Long todoId, TodoRequestDTO requestDto);
    void deleteTodo(Long todoId);
}
