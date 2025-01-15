package grooo.jpa_sample.api.board;

import grooo.jpa_sample.api.board.dto.RequestBoard;
import grooo.jpa_sample.api.board.dto.ResponseBoard;
import grooo.jpa_sample.api.board.service.BoardService;
import grooo.jpa_sample.common.dto.PaginatedResponse;
import grooo.jpa_sample.common.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/board", "/board/"})
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<ResponseBoard> createBoard(HttpServletRequest request, @RequestBody RequestBoard requestBody) {
        Long workerId = AuthUtil.getAuthenticatedWorkerId(request);

        requestBody.basicValidate();
        ResponseBoard response = boardService.createBoard(workerId, requestBody);
        return ResponseEntity.ok(response);
    }

    @GetMapping({"/list", "/list/"})
    public ResponseEntity<PaginatedResponse<ResponseBoard>> getBoardList(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int max
    ) {
        return ResponseEntity.ok(boardService.findAllBoards(offset, max));
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<ResponseBoard> updateBoard(
            HttpServletRequest request,
            @PathVariable Long boardId,
            @RequestBody RequestBoard requestBody
    ) {
        Long workerId = AuthUtil.getAuthenticatedWorkerId(request);
        requestBody.basicValidate();
        ResponseBoard response = boardService.updateBoard(boardId, workerId, requestBody);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(HttpServletRequest request, @PathVariable Long boardId) {
        Long workerId = AuthUtil.getAuthenticatedWorkerId(request);
        boardService.deleteBoard(boardId, workerId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}
