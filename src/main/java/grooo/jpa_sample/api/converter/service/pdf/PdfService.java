package grooo.jpa_sample.api.converter.service.pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(String templateHtml) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.useFastMode();
            builder.withHtmlContent(templateHtml, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        } catch (Exception e) {
            System.out.println("PDF 변환 실패 =>"+ e.getMessage());
            throw new CustomException(ErrorCode.UNHANDLED_SERVER_ERROR);
        }
    }
}
