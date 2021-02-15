package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostSaveRequest {

    private String author;
    private String title;
    private String content;

    public Post toEntity() {
        return Post.builder()
                .author(author)
                .content(content)
                .title(title)
                .build();
    }
}
