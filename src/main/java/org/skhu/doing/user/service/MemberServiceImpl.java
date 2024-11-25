package org.skhu.doing.user.service;

import jakarta.persistence.EntityNotFoundException;
import org.skhu.doing.entity.Member;
import org.skhu.doing.user.MemberDTO;
import org.skhu.doing.user.repository.MemberRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member kakaoLogin(OAuth2AuthenticationToken authenticationToken) {
        // OAuth2User에서 사용자 정보 추출
        Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
        Long kakaoMemberId = Long.valueOf(attributes.get("id").toString());

        // kakao_account와 properties는 Map 형태이므로 캐스팅 필요
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) properties.get("nickname");

        // 기존 회원 조회
        Optional<Member> existingMember = memberRepository.findByKakaoMember(kakaoMemberId);
        if (existingMember.isPresent()) {
            return existingMember.get(); // 기존 회원 반환
        }

        // 신규 회원 생성 및 저장
        Member newMember = new Member(kakaoMemberId, email, nickname);
        return memberRepository.save(newMember);
    }

    @Override
    public MemberDTO getMemberProfile(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다. ID: " + memberId));
        return MemberDTO.fromEntity(member);
    }

    @Override
    public void deleteMember(Long memberId) {
        Optional<Member> member = memberRepository.findByKakaoMember(memberId);
        if (member.isPresent()) {
            memberRepository.delete(member.get());
        } else {
            throw new IllegalArgumentException("해당 회원을 찾을 수 없습니다. ID: " + memberId);
        }
    }

//    @Override
//    public List<MemberDTO> getMemos(Long memberId) {
//        // 구현 필요
//    }
//
//    @Override
//    public List<MemberDTO> getTodos(Long memberId) {
//        // 구현 필요
//    }
//
//    @Override
//    public List<MemberDTO> getChats(Long memberId, Long chatroomId) {
//        // 구현 필요
//    }
}

