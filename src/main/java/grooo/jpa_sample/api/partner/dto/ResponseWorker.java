package grooo.jpa_sample.api.partner.dto;

import grooo.jpa_sample.api.partner.domain.Worker;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseWorker {
    private Long id;
    private String name;

    public static ResponseWorker build(Worker worker) {
        return new ResponseWorker(worker.getId(), worker.getName());
    }
}
