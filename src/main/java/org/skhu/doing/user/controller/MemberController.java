package org.skhu.doing.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.skhu.doing.entity.Member;
import org.skhu.doing.user.MemberDTO;
import org.skhu.doing.user.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/oauth/kakao")
@Tag(name = "Member", description = "Member Controller")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "카카오 소셜 로그인", description = "카카오 소셜 로그인 기능입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @GetMapping
    public ResponseEntity<MemberDTO> kakaoLogin(OAuth2AuthenticationToken authenticationToken) {
        Member member = memberService.kakaoLogin(authenticationToken);
        MemberDTO response = MemberDTO.fromEntity(member);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "마이페이지 조회", description = "회원의 마이페이지 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getMemberProfile(@PathVariable("memberId") Long memberId) {
        MemberDTO response = memberService.getMemberProfile(memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 탈퇴", description = "회원의 계정을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteMember() {
        // SecurityContextHolder에서 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 ID 가져오기
        Long kakaoMember = Long.valueOf(oAuth2User.getAttribute("id"));

        // 회원 탈퇴 처리
        memberService.deleteMember(kakaoMember);

        return ResponseEntity.ok().build();
    }
//    @Operation(summary = "작성한 메모 목록 조회", description = "회원이 작성한 메모 목록을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "메모 목록 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "회원 또는 메모를 찾을 수 없음")
//    })
//    @GetMapping("/memo")
//    public ResponseEntity<?> getMemberMemos() {
//        return ResponseEntity.ok(memberService.getMemberMemos());
//    }
//
//    @Operation(summary = "작성한 투두 목록 조회", description = "회원이 작성한 투두 목록을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "투두 목록 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "회원 또는 투두를 찾을 수 없음")
//    })
//    @GetMapping("/todo")
//    public ResponseEntity<?> getMemberTodos() {
//        return ResponseEntity.ok(memberService.getMemberTodos());
//    }
//
//    @Operation(summary = "참여 중인 채팅 목록 조회", description = "회원이 참여 중인 그룹 채팅 목록을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "채팅 목록 조회 성공"),
//            @ApiResponse(responseCode = "404", description = "회원 또는 채팅 목록을 찾을 수 없음")
//    })
//    @GetMapping("/chat/{chatroomId}")
//    public ResponseEntity<?> getMemberChatRooms(@PathVariable("chatroomId") Long chatroomId) {
//        return ResponseEntity.ok(memberService.getMemberChatRooms(chatroomId));
//    }
}

