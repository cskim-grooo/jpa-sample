package grooo.jpa_sample.api.partner.domain;

import grooo.jpa_sample.common.model.BasePartner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity(name = "Partner")
@Getter
@NoArgsConstructor
@SuperBuilder
@Table(name = "partner", schema = "jpa")
public class Partner extends BasePartner {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    private Worker creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updator_id", insertable = false, updatable = false)
    private Worker lastUpdator;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private List<PartnerI18n> i18nList;
}
