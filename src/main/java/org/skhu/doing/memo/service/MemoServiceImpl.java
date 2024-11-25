package org.skhu.doing.memo.service;

import org.skhu.doing.entity.Folder;
import org.skhu.doing.entity.Memo;
import org.skhu.doing.folder.repository.FolderRepository;
import org.skhu.doing.memo.dto.MemoRequestDTO;
import org.skhu.doing.memo.dto.MemoResponseDTO;
import org.skhu.doing.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;
    private final FolderRepository folderRepository;

    public MemoServiceImpl(MemoRepository memoRepository, FolderRepository folderRepository) {
        this.memoRepository = memoRepository;
        this.folderRepository = folderRepository;
    }

    @Override
    public MemoResponseDTO createMemo(MemoRequestDTO requestDto) {
        Folder folder = folderRepository.findById(requestDto.getFolderId())
                .orElseThrow(() -> new IllegalArgumentException("Folder를 찾을 수 없습니다. id: " + requestDto.getFolderId()));

        Memo memo = new Memo();
        memo.setTitle(requestDto.getTitle());
        memo.setContent(requestDto.getContent());
        memo.setFolder(folder); // Folder와 연관 설정
        Memo savedMemo = memoRepository.save(memo);

        return MemoResponseDTO.fromEntity(savedMemo);

    }

    @Override
    public MemoResponseDTO getMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("Memo 를 찾을 수 없습니다. id: " + memoId));
        return MemoResponseDTO.fromEntity(memo);
    }

    @Override
    public MemoResponseDTO updateMemo(Long memoId, MemoRequestDTO requestDto) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("Memo를 찾을 수 없습니다. id: " + memoId));

        Folder folder = folderRepository.findById(requestDto.getFolderId())
                .orElseThrow(() -> new IllegalArgumentException("Folder를 찾을 수 없습니다. id: " + requestDto.getFolderId()));

        memo.setTitle(requestDto.getTitle());
        memo.setContent(requestDto.getContent());
        memo.setFolder(folder); // Folder와 연관 설정
        Memo updatedMemo = memoRepository.save(memo);

        return MemoResponseDTO.fromEntity(updatedMemo);
    }

    @Override
    public void deleteMemo(Long memoId) {
        Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new IllegalArgumentException("Memo 를 찾을 수 없습니다. id: " + memoId));
        memoRepository.delete(memo);
    }
}
