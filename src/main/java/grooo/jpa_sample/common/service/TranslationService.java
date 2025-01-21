package grooo.jpa_sample.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class TranslationService {

    private final MessageSource messageSource;

    public String translateMessage(String messageKey, Object ...args) {
        return messageSource.getMessage(
                messageKey,
                args,
                messageKey,
                LocaleContextHolder.getLocale()
        );
    }

    public Object[] translateStringArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return args;
        }
        return Arrays.stream(args).map(arg -> {
            if (arg instanceof String) {
                System.out.println("arg=>"+arg);
                return translateMessage((String) arg);
            }
            return arg;
        }).toArray();
    }
}
