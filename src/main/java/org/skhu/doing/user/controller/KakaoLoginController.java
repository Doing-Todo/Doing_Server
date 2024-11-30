package org.skhu.doing.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skhu.doing.user.KakaoUserInfoResponseDto;
import org.skhu.doing.user.MemberDTO;
import org.skhu.doing.user.service.KakaoService;
import org.skhu.doing.user.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final MemberService memberService;

//    @GetMapping("api/oauth/kakao")
//    public ResponseEntity<?> callback(@RequestParam("code") String code) {
//        String accessToken = kakaoService.getAccessTokenFromKakao(code);
//
//        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
//
//        // User 로그인, 또는 회원가입 로직 추가
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @GetMapping("api/oauth/kakao")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        // Access Token 가져오기
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        // 카카오 사용자 정보 가져오기
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        // 회원 등록 및 정보 저장
        MemberDTO member = memberService.findOrRegisterMember(userInfo);

        // 반환 또는 추가 로직
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}

