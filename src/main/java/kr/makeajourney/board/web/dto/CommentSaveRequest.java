package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.post.Subcomment;
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

    public Subcomment toEntity(Comment comment) {
        return Subcomment.builder()
            .author(author)
            .content(content)
            .comment(comment)
            .build();
    }
}
