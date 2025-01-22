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
public class PartnerLanguageId implements Serializable {
    @Column(name = "partner_id")
    private Long partnerId;

    @Column(name = "language_id")
    private Long languageId;
}
