package kr.makeajourney.board.web.dto;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.post.Subcomment;
import kr.makeajourney.board.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CommentSaveRequest {

    private String content;

    public Comment toEntity(Post post, User user) {
        return Comment.builder()
            .user(user)
            .content(content)
            .post(post)
            .build();
    }

    public Subcomment toEntity(Comment comment, User user) {
        return Subcomment.builder()
            .user(user)
            .content(content)
            .comment(comment)
            .build();
    }
}
