package grooo.jpa_sample.api.board.repository;

import grooo.jpa_sample.api.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    @Query("SELECT b FROM Board b JOIN FETCH b.creator JOIN FETCH b.lastUpdator WHERE b.id = :id")
    Board findByIdWithWorkers(@Param("id") Long id);

    @Query("SELECT b FROM Board b JOIN FETCH b.creator JOIN FETCH b.lastUpdator")
    Page<Board> findAllWithWorkers(Pageable pageable);
}
