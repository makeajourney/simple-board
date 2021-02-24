package kr.makeajourney.board.domain.post;

import kr.makeajourney.board.domain.user.Role;
import kr.makeajourney.board.domain.user.User;
import kr.makeajourney.board.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        User user = User.builder()
            .email("test@carrotins.com")
            .password("{noop}1234")
            .role(Role.USER)
            .build();

        userRepository.save(user);

        postRepository.save(Post.builder()
            .title(title)
            .content(content)
            .user(user)
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
        User user = User.builder()
            .email("test@carrotins.com")
            .password("{noop}1234")
            .role(Role.USER)
            .build();

        userRepository.save(user);

        LocalDateTime now = LocalDateTime.now();
        postRepository.save(Post.builder()
            .title("title")
            .content("content")
            .user(user)
            .build());

        //when
        List<Post> postList = postRepository.findAll();

        //then
        Post post = postList.get(0);

        System.out.println(">>>>>>>> createdDate=" + post.getCreatedDatetime() + ", modifiedDate=" + post.getModifiedDatetime());

        Assertions.assertTrue(post.getCreatedDatetime().isAfter(now));
        Assertions.assertTrue(post.getModifiedDatetime().isAfter(now));
    }
}
