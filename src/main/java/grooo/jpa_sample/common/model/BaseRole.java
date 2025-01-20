package grooo.jpa_sample.common.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Builder.Default
    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean isActive = true;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId;

    @Column(updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "last_updator_id", nullable = false)
    private Long lastUpdatorId;

    private LocalDateTime lastUpdated;
}
