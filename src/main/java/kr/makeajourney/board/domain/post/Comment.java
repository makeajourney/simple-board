package kr.makeajourney.board.domain.post;

import kr.makeajourney.board.domain.BaseTimeEntity;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String content;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Subcomment> subcomments;

    @Builder
    public Comment(User user, String content, Post post) {
        this.user = user;
        this.content = content;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }

    public void addSubcomment(Subcomment subcomment) {
        this.subcomments.add(subcomment);
    }

    public String getAuthor() {
        return this.user.getName();
    }
}
