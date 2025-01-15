package grooo.jpa_sample.api.auth.domain;

import grooo.jpa_sample.common.model.BaseWorker;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Entity(name = "AuthWorker")
@Getter
@SuperBuilder
@NoArgsConstructor
@Where(clause = "is_deleted = false")
@Table(name = "worker", schema = "jpa")
public class Worker extends BaseWorker {
    public void createValidate(String requestPassword) {
        if (getLoginId().equals(requestPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호는 로그인 ID와 같을 수 없습니다.");
        }
        if (getEmail() == null || !getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "올바른 이메일 형식이 아닙니다.");
        }
        if (getExpiryDate() == null || getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "만료일자는 현재 시간보다 이후여야 합니다.");
        }
    }
}
