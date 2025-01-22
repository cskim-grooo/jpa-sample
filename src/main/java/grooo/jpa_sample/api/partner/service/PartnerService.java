package grooo.jpa_sample.api.partner.service;

import grooo.jpa_sample.api.partner.domain.Partner;
import grooo.jpa_sample.api.partner.dto.ResponsePartner;
import grooo.jpa_sample.api.partner.repository.PartnerRepository;
import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;

    @Transactional
    public ResponsePartner findById(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECORD));
        return ResponsePartner.build(partner);
    }
}
