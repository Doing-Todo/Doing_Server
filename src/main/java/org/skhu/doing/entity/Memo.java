package org.skhu.doing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Note")
@Getter @Setter
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @Column(name = "folder_id", nullable = false)
    private Long folderId;

    @PreUpdate
    public void preUpdate() {
        this.lastUpdateDate = LocalDateTime.now();
    }
}