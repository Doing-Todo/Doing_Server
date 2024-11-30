package org.skhu.doing.user.repository;

import org.skhu.doing.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaomember(Long kakaoMember);
    Optional<Member> findByEmail(String email);
}
