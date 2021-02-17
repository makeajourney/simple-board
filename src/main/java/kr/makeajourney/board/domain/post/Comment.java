package kr.makeajourney.board.domain.post;

import kr.makeajourney.board.domain.BaseTimeEntity;
import kr.makeajourney.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(length = 50)
    private String author;

    @Column
    private String content;

    @ManyToOne
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Subcomment> subcomments;

    @Builder
    public Comment(String author, String content, Post post) {
        this.author = author;
        this.content = content;
        this.post = post;
    }

    public void update(String content) {
        this.content = content;
    }

    public void addSubcomment(Subcomment subcomment) {
        this.subcomments.add(subcomment);
    }
}
