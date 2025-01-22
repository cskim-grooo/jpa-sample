package grooo.jpa_sample.api.partner.domain;

import grooo.jpa_sample.common.model.BasePartnerI18n;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "PartnerI18n")
@Getter
@NoArgsConstructor
@SuperBuilder
@Table(name = "partner_i18n", schema = "jpa")
public class PartnerI18n extends BasePartnerI18n {
}
