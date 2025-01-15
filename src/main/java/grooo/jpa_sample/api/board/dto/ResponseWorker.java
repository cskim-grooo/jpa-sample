package grooo.jpa_sample.api.board.dto;

import grooo.jpa_sample.api.board.domain.Worker;
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
