package kr.makeajourney.board.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT distinct c FROM Comment c LEFT OUTER JOIN FETCH c.subcomments WHERE c.post = :post")
    List<Comment> findByPost(Post post);
}
