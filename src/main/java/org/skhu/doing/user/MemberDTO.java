package org.skhu.doing.user;

import lombok.Data;
import org.skhu.doing.entity.Member;

@Data
public class MemberDTO {
    private Long id;
    private Long kakaoMember;
    private String email;
    private String nickname;

    public static MemberDTO fromEntity(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setId(member.getId());
        dto.setKakaoMember(member.getKakaomember());
        dto.setEmail(member.getEmail());
        dto.setNickname(member.getNickname());
        return dto;
    }
}