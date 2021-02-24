package kr.makeajourney.board.web.model;

import kr.makeajourney.board.dto.CommentDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseModel {
    private final Long id;
    private final String author;
    private final String content;

    private final LocalDateTime createdDatetime;
    private final LocalDateTime modifiedDatetime;

    private final List<CommentResponseModel> subcomments;

    public CommentResponseModel(CommentDto commentDto) {
        this.id = commentDto.getId();
        this.author = commentDto.getUsername();
        this.content = commentDto.getContent();
        this.createdDatetime = commentDto.getCreatedDatetime();
        this.modifiedDatetime = commentDto.getModifiedDatetime();
        this.subcomments = commentDto.getSubcomments().stream()
            .map(CommentResponseModel::new)
            .collect(Collectors.toList());
    }
}
