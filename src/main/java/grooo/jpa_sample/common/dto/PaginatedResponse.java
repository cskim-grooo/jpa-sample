package grooo.jpa_sample.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;
    private int offset;
    private int max;
    private long total;
}
