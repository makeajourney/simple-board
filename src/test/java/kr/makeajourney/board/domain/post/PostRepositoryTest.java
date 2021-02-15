package kr.makeajourney.board.domain.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postRepository.save(Post.builder()
            .title(title)
            .content(content)
            .author("makeajourney@gmail.com")
            .build());

        // when
        List<Post> postList = postRepository.findAll();

        // then
        Post post = postList.get(0);
        Assertions.assertEquals(post.getTitle(), title);
        Assertions.assertEquals(post.getContent(), content);
    }

    @Test
    public void BaseTimeEntity_auditing_적용() {
        //given
        LocalDateTime now = LocalDateTime.now();
        postRepository.save(Post.builder()
            .title("title")
            .content("content")
            .author("author")
            .build());

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);

        System.out.println(">>>>>>>> createdDate=" + post.getCreatedDate() + ", modifiedDate=" + post.getModifiedDate());

        Assertions.assertTrue(post.getCreatedDate().isAfter(now));
        Assertions.assertTrue(post.getModifiedDate().isAfter(now));
    }
}
