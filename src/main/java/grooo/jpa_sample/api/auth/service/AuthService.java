package grooo.jpa_sample.api.auth.service;

import grooo.jpa_sample.api.auth.domain.Worker;
import grooo.jpa_sample.api.auth.domain.WorkerLoginLog;
import grooo.jpa_sample.api.auth.domain.WorkerRole;
import grooo.jpa_sample.api.auth.dto.LoginRequest;
import grooo.jpa_sample.api.auth.dto.SignupRequest;
import grooo.jpa_sample.api.auth.repository.WorkerLoginLogRepository;
import grooo.jpa_sample.api.auth.repository.WorkerRepository;
import grooo.jpa_sample.api.auth.repository.WorkerRoleRepository;
import grooo.jpa_sample.common.model.composite_key.WorkerRoleId;
import grooo.jpa_sample.common.service.PasswordEncoderService;
import grooo.jpa_sample.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoderService passwordEncoderService;
    private final WorkerRepository workerRepository;
    private final WorkerLoginLogRepository workerLoginLogRepository;
    private final WorkerRoleRepository workerRoleRepository;
    private final JwtUtil jwtUtil;
    private final HttpServletRequest httpServletRequest;

    public String login(LoginRequest request) {
        Worker worker = workerRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        if ( ! passwordEncoderService.checkPassword(request.getPassword(), worker.getPassword() ) ) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        WorkerLoginLog loginLog = WorkerLoginLog.builder()
                .worker(worker)
                .workerIp(getClientIp())
                .build();
        workerLoginLogRepository.save(loginLog);

        return jwtUtil.createToken(worker.getId(), worker.getLoginId());
    }

    @Transactional
    public void signup(SignupRequest request) {
        if (workerRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 LoginID 입니다.");
        }

        Worker worker = toDomainWorker(request);
        worker.createValidate(request.getPassword());
        workerRepository.save(worker);

        List<WorkerRole> workerRoles = toDomainWorkerRoles(request.getRoleIds(), worker.getId());
        workerRoleRepository.saveAll(workerRoles);
    }

    private String getClientIp() {
        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }

    private Worker toDomainWorker(SignupRequest request) {
        return Worker.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoderService.encodePassword(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .expiryDate(request.getExpiryDate())
                .build();
    }

    private List<WorkerRole> toDomainWorkerRoles(List<Long> roleIds, Long workerId) {
        return roleIds.stream().<WorkerRole>map(roleId -> WorkerRole.builder()
                        .id(WorkerRoleId.builder()
                                .workerId(workerId)
                                .roleId(roleId)
                                .build())
                        .creatorId(workerId)
                        .build())
                .toList();
    }
}
