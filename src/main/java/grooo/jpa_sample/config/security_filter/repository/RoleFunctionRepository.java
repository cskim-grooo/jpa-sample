package grooo.jpa_sample.config.security_filter.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoleFunctionRepository {
    private final EntityManager em;

    public boolean existsWorkerRole(Long workerId, String url, String httpMethod) {
        String sql = """
            SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END
              FROM role_function rf
                JOIN role r ON rf.role_id = r.id
                JOIN worker_role wr ON wr.role_id = r.id
                JOIN function f ON rf.function_id = f.id
            WHERE wr.worker_id = ?1
              AND INSTR(?2, f.url) > 0
              AND f.http_method = ?3
        """;

        Object result = em.createNativeQuery(sql)
                .setParameter(1, workerId)
                .setParameter(2, url)
                .setParameter(3, httpMethod)
                .getSingleResult();
        return result != null && ((Number) result).intValue() > 0;
    }
}
