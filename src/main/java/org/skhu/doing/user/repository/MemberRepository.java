package org.skhu.doing.user.repository;

import org.skhu.doing.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoMember(Long kakaoMember);
}