package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentSaveRequest {

    private String author;
    private String content;

    public Comment toEntity(Post post) {
        return Comment.builder()
            .author(author)
            .content(content)
            .post(post)
            .build();
    }
}
