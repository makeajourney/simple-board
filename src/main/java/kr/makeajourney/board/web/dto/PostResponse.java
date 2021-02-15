package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final String author;
    private final String title;
    private final String content;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime updatedDatetime;

    public PostResponse(Post entity) {
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDatetime = entity.getCreatedDatetime();
        this.updatedDatetime = entity.getModifiedDatetime();
    }
}
