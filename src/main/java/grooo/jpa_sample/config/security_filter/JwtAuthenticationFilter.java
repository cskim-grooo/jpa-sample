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
            request.setAttribute("exception", "ExpiredJwtException");
            throw ex;
        } catch (JwtException ex) {
            request.setAttribute("exception", "JwtException");
            throw ex;
        } catch (Exception ex) {
            request.setAttribute("exception", "Exception");
            throw ex;
        }
    }
}
