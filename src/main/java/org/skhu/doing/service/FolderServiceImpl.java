package org.skhu.doing.service;

import org.skhu.doing.entity.Folder;
import org.skhu.doing.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;

    @Autowired
    public FolderServiceImpl(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    @Override
    public Folder createFolder(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public Folder getFolderById(Long id) {
        return folderRepository.findById(id).orElseThrow(() -> new RuntimeException("Folder not found"));
    }

    @Override
    public List<Folder> getFoldersByMemberId(Long memberId) {
        return folderRepository.findByMemberId(memberId);
    }

    @Override
    public Folder updateFolder(Long id, Folder updatedFolder) {
        Folder existingFolder = getFolderById(id);
        existingFolder.setName(updatedFolder.getName());
        return folderRepository.save(existingFolder);
    }

    @Override
    public void deleteFolderById(Long id) {
        folderRepository.deleteById(id);
    }
}
