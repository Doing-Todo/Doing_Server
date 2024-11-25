package org.skhu.doing.todo.service;

import org.skhu.doing.entity.Folder;
import org.skhu.doing.entity.Todo;
import org.skhu.doing.folder.repository.FolderRepository;
import org.skhu.doing.todo.dto.TodoRequestDTO;
import org.skhu.doing.todo.dto.TodoResponseDTO;
import org.skhu.doing.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FolderRepository folderRepository;

    public TodoServiceImpl(TodoRepository todoRepository, FolderRepository folderRepository) {
        this.todoRepository = todoRepository;
        this.folderRepository = folderRepository;
    }

    @Override
    public TodoResponseDTO createTodo(TodoRequestDTO requestDto) {
        Folder folder = folderRepository.findById(requestDto.getFolderId())
                .orElseThrow(() -> new IllegalArgumentException("Folder를 찾을 수 없습니다. id: " + requestDto.getFolderId()));

        Todo todo = new Todo();
        todo.setDescription(requestDto.getDescription());
        todo.setStatus(requestDto.getStatus());
        todo.setFolder(folder);
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
                .orElseThrow(() -> new IllegalArgumentException("Todo를 찾을 수 없습니다. id: " + todoId));

        Folder folder = folderRepository.findById(requestDto.getFolderId())
                .orElseThrow(() -> new IllegalArgumentException("Folder를 찾을 수 없습니다. id: " + requestDto.getFolderId()));

        todo.setDescription(requestDto.getDescription());
        todo.setStatus(requestDto.getStatus());
        todo.setFolder(folder); // Folder와 연관 설정
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

