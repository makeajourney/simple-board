package kr.makeajourney.board.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM Post p LEFT OUTER JOIN FETCH p.user", countQuery = "SELECT count(*) FROM Post")
    Page<Post> findAllPosts(Pageable pageable);
}
