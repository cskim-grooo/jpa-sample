package grooo.jpa_sample.api.converter.repository;

import grooo.jpa_sample.api.converter.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}