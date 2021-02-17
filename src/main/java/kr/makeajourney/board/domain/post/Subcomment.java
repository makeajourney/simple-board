package kr.makeajourney.board.domain.post;

import kr.makeajourney.board.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    @Column(length = 50)
    private String author;

    @Column
    private String content;

    @ManyToOne
    private Comment comment;

    @Builder
    public Subcomment(String author, String content, Comment comment) {
        this.author = author;
        this.content = content;
        this.comment = comment;
    }

    public void update(String content) {
        this.content = content;
    }
}
