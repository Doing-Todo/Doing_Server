package org.skhu.doing.repository;

import org.skhu.doing.entity.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByMemberId(Long memberId);
}