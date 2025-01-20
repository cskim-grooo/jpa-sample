package grooo.jpa_sample.api.auth.repository;

import grooo.jpa_sample.api.auth.domain.WorkerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRoleRepository extends JpaRepository<WorkerRole, Long> {}
