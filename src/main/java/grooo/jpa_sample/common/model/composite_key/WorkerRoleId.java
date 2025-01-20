package grooo.jpa_sample.common.model.composite_key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Embeddable
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerRoleId implements Serializable {
    @Column(name = "worker_id")
    private Long workerId;

    @Column(name = "role_id")
    private Long roleId;
}
