package org.skhu.doing.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long kakaoMember;

    @Column(length = 255)
    private String email;

    @Column(length = 100)
    private String nickname;

    public Member(Long kakaoMember, String email, String nickname) {
        this.kakaoMember = kakaoMember;
        this.email = email;
        this.nickname = nickname;
    }
}

