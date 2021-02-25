package kr.makeajourney.board.service;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.CommentRepository;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.post.PostRepository;
import kr.makeajourney.board.domain.user.User;
import kr.makeajourney.board.dto.CommentDto;
import kr.makeajourney.board.dto.PostDto;
import kr.makeajourney.board.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static kr.makeajourney.board.service.UserService.validateUser;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Transactional
    public Long save(PostDto postDto, UserDto tokenUser) {
        User dbUser = userService.findDbUserFromTokenUser(tokenUser);
        return postRepository.save(postDto.toEntity(dbUser)).getId();
    }

    @Transactional
    public Long update(Long postId, PostDto postDto, UserDto tokenUser) {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        validateUser(post.getUser(), tokenUser);

        post.update(postDto.getTitle(), postDto.getContent());

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        List<Comment> comments = commentRepository.findAllByPost(post);
        List<CommentDto> commentDtoList = comments.stream()
            .filter(c -> c.getParent() == null)
            .map(CommentDto::new)
            .collect(Collectors.toList());

        PostDto postDto = new PostDto(post);
        postDto.setComments(commentDtoList);

        return postDto;
    }

    @Transactional
    public void delete(Long postId, UserDto user) throws NoSuchElementException {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        validateUser(post.getUser(), user);

        postRepository.delete(post);
    }

    @Transactional
    public Long saveComment(Long postId, CommentDto commentDto, UserDto tokenUser) {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        User dbUser = userService.findDbUserFromTokenUser(tokenUser);
        Comment comment = commentDto.toEntity(post, dbUser);
        post.addComment(comment);

        return post.getId();
    }

    @Transactional
    public Long updateComment(Long postId, Long commentId, CommentDto commentDto, UserDto user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        comment.update(commentDto.getContent());

        return postId;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, UserDto user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> findAll(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllPosts(pageable);

        List<PostDto> postDtoList = postPage.getContent().stream().map(PostDto::new).collect(Collectors.toList());

        return new PageImpl<>(postDtoList, postPage.getPageable(), postPage.getTotalElements());
    }

    @Transactional
    public Long saveSubcomment(Long postId, Long commentId, CommentDto commentDto, UserDto tokenUser) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        Post post = comment.getPost();
        if (!post.getId().equals(postId) || !comment.getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        User dbUser = userService.findDbUserFromTokenUser(tokenUser);
        comment.addChildren(commentDto.toEntity(post, comment, dbUser));

        return postId;
    }

    @Transactional
    public Long updateSubcomment(Long postId, Long commentId, Long subcommentId, CommentDto commentDto, UserDto user) {
        Comment comment = commentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getParent().getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        comment.update(commentDto.getContent());

        return postId;
    }

    public void deleteSubcomment(Long postId, Long commentId, Long subcommentId, UserDto user) {
        Comment comment = commentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        Comment parent = comment.getParent();
        if (!parent.getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        commentRepository.delete(comment);
    }
}
