package grooo.jpa_sample.api.board.service;

import grooo.jpa_sample.api.board.domain.Board;
import grooo.jpa_sample.api.board.dto.RequestBoard;
import grooo.jpa_sample.api.board.dto.ResponseBoard;
import grooo.jpa_sample.api.board.repository.BoardRepository;
import grooo.jpa_sample.common.dto.PaginatedResponse;
import grooo.jpa_sample.common.exception.CustomException;
import grooo.jpa_sample.common.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public ResponseBoard createBoard(Long workerId, RequestBoard request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .creatorId(workerId)
                .lastUpdatorId(workerId)
                .build();

        board.createValidate();
        Board savedBoard = boardRepository.save(board);
        Board boardWithWorkers = boardRepository.findByIdWithWorkers(savedBoard.getId());

        return ResponseBoard.build(boardWithWorkers);
    }

    public PaginatedResponse<ResponseBoard> findAllBoards(int offset, int max) {
        Pageable pageable = PageRequest.of(offset, max, Sort.by(Sort.Direction.DESC, "dateCreated"));
        Page<ResponseBoard> boardPage = boardRepository.findAllWithWorkers(pageable).map(ResponseBoard::build);

        return PaginatedResponse.<ResponseBoard>builder()
                .data(boardPage.getContent())
                .max(boardPage.getSize())
                .offset(boardPage.getNumber())
                .total(boardPage.getTotalElements()).build();
    }

    @Transactional
    public ResponseBoard updateBoard(Long boardId, Long workerId, RequestBoard request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECORD));
        board.updateBoard(request.getTitle(), request.getContent(), workerId);
        return ResponseBoard.build(board);
    }

    public void deleteBoard(Long boardId, Long workerId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECORD));

        boardRepository.softDelete(boardId, workerId);
    }
}
