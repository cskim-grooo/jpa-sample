package grooo.jpa_sample.common.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplateUtil {
    private final Configuration freemarkerConfig;
    private static Configuration staticFreemarkerConfig;

    @PostConstruct
    public void init() {
        staticFreemarkerConfig = this.freemarkerConfig;
    }

    public static String buildHtml(String htmlTemplate, Map<String, Object> variables) {
        try {
            Template template = new Template("template", new StringReader(htmlTemplate), staticFreemarkerConfig);
            StringWriter writer = new StringWriter();
            template.process(variables, writer);
            return writer.toString();
        } catch (TemplateException | java.io.IOException e) {
            System.out.println("TemplateException 발생(TemplateException) => " + e.getMessage() );
            throw new CustomException(ErrorCode.UNHANDLED_SERVER_ERROR);
        }
    }
}
