package org.skhu.doing.service;

import jakarta.transaction.Transactional;
import org.skhu.doing.entity.Todo;
import org.skhu.doing.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // ID로 Todo 조회
    @Override
    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
    }

    // 모든 Todo 조회
    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    // Todo 생성
    @Override
    @Transactional
    public Todo create(Todo todo) {
        return todoRepository.save(todo);
    }

    // Todo 수정
    @Override
    @Transactional
    public Todo update(Long id, Todo todoDetails) {
        Todo todo = findById(id);  // 기존 Todo를 조회하여 존재 확인

        todo.setDescription(todoDetails.getDescription());
        todo.setStatus(todoDetails.getStatus());
        todo.setFolderId(todoDetails.getFolderId());  // 폴더 변경이 필요하다면

        return todoRepository.save(todo);
    }

    // Todo 삭제
    @Override
    @Transactional
    public void delete(Long id) {
        Todo todo = findById(id);  // 먼저 Todo가 존재하는지 확인
        todoRepository.delete(todo);
    }
}

