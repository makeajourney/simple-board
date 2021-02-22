package kr.makeajourney.board.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SubcommentRepository extends JpaRepository<Subcomment, Long> {
    @Query(value = "SELECT s FROM Subcomment s LEFT OUTER JOIN FETCH s.user WHERE s.comment in :comment")
    List<Subcomment> findAllByCommentIn(Collection<Comment> comment);
}
