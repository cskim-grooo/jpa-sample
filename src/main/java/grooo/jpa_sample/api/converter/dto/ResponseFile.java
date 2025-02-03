package grooo.jpa_sample.api.converter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseFile {
    private String fileName;
    private byte[] bytes;
}
