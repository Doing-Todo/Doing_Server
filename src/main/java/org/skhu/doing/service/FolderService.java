package org.skhu.doing.service;

import org.skhu.doing.entity.Folder;

import java.util.List;

public interface FolderService {
    Folder createFolder(Folder folder);
    Folder getFolderById(Long id);
    List<Folder> getFoldersByMemberId(Long memberId);
    Folder updateFolder(Long id, Folder folder);
    void deleteFolderById(Long id);
}
