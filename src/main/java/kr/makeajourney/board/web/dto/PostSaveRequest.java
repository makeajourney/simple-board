package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostSaveRequest {

    private String title;
    private String content;

    public Post toEntity(User user) {
        return Post.builder()
            .user(user)
            .content(content)
            .title(title)
            .build();
    }
}
