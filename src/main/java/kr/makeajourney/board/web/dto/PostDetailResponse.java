package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostDetailResponse {

    private final Long id;
    private final String author;
    private final String title;
    private final String content;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    private final List<CommentResponse> comments;

    public PostDetailResponse(Post entity, List<CommentResponse> commentResponseList) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDatetime = entity.getCreatedDatetime();
        this.modifiedDatetime = entity.getModifiedDatetime();
        this.comments = commentResponseList;
    }
}
