package org.skhu.doing.user.service;

import org.skhu.doing.user.MemberDTO;
import org.skhu.doing.entity.Member;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.List;

public interface MemberService {
    Member kakaoLogin(OAuth2AuthenticationToken authenticationToken);
    MemberDTO getMemberProfile(Long memberId);
    void deleteMember(Long memberId);
//    List<MemberDTO> getMemberMemos(Long memberId);
//    List<MemberDTO> getMemberTodos(Long memberId);
//    List<MemberDTO> getMemberChatRooms(Long memberId, Long chatroomId);
}
