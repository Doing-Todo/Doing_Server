package org.skhu.doing.service;

import org.skhu.doing.entity.Member;

import java.util.Optional;

public interface MemberService {
    Member findOrCreateMember(Long kakaoMemberId, String email, String nickname);
    Optional<Member> findByKakaoMember(Long kakaoMemberId);
    Member updateMember(Long memberId, String nickname);
}
