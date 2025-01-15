package grooo.jpa_sample.api.board.repository;

import grooo.jpa_sample.api.board.domain.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

//    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void softDelete(Long boardId, Long updatorId) {
        String sql = "UPDATE board SET is_deleted = true, last_updator_id = ?1 WHERE id = ?2 AND is_deleted = false";

        entityManager.createNativeQuery(sql)
                .setParameter(1, updatorId)
                .setParameter(2, boardId)
                .executeUpdate();
    }

    @Override
    public List<Board> findActiveBoards() {
        return null;
//        QBoard board = QBoard.board;  // QBoard 객체 사용
//
//        return queryFactory
//                .selectFrom(board)
//                .where(board.isDeleted.eq(false))  // isDeleted가 false인 게시글 조회
//                .fetch();
    }
}
