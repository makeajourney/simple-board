package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class CommentResponse {
    private final Long id;
    private final String author;
    private final String content;
    private final List<SubcommentResponse> subcomments;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    public CommentResponse(Comment entity, List<SubcommentResponse> subcommentResponseList) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.createdDatetime = entity.getCreatedDatetime();
        this.modifiedDatetime = entity.getModifiedDatetime();
        this.subcomments = Objects.requireNonNullElseGet(subcommentResponseList, ArrayList::new);
    }
}
