package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponse {

    private final Long id;
    private final String author;
    private final String title;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    public PostListResponse(Post entity) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.title = entity.getTitle();
        this.createdDatetime = entity.getCreatedDatetime();
        this.modifiedDatetime = entity.getModifiedDatetime();
    }
}
