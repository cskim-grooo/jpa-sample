package grooo.jpa_sample.config.security_filter;

import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import grooo.jpa_sample.common.util.AuthUtil;
import grooo.jpa_sample.config.security_filter.service.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RoleAuthorizationFilter extends OncePerRequestFilter {

    private final AuthorizationService authorizationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        Long workerId = AuthUtil.getAuthenticatedWorkerId(request);
        String requestUri = request.getRequestURI();
        String httpMethod = request.getMethod();
        if (!authorizationService.hasPermission(workerId, requestUri, httpMethod)) {
            throw new org.springframework.security.access.AccessDeniedException(ErrorCode.AUTH_PERMISSION_DENIED.getMessageKey());
        }
        filterChain.doFilter(request, response);
    }
}
