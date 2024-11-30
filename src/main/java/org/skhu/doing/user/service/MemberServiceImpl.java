package org.skhu.doing.user.service;

import jakarta.persistence.EntityNotFoundException;
import org.skhu.doing.entity.Folder;
import org.skhu.doing.entity.Member;
import org.skhu.doing.folder.repository.FolderRepository;
import org.skhu.doing.memo.dto.MemoResponseDTO;
import org.skhu.doing.memo.repository.MemoRepository;
import org.skhu.doing.todo.dto.TodoResponseDTO;
import org.skhu.doing.todo.repository.TodoRepository;
import org.skhu.doing.user.KakaoUserInfoResponseDto;
import org.skhu.doing.user.MemberDTO;
import org.skhu.doing.user.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final FolderRepository folderRepository;
    private final TodoRepository todoRepository;
    private final MemoRepository memoRepository;

    public MemberServiceImpl(MemberRepository memberRepository, FolderRepository folderRepository, TodoRepository todoRepository, MemoRepository memoRepository) {
        this.memberRepository = memberRepository;
        this.folderRepository = folderRepository;
        this.todoRepository = todoRepository;
        this.memoRepository = memoRepository;
    }

    @Override
    public MemberDTO findOrRegisterMember(KakaoUserInfoResponseDto userInfo) {
        Long kakaoMemberId = userInfo.getId();
        String email = userInfo.getKakaoAccount().getEmail();
        String nickname = userInfo.getKakaoAccount().getProfile().getNickName();

        // 회원 정보 저장 또는 업데이트
        Member member = memberRepository.findByKakaoMember(kakaoMemberId)
                .orElseGet(() -> {
                    Member newMember = new Member();
                    newMember.setKakaoMember(kakaoMemberId);
                    newMember.setEmail(email);
                    newMember.setNickname(nickname);
                    return memberRepository.save(newMember);
                });

        // 이메일과 닉네임 업데이트
        if (email != null && !email.equals(member.getEmail())) {
            member.setEmail(email);
        }
        if (nickname != null && !nickname.equals(member.getNickname())) {
            member.setNickname(nickname);
        }
        memberRepository.save(member);

        return MemberDTO.fromEntity(member);
    }

    @Override
    public MemberDTO getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다. ID: " + memberId));
        return MemberDTO.fromEntity(member);
    }

    @Override
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다. ID: " + memberId));
        memberRepository.delete(member);
    }

    private <T> List<T> getItemsByMember(String email, Function<Folder, List<T>> repositoryMethod) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다. email: " + email));

        // Member ID로 Folder를 조회
        List<Folder> folders = folderRepository.findByMemberId(member.getId());

        // Folder별로 repositoryMethod를 실행하여 메모 또는 투두를 찾음
        return folders.stream()
                .flatMap(folder -> repositoryMethod.apply(folder).stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<MemoResponseDTO> getMemosByMember(String email) {
        return getItemsByMember(email, folder -> memoRepository.findByFolder(folder).stream()
                .map(MemoResponseDTO::fromEntity)
                .collect(Collectors.toList()));
    }

    @Override
    public List<TodoResponseDTO> getTodosByMember(String email) {
        return getItemsByMember(email, folder -> todoRepository.findByFolder(folder).stream()
                .map(TodoResponseDTO::fromEntity)
                .collect(Collectors.toList()));
    }

//    @Override
//    public List<ChatroomResponseDTO> getChatroomsByMember(String email, Long chatroomId) {
//        Member member = memberRepository.findByEmail(email)
//                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다. email: " + email));
//
//        // Member -> MemberChatroom -> Chatroom 관계를 조회
//        return chatroomRepository.findByMemberAndChatroomId(member, chatroomId).stream()
//                .map(ChatroomResponseDTO::fromEntity)
//                .collect(Collectors.toList());
//    }
}

