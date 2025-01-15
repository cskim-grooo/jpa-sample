package grooo.jpa_sample.api.board.dto;

import grooo.jpa_sample.api.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseBoard {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private String dateCreated;
    private ResponseWorker creator;
    private ResponseWorker lastUpdator;

    public static ResponseBoard build(Board board) {
        return new ResponseBoard(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getViewCount(),
                board.getDateCreated().toString(),
                ResponseWorker.build(board.getCreator()),
                ResponseWorker.build(board.getLastUpdator())
        );
    }
}
