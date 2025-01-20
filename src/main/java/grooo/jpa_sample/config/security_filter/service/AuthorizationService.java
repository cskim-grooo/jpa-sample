package grooo.jpa_sample.config.security_filter.service;

import grooo.jpa_sample.config.security_filter.repository.RoleFunctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final RoleFunctionRepository roleFunctionRepository;

    public boolean hasPermission(Long workerId, String requestUri, String httpMethod) {
        return roleFunctionRepository.existsWorkerRole(workerId, requestUri, httpMethod);
    }
}
