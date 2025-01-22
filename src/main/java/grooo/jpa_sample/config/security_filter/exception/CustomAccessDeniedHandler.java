package grooo.jpa_sample.config.security_filter.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import grooo.jpa_sample.common.exception.ErrorCode;
import grooo.jpa_sample.common.exception.ErrorResponse;
import grooo.jpa_sample.common.service.TranslationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TranslationService translationService;
    private final LocaleResolver localeResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        LocaleContextHolder.setLocale(localeResolver.resolveLocale(request));
        ErrorCode permissionDenied = ErrorCode.AUTH_PERMISSION_DENIED;
        String localizedMessage = translationService.translateMessage(permissionDenied.getMessageKey());

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(permissionDenied.getStatus().value());

        ErrorResponse errorResponse = new ErrorResponse(permissionDenied.getStatus().value(), localizedMessage);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
