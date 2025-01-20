package grooo.jpa_sample.config.security_filter;

import grooo.jpa_sample.common.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                Claims claims = jwtUtil.validateToken(token);
                if (claims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Long workerId = jwtUtil.getWorkerId(token);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(workerId, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.setAttribute("workerId", workerId);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            // 토큰 만료
            request.setAttribute("exception", "ExpiredJwtException");
            throw new RuntimeException("토큰이 만료되었습니다.", ex);
        } catch (JwtException ex) {
            // 잘못된 토큰
            request.setAttribute("exception", "JwtException");
            throw new RuntimeException("잘못된 토큰입니다.", ex);
        } catch (Exception ex) {
            // 기타 오류
            request.setAttribute("exception", "Exception");
            throw new RuntimeException("인증에 실패하였습니다.", ex);
        }
    }
}
