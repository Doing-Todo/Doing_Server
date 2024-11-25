package org.skhu.doing.memo.service;

import org.skhu.doing.memo.dto.MemoRequestDTO;
import org.skhu.doing.memo.dto.MemoResponseDTO;

public interface MemoService {
    MemoResponseDTO createMemo(MemoRequestDTO requestDto);
    MemoResponseDTO getMemo(Long memoId);
    MemoResponseDTO updateMemo(Long memoId, MemoRequestDTO requestDto);
    void deleteMemo(Long memoId);
}