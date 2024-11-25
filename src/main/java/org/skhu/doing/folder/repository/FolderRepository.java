package org.skhu.doing.folder.repository;

import org.skhu.doing.entity.Folder;
import org.skhu.doing.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f JOIN f.memberFolders mf WHERE mf.member.id = :memberId")
    List<Folder> findByMemberId(@Param("memberId") Long memberId);
}
