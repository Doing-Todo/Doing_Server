package org.skhu.doing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "member_folder")
public class MemberFolder {
    @EmbeddedId
    private MemberFolderId id;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @MapsId("folderId")
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    public MemberFolder(Member member, Folder folder) {
        this.member = member;
        this.folder = folder;
        this.id = new MemberFolderId(member.getId(), folder.getId());
    }
}
