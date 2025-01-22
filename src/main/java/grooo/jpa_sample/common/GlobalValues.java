package grooo.jpa_sample.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalValues {

    private static final Map<String, Long> languageMap = new HashMap<>(); // lcid -> language_id

    @Getter
    @Value("${spring.web.locale:ja}")
    private static final long defaultLanguageId = 0;

    public static void init(Map<String, Long> initialData) {
        languageMap.clear();
        languageMap.putAll(initialData);
    }

    public static Long getLanguageId() {
        String lcid = LocaleContextHolder.getLocale().toString();
        return getLanguageId(lcid); // 매개변수가 있는 메서드를 호출
    }

    public static Long getLanguageId(String lcid) {
        return languageMap.getOrDefault(lcid, defaultLanguageId);
    }
}
