package grooo.jpa_sample.api.board.domain;

import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import grooo.jpa_sample.common.model.BaseBoard;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Entity(name = "Board")
@Getter
@NoArgsConstructor
@SuperBuilder
@Where(clause = "is_deleted = false")
@Table(name = "board", schema = "jpa")
public class Board extends BaseBoard {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    private Worker creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updator_id", insertable = false, updatable = false)
    private Worker lastUpdator;

    public void createValidate() {
        if (getCreatorId() == null || getLastUpdatorId() == null) {
            throw new CustomException(ErrorCode.AUTH_TOKEN_UNAVAILABLE);
        }
    }

    public void updateBoard(String title, String content, Long lastUpdatorId) {
        this.setTitle(title);
        this.setContent(content);
        this.setLastUpdatorId(lastUpdatorId);
    }
}
