package kr.makeajourney.board.dto;

import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostDto {
    private Long id;
    private UserDto user;
    private String title;
    private String content;

    private List<CommentDto> comments;

    private LocalDateTime createdDatetime;
    private LocalDateTime modifiedDatetime;

    @Builder
    public PostDto(Long id, UserDto user, String title, String content) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public PostDto(Post entity) {
        this.id = entity.getId();
        this.user = new UserDto(entity.getUser());
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDatetime = entity.getCreatedDatetime();
        this.modifiedDatetime = entity.getModifiedDatetime();
    }

    public Post toEntity(User user) {
        return Post.builder()
            .user(user)
            .content(content)
            .title(title)
            .build();
    }

    public void setComments(List<CommentDto> commentDtoList) {
        this.comments = commentDtoList;
    }

    public String getUsername() {
        return (user != null) ? user.getName() : "Unknown";
    }
}
