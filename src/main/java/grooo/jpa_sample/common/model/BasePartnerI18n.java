package grooo.jpa_sample.common.model;

import grooo.jpa_sample.common.model.composite_key.PartnerLanguageId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class BasePartnerI18n {

    @EmbeddedId
    private PartnerLanguageId id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", nullable = false, length = 255)
    private String address;
}
