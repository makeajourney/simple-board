package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResponse {

    private final Long id;
    private final String author;
    private final String title;
    private final String content;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    private final List<CommentResponse> comments;

    public PostDetailResponse(Post entity, List<Comment> comments) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDatetime = entity.getCreatedDatetime();
        this.modifiedDatetime = entity.getModifiedDatetime();
        this.comments = comments.stream()
            .map(CommentResponse::new)
            .collect(Collectors.toList());
    }
}
