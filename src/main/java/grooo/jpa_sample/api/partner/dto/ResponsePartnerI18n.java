package grooo.jpa_sample.api.partner.dto;

import grooo.jpa_sample.api.partner.domain.PartnerI18n;
import grooo.jpa_sample.common.GlobalValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static grooo.jpa_sample.common.util.I18nUtil.filterByLanguageId;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePartnerI18n {
    private Long partnerId;
    private Long languageId;
    private String name;
    private String address;

    public static ResponsePartnerI18n buildByLocaleInfo(List<PartnerI18n> i18nList) {
        if (i18nList == null || i18nList.isEmpty()) {
            return null;
        }

        Long languageId = GlobalValues.getLanguageId();
        Long defaultLanguageId = GlobalValues.getDefaultLanguageId();

        PartnerI18n localeI18n = filterByLanguageId(i18nList, languageId, defaultLanguageId);

        return convertToResponse(localeI18n);
    }

    private static ResponsePartnerI18n convertToResponse(PartnerI18n i18n) {
        if (i18n == null) {
            return null;
        }
        return new ResponsePartnerI18n(
                i18n.getId().getPartnerId(),
                i18n.getId().getLanguageId(),
                i18n.getName(),
                i18n.getAddress()
        );
    }
}
