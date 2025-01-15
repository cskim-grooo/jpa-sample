package grooo.jpa_sample.config;

import grooo.jpa_sample.common.util.JwtAuthenticationFilter;
import grooo.jpa_sample.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // 설정 클래스임을 명시
@EnableWebSecurity  // Spring Security 활성화
@RequiredArgsConstructor // final, nonNull로 선언된 객체를 자동으로 주입시켜줌.
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (REST API)
                .csrf(AbstractHttpConfigurer::disable)
                // 로그인 실패시 리다이렉트 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                // HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // 세션 사용 안 함 (Stateless -> JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 예외 처리 설정 (401 Unauthorized 반환=
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()  // 로그인, 회원가입은 허용
                        .anyRequest().authenticated()             // 나머지는 인증(토큰) 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);  // JWT 필터 등록;
        return http.build();
    }
}
