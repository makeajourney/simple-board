package kr.makeajourney.board.web.model;

import kr.makeajourney.board.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentSaveRequestModel {

    private String content;

    public CommentDto toDto() {
        return CommentDto.builder()
            .content(content)
            .build();
    }
}
