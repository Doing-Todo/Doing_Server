package org.skhu.doing.memo.service;

import org.skhu.doing.entity.Memo;
import org.skhu.doing.memo.dto.MemoRequestDTO;
import org.skhu.doing.memo.dto.MemoResponseDTO;
import org.skhu.doing.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDTO createMemo(MemoRequestDTO requestDto) {
        Memo memo = new Memo();
        memo.setTitle(requestDto.getTitle());
        memo.setContent(requestDto.getContent());
        memo.setFolderId(requestDto.getFolderId());
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
                .orElseThrow(() -> new IllegalArgumentException("Memo 를 찾을 수 없습니다. id: " + memoId));

        memo.setTitle(requestDto.getTitle());
        memo.setContent(requestDto.getContent());
        memo.setFolderId(requestDto.getFolderId());
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
