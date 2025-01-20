package grooo.jpa_sample.common.model;

import grooo.jpa_sample.common.model.composite_key.WorkerRoleId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@SuperBuilder
@Getter
@NoArgsConstructor
public abstract class BaseWorkerRole {
    @EmbeddedId
    private WorkerRoleId id; // 복합 키

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }
}
