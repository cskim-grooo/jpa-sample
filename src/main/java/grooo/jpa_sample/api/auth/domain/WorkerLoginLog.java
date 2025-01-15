package grooo.jpa_sample.api.auth.domain;

import grooo.jpa_sample.common.model.BaseWorkerLoginLog;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@Table
public class WorkerLoginLog extends BaseWorkerLoginLog {
    public void createValidate() {}
}
