package grooo.jpa_sample.api.board.domain;

import grooo.jpa_sample.common.model.BaseWorker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "BoardWorker")
@Getter
@NoArgsConstructor
@Table(name = "worker")
public class Worker extends BaseWorker {
}
