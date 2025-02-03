package grooo.jpa_sample.api.converter;

import grooo.jpa_sample.api.converter.dto.ResponseFile;
import grooo.jpa_sample.api.converter.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/converter", "/converter/"})
public class ConverterController {

    private final ConverterService converterService;

    @GetMapping("/sample-excel")
    public ResponseEntity<byte[]> exportLanguages() {
        ResponseFile responseFile = converterService.exportLanguages();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + responseFile.getFileName() + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return new ResponseEntity<>(responseFile.getBytes(), headers, HttpStatus.OK);
    }
}
