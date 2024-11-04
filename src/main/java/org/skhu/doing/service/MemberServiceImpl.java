package org.skhu.doing.service;

import org.skhu.doing.entity.Member;
import org.skhu.doing.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member findOrCreateMember(Long kakaoMemberId, String email, String nickname) {
        return memberRepository.findByKakaoMember(kakaoMemberId)
                .orElseGet(() -> memberRepository.save(new Member(kakaoMemberId, email, nickname)));
    }

    @Override
    public Optional<Member> findByKakaoMember(Long kakaoMemberId) {
        return memberRepository.findByKakaoMember(kakaoMemberId);
    }

    @Override
    public Member updateMember(Long memberId, String nickname) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setNickname(nickname);
        return memberRepository.save(member);
    }
}

