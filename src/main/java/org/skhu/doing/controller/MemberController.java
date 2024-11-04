package org.skhu.doing.controller;

import org.skhu.doing.entity.Member;
import org.skhu.doing.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 정보 조회 (예: 카카오 로그인 ID로 회원 조회)
    @GetMapping("/{kakaoMemberId}")
    public ResponseEntity<Member> getMemberByKakaoId(@PathVariable Long kakaoMemberId) {
        return memberService.findByKakaoMember(kakaoMemberId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 신규 회원 생성 또는 기존 회원 조회 (카카오 로그인 후 호출)
    @PostMapping("/login")
    public ResponseEntity<Member> loginOrCreateMember(@RequestParam Long kakaoMemberId,
                                                      @RequestParam String email,
                                                      @RequestParam String nickname) {
        Member member = memberService.findOrCreateMember(kakaoMemberId, email, nickname);
        return ResponseEntity.ok(member);
    }

    // 회원 정보 업데이트 (닉네임 변경 등)
    @PutMapping("/{memberId}")
    public ResponseEntity<Member> updateMember(@PathVariable Long memberId,
                                               @RequestParam String nickname) {
        Member updatedMember = memberService.updateMember(memberId, nickname);
        return ResponseEntity.ok(updatedMember);
    }
}


