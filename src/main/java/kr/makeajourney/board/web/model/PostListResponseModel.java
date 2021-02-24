package kr.makeajourney.board.web.model;

import kr.makeajourney.board.dto.PostDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseModel {

    private final Long id;
    private final String author;
    private final String title;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    public PostListResponseModel(PostDto postDto) {
        this.id = postDto.getId();
        this.author = postDto.getUsername();
        this.title = postDto.getTitle();
        this.createdDatetime = postDto.getCreatedDatetime();
        this.modifiedDatetime = postDto.getModifiedDatetime();
    }
}
