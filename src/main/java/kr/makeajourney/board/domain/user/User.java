package kr.makeajourney.board.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.post.Subcomment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Subcomment> subcomments = new ArrayList<>();

    @Builder
    public User(String email, String password, Role role, List<Post> posts) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.posts = posts;
    }

    public String getName() {
        return this.email;
    }
}
