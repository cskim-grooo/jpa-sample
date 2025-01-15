package grooo.jpa_sample.api.auth.repository;

import grooo.jpa_sample.api.auth.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
