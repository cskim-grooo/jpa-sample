package grooo.jpa_sample.api.partner.repository;

import grooo.jpa_sample.api.partner.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long>{
}
