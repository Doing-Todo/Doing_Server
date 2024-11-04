package org.skhu.doing.repository;

import org.skhu.doing.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 상태로 Todo 검색
    List<Todo> findByStatus(String status);

    // 설명에 특정 키워드가 포함된 Todo 검색
    List<Todo> findByDescriptionContaining(String keyword);

}
