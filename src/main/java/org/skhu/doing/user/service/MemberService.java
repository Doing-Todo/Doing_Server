package org.skhu.doing.user.service;

import org.skhu.doing.memo.dto.MemoResponseDTO;
import org.skhu.doing.todo.dto.TodoResponseDTO;
import org.skhu.doing.user.MemberDTO;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.List;

public interface MemberService {
    MemberDTO findOrRegisterMember(OAuth2AuthenticationToken authenticationToken);
    MemberDTO getMemberProfile(Long memberId);
    void deleteMember(Long memberId);
    List<MemoResponseDTO> getMemosByMember(String email);
    List<TodoResponseDTO> getTodosByMember(String email);
//    List<ChatroomResponseDTO> getChatroomsByMember(String email, Long chatroomId);
}
