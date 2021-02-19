package kr.makeajourney.board.domain.post;

import kr.makeajourney.board.domain.BaseTimeEntity;
import kr.makeajourney.board.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Entity
public class Subcomment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String content;

    @ManyToOne
    private Comment comment;

    @Builder
    public Subcomment(User user, String content, Comment comment) {
        this.user = user;
        this.content = content;
        this.comment = comment;
    }

    public void update(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return this.user.getName();
    }
}
