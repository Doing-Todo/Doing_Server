package org.skhu.doing.repository;

import org.skhu.doing.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Memo, Long> {
    List<Memo> findByFolderId(Long folderId);
}
