package grooo.jpa_sample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import grooo.jpa_sample.common.exception.ErrorCode;
import grooo.jpa_sample.common.exception.ErrorResponse;
import grooo.jpa_sample.common.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TranslationService translationService;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        ErrorCode errorCode;
        String exception = (String) request.getAttribute("exception");
        switch (exception) {
            case "ExpiredJwtException" -> errorCode = ErrorCode.AUTH_TOKEN_EXPIRED;
            case "JwtException" -> errorCode = ErrorCode.AUTH_TOKEN_UNAVAILABLE;
            case null, default -> errorCode = ErrorCode.AUTH_NEED_TOKEN;
        }

        String localizedMessage = translationService.translateMessage(errorCode.getMessageKey());

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(errorCode.getStatus().value());

        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus().value(), localizedMessage);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
