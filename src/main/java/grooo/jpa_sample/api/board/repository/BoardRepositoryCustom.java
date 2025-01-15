package grooo.jpa_sample.api.board.repository;

import grooo.jpa_sample.api.board.domain.Board;

import java.util.List;

public interface BoardRepositoryCustom {
    void softDelete(Long boardId, Long updaterId);
    List<Board> findActiveBoards();
}
