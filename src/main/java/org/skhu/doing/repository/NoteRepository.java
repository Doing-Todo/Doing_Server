package org.skhu.doing.repository;

import org.skhu.doing.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByFolderId(Long folderId);
}
