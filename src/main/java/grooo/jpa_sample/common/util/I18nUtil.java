package grooo.jpa_sample.common.util;

import java.util.List;

public class I18nUtil {

    public static <T> T filterByLanguageId(List<T> i18nList, Long languageId, Long defaultLanguageId) {
        if (i18nList == null || i18nList.isEmpty()) {
            return null;
        }

        T result = filterByLanguageId(i18nList, languageId);
        if (result == null) {
            result = filterByLanguageId(i18nList, defaultLanguageId);
        }
        return result;
    }

    private static <T> T filterByLanguageId(List<T> i18nList, Long targetLanguageId) {
        return i18nList.stream()
                .filter(i18n -> {
                    try {
                        Object id = i18n.getClass().getMethod("getId").invoke(i18n);
                        Object langId = id.getClass().getMethod("getLanguageId").invoke(id);
                        return langId != null && langId.equals(targetLanguageId);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);
    }
}
