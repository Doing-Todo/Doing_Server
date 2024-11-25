package org.skhu.doing.folder.service;

import org.skhu.doing.folder.dto.FolderRequestDTO;
import org.skhu.doing.folder.dto.FolderResponseDTO;

import java.util.List;

public interface FolderService {
    FolderResponseDTO createFolder(FolderRequestDTO folderRequestDTO);
    FolderResponseDTO getFolderDetail(Long folderId);
    FolderResponseDTO updateFolder(Long folderId, FolderRequestDTO folderRequestDTO);
    void deleteFolder(Long folderId);
    List<FolderResponseDTO> getFoldersByMember(Long memberId);
}

