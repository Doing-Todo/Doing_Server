package org.skhu.doing.memo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.skhu.doing.entity.Memo;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoResponseDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Long folderId;

    public static MemoResponseDTO fromEntity(Memo memo) {
        return new MemoResponseDTO(
                memo.getId(),
                memo.getTitle(),
                memo.getContent(),
                memo.getCreateDate(),
                memo.getLastUpdateDate(),
                memo.getFolderId()
        );
    }
}
