package grooo.jpa_sample.api.converter.domain;

import grooo.jpa_sample.common.model.BaseLanguage;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "ConverterLanguage")
@Getter
@NoArgsConstructor
@Table(name = "language", schema = "jpa")
public class Language extends BaseLanguage {
}
