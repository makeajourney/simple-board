package kr.makeajourney.board.web.model;

import kr.makeajourney.board.dto.PostDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostDetailResponseModel {

    private final Long id;
    private final String author;
    private final String title;
    private final String content;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    private final List<CommentResponseModel> comments;

    public PostDetailResponseModel(PostDto postDto) {
        this.id = postDto.getId();
        this.author = postDto.getUsername();
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.createdDatetime = postDto.getCreatedDatetime();
        this.modifiedDatetime = postDto.getModifiedDatetime();
        this.comments = postDto.getComments().stream().map(CommentResponseModel::new).collect(Collectors.toList());
    }
}
