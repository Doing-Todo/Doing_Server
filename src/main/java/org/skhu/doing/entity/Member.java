package org.skhu.doing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 추가
    private Long id;

    @Column(nullable = false, unique = true)
    private Long kakaomember;

    @Column(length = 255)
    private String email;

    @Column(length = 100)
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberFolder> memberFolders = new ArrayList<>();
}
