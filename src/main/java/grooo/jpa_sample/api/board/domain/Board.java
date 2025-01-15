package grooo.jpa_sample.api.board.domain;

import grooo.jpa_sample.common.model.BaseBoard;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성자 정보가 없습니다.");
        }
    }

    public void updateBoard(String title, String content, Long lastUpdatorId) {
        this.setTitle(title);
        this.setContent(content);
        this.setLastUpdatorId(lastUpdatorId);
    }
}
