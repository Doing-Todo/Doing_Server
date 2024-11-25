package org.skhu.doing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Getter
public class MemberFolderId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "folder_id")
    private Long folderId;

    public MemberFolderId(Long memberId, Long folderId) {
        this.memberId = memberId;
        this.folderId = folderId;
    }
}

