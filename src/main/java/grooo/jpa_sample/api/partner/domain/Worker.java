package grooo.jpa_sample.api.partner.domain;

import grooo.jpa_sample.common.model.BaseWorker;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "PartnerWorker")
@Getter
@NoArgsConstructor
@Table(name = "worker")
public class Worker extends BaseWorker {
}
