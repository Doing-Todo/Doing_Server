package org.skhu.doing.memo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class MemoRequestDTO {
    private String title;
    private String content;
    private Long folderId;
}

