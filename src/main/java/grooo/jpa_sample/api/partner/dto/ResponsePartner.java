package grooo.jpa_sample.api.partner.dto;

import grooo.jpa_sample.api.partner.domain.Partner;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePartner {
    private Long id;
    private String faxNo;
    private String dateCreated;
    private ResponseWorker creator;
    private ResponseWorker lastUpdator;
    private ResponsePartnerI18n responsePartnerI18n;

    public static ResponsePartner build(Partner partner) {
        return new ResponsePartner(
                partner.getId(),
                partner.getFaxNo(),
                partner.getDateCreated().toString(),
                ResponseWorker.build(partner.getCreator()),
                ResponseWorker.build(partner.getLastUpdator()),
                ResponsePartnerI18n.buildByLocaleInfo(partner.getI18nList())
        );
    }
}

