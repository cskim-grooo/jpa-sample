package grooo.jpa_sample;

import com.querydsl.jpa.impl.JPAQueryFactory;
import grooo.jpa_sample.common.GlobalValues;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@EnableJpaAuditing
@SpringBootApplication
public class Main {

	@PersistenceContext
	private EntityManager entityManager;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	CommandLineRunner initGlobalValues() {
		return args -> {
			String sql = "SELECT lcid, id FROM language";
			@SuppressWarnings("unchecked")
			List<Object[]> results = entityManager.createNativeQuery(sql).getResultList();

			Map<String, Long> languageData = results.stream()
					.collect(Collectors.toMap(
							result -> (String) result[0],
							result -> ((Number) result[1]).longValue()
					));
			GlobalValues.init(languageData);
		};
	}

	// TODO QueryDSL 사용시 빈에 등록해야함.
//	@Bean
//	JPAQueryFactory jpaQueryFactory(EntityManager em) {
//		return new JPAQueryFactory(em);
//	}
}
