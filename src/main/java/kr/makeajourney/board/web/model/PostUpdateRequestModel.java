package kr.makeajourney.board.web.model;

import kr.makeajourney.board.dto.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostUpdateRequestModel {
    private String title;
    private String content;

    public PostDto toDto() {
        return PostDto.builder()
            .title(title)
            .content(content)
            .build();
    }
}
