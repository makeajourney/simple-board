package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponse {
    private final Long id;
    private final String author;
    private final String content;
    private final List<CommentResponse> subcomments;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    public CommentResponse(Comment entity) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.createdDatetime = entity.getCreatedDatetime();
        this.modifiedDatetime = entity.getModifiedDatetime();
        this.subcomments = entity.getChildren().stream().map(CommentResponse::new).collect(Collectors.toList()); // todo
    }
}
