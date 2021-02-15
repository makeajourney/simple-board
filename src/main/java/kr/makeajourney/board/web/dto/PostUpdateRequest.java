package kr.makeajourney.board.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostUpdateRequest {
    private String title;
    private String content;
}
