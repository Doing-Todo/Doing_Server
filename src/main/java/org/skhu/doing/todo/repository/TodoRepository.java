package org.skhu.doing.todo.repository;

import org.skhu.doing.entity.Member;
import org.skhu.doing.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByMember(Member member);
}
