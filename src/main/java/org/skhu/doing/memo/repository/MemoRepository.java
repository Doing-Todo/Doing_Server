package org.skhu.doing.memo.repository;

import org.skhu.doing.entity.Member;
import org.skhu.doing.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByMember(Member member);
}
