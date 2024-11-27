package org.skhu.doing.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.skhu.doing.user.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
    }

    // SecurityFilterChain Bean을 생성하는 메소드
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // CORS 설정을 커스터마이즈
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/oauth/kakao/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().permitAll()
                )
                // 커스텀 필터 추가
                .addFilterBefore(new UpgradeRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/api/oauth/kakao")                     // 카카오 OAuth2 로그인 엔드포인트
                .defaultSuccessUrl("/api/oauth/kakao/success")    // 로그인 성공 시 리디렉션 경로
                .failureUrl("/api/oauth/kakao/failure")           // 로그인 실패 시 리디렉션 경로
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(oAuth2UserService)       // 사용자 정보 서비스 설정
                )
        );
        return http.build();
    }

    // CORS 설정
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*")); // 모든 출처 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD")); // 모든 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 쿠키를 포함한 요청 허용
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // 응답 헤더로 Authorization 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL에 대해 CORS 설정

        return source;
    }

    // Upgrade-Insecure-Requests 헤더를 비활성화하는 필터
    public class UpgradeRequestFilter implements Filter {

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
                HttpServletRequest httpRequest = (HttpServletRequest) request;

                // "Upgrade-Insecure-Requests" 헤더를 무시
                if (httpRequest.getHeader("Upgrade-Insecure-Requests") != null) {
                    httpRequest = new HttpServletRequestWrapper(httpRequest) {
                        @Override
                        public String getHeader(String name) {
                            if ("Upgrade-Insecure-Requests".equalsIgnoreCase(name)) {
                                return null; // 헤더 무시
                            }
                            return super.getHeader(name);
                        }
                    };
                }

                chain.doFilter(httpRequest, response);
            } else {
                chain.doFilter(request, response); // HttpServletRequest가 아닐 경우 그대로 진행
            }
        }
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String id = defaultOAuth2User.getAttributes().get("id").toString();
            String body = """
                    {"id":"%s"}
                    """.formatted(id);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            PrintWriter writer = response.getWriter();
            writer.println(body);
            writer.flush();
        });
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + exception.getMessage());
        };
    }
}
