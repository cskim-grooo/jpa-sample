package grooo.jpa_sample.api.converter.dto;

import lombok.Data;

@Data
public class ResponseImport {
    private Long id;
    private String name;
    private String code;
    private String lcid;
}
