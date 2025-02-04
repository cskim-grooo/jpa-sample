package grooo.jpa_sample.api.converter.service;

import grooo.jpa_sample.api.converter.domain.Language;
import grooo.jpa_sample.api.converter.dto.ResponseFile;
import grooo.jpa_sample.api.converter.dto.ResponseImport;
import grooo.jpa_sample.api.converter.repository.LanguageRepository;
import grooo.jpa_sample.api.converter.service.excel.ExcelService;
import grooo.jpa_sample.api.converter.service.pdf.PdfService;
import grooo.jpa_sample.common.util.TemplateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.Long.parseLong;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final LanguageRepository languageRepository;
    private final ExcelService excelService;
    private final PdfService pdfService;

    public ResponseFile exportLanguages() {
        List<Language> languages = languageRepository.findAll();
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        // TODO : EasyExcel 사용 - 데이터 모델을 자동으로 변환하여 Excel&시트를 생성 ( 개별적으로 셀을 조작하기에 제한적임(Apache POI 일부기능 사용). 대용량 엑셀 처리 빠름. )
        excelService.createEasyExcel(os, languages);
        // TODO : Apache POI 사용 - 엑셀을 추가적으로 조작(시트생성),개별 좌표(x, y) 지정 후 셀 입력 ( 모든 조작이 자유롭게 가능하지만 메모리사용량 많고 느리다. )
        excelService.writeExcelWithApachePOI(os, "apachePOI-시트명", languages);

        return new ResponseFile("export_languages.xlsx", os.toByteArray());
    }

    public List<ResponseImport> xlsxToJsonFormLanguages(MultipartFile file) {
        List<Map<Integer, String>> rawData = excelService.readExcelAsRawData(file);
        return convertResponseImport(rawData);
    }

    private List<ResponseImport> convertResponseImport(List<Map<Integer, String>> rawData) {
        List<ResponseImport> result = new ArrayList<>();
        if (rawData.isEmpty()) return result;

        Map<Integer, String> headerRow = rawData.getFirst();
        List<String> headers = new ArrayList<>(headerRow.values());

        for (int i = 1; i < rawData.size(); i++) {
            Map<Integer, String> rowData = rawData.get(i);
            ResponseImport responseImport = new ResponseImport();

            for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                String cellValue = rowData.getOrDefault(colIndex, "");
                switch (headers.get(colIndex)) {
                    case "id" -> responseImport.setId(parseLong(cellValue));
                    case "name" -> responseImport.setName(cellValue);
                    case "code" -> responseImport.setCode(cellValue);
                    case "lcid" -> responseImport.setLcid(cellValue);
                }
            }
            result.add(responseImport);
        }
        return result;
    }

    public ResponseFile generatePdfWithLanguages() {
        List<Language> languages = languageRepository.findAll();

        Map<String, Object> variables = new HashMap<>();
        variables.put("title", "HTML TO PDF CONVERT TEST");
        variables.put("languages", languages);
        String processedHtml = TemplateUtil.buildHtml(exampleHtmlTemplate(), variables);

        byte[] pdfByte = pdfService.generatePdf(processedHtml);

        return new ResponseFile("example.pdf", pdfByte);
    }

    private String exampleHtmlTemplate() {
        return """
    <html>
    <head>
        <style>
            body { font-family: Arial, sans-serif; margin: 20px; }
            h1 { color: blue; }
            table { width: 100%%; border-collapse: collapse; margin-top: 20px; }
            th, td { border: 1px solid black; padding: 8px; text-align: left; }
            th { background-color: #f2f2f2; }
        </style>
    </head>
    <body>
        <h1>${title}</h1>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>CODE</th>
                    <th>LCID</th>
                </tr>
            </thead>
            <tbody>
                <#list languages as lang>
                    <tr>
                        <td>${lang.id}</td>
                        <td>${lang.name}</td>
                        <td>${lang.code}</td>
                        <td>${lang.lcid}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </body>
    </html>
        """;
    }
}


