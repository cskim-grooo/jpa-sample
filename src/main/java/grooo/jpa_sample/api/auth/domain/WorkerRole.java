package grooo.jpa_sample.api.auth.domain;

import grooo.jpa_sample.common.model.BaseWorkerRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity(name = "AuthWorkerRole")
@Getter
@SuperBuilder
@NoArgsConstructor
@Table(name = "worker_role")
public class WorkerRole extends BaseWorkerRole {
}
