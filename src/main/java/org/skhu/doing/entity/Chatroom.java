package org.skhu.doing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chatroom")
@Getter @Setter
@NoArgsConstructor
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChatroomType type = ChatroomType.one;  // 기본값 설정

    private Long createdBy;

    @OneToMany(mappedBy = "chatroom")
    private List<MemberChatroom> memberChatrooms = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom")
    private List<Message> messages = new ArrayList<>();

    public enum ChatroomType {
        one, group
    }
}
