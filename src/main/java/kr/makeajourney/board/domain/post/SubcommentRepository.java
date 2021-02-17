package kr.makeajourney.board.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcommentRepository extends JpaRepository<Subcomment, Long> {
}
