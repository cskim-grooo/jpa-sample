package grooo.jpa_sample.api.auth.domain;

import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import grooo.jpa_sample.common.model.BaseWorker;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

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
            throw new CustomException(ErrorCode.PASSWORD_EQUALS_LOGIN_ID);
        }
        if (getEmail() == null || !getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new CustomException(ErrorCode.WRONG_DATA_FIELD, "email");
        }
        if (getExpiryDate() == null || getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRY_DATE_BEFORE_NOW);
        }
    }
}
