package grooo.jpa_sample.api.auth.service;

import grooo.jpa_sample.api.auth.domain.Worker;
import grooo.jpa_sample.api.auth.domain.WorkerLoginLog;
import grooo.jpa_sample.api.auth.dto.LoginRequest;
import grooo.jpa_sample.api.auth.dto.SignupRequest;
import grooo.jpa_sample.api.auth.repository.WorkerLoginLogRepository;
import grooo.jpa_sample.api.auth.repository.WorkerRepository;
import grooo.jpa_sample.common.service.PasswordEncoderService;
import grooo.jpa_sample.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoderService passwordEncoderService;
    private final WorkerRepository workerRepository;
    private final WorkerLoginLogRepository workerLoginLogRepository;
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

    public void signup(SignupRequest request) {
        if (workerRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 LoginID 입니다.");
        }

        Worker worker = Worker.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoderService.encodePassword(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .expiryDate(request.getExpiryDate())
                .build();

        worker.createValidate(request.getPassword());
        workerRepository.save(worker);
    }

    private String getClientIp() {
        String ip = httpServletRequest.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }
}
