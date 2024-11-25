package org.skhu.doing.todo.service;

import org.skhu.doing.entity.Todo;
import org.skhu.doing.todo.dto.TodoRequestDTO;
import org.skhu.doing.todo.dto.TodoResponseDTO;
import org.skhu.doing.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO requestDto) {
        Todo todo = new Todo();
        todo.setDescription(requestDto.getDescription());
        todo.setStatus(requestDto.getStatus());
        todo.setFolderId(requestDto.getFolderId());
        Todo savedTodo = todoRepository.save(todo);
        return TodoResponseDTO.fromEntity(savedTodo);
    }

    @Override
    public TodoResponseDTO getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("TODO 를 찾을 수 없습니다. id: " + todoId));
        return TodoResponseDTO.fromEntity(todo);
    }

    @Override
    public TodoResponseDTO updateTodo(Long todoId, TodoRequestDTO requestDto) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("TODO 를 찾을 수 없습니다. id: " + todoId));

        todo.setDescription(requestDto.getDescription());
        todo.setStatus(requestDto.getStatus());
        todo.setFolderId(requestDto.getFolderId());
        Todo updatedTodo = todoRepository.save(todo);

        return TodoResponseDTO.fromEntity(updatedTodo);
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("TODO 를 찾을 수 없습니다. id: " + todoId));
        todoRepository.delete(todo);
    }
}

