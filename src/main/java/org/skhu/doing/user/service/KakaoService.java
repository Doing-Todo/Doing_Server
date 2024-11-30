package org.skhu.doing.user.service;

import org.skhu.doing.user.KakaoUserInfoResponseDto;

public interface KakaoService {
    String getAccessTokenFromKakao(String code);
    KakaoUserInfoResponseDto getUserInfo(String accessToken);
}