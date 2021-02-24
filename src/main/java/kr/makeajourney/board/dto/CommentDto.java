package kr.makeajourney.board.dto;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class CommentDto {

    private Long id;
    private UserDto user;
    private String content;

    private LocalDateTime createdDatetime;
    private LocalDateTime modifiedDatetime;

    private List<CommentDto> subcomments;

    @Builder
    public CommentDto(Long id, UserDto user, String content, List<CommentDto> subcomments) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.subcomments = Objects.requireNonNullElseGet(subcomments, ArrayList::new);
    }

    public CommentDto(Comment entity) {
        this.id = entity.getId();
        this.user = new UserDto(entity.getUser());
        this.content = entity.getContent();
        this.createdDatetime = entity.getCreatedDatetime();
        this.modifiedDatetime = entity.getModifiedDatetime();
        this.subcomments = entity.getChildren().stream().map(CommentDto::new).collect(Collectors.toList());
    }

    public Comment toEntity(Post post, User user) {
        return Comment.builder()
            .user(user)
            .content(content)
            .post(post)
            .build();
    }

    public Comment toEntity(Post post, Comment parent, User user) {
        return Comment.builder()
            .post(post)
            .parent(parent)
            .user(user)
            .content(content)
            .build();
    }

    public String getUsername() {
        return (user != null) ? user.getName() : "Unknown";
    }
}
