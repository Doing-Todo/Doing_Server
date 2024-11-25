package org.skhu.doing.user.service;

import org.skhu.doing.entity.Member;
import org.skhu.doing.user.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // Extract Kakao user information
        Long kakaoMember = Long.valueOf((Integer) attributes.get("id"));
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        String email = kakaoAccount != null ? (String) kakaoAccount.get("email") : null;
        String nickname = properties != null ? (String) properties.get("nickname") : null;

        // Find or create Member
        Member member = memberRepository.findByKakaoMember(kakaoMember)
                .orElseGet(() -> {
                    Member newMember = new Member();
                    newMember.setKakaoMember(kakaoMember);
                    newMember.setEmail(email);
                    newMember.setNickname(nickname);
                    return memberRepository.save(newMember);
                });


        if (member.getEmail() == null || !member.getEmail().equals(email)) {
            member.setEmail(email);
        }
        if (member.getNickname() == null || !member.getNickname().equals(nickname)) {
            member.setNickname(nickname);
        }
        memberRepository.save(member);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id" // Name attribute key
        );
    }
}
