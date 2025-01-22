package grooo.jpa_sample.api.partner;

import grooo.jpa_sample.api.partner.dto.ResponsePartner;
import grooo.jpa_sample.api.partner.service.PartnerService;
import grooo.jpa_sample.common.dto.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/partner", "/partner/"})
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping("/{partnerId}")
    public ResponseEntity<ResponsePartner> getPartner(@PathVariable Long partnerId) {
        ResponsePartner response = partnerService.findById(partnerId);
        return ResponseEntity.ok(response);
    }
}
