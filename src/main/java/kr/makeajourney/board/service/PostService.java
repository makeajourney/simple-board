package kr.makeajourney.board.service;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.CommentRepository;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.post.PostRepository;
import kr.makeajourney.board.domain.user.User;
import kr.makeajourney.board.web.dto.CommentResponse;
import kr.makeajourney.board.web.dto.CommentSaveRequest;
import kr.makeajourney.board.web.dto.CommentUpdateRequest;
import kr.makeajourney.board.web.dto.PostDetailResponse;
import kr.makeajourney.board.web.dto.PostSaveRequest;
import kr.makeajourney.board.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long save(PostSaveRequest request, User author) {

        return postRepository.save(request.toEntity(author)).getId();
    }

    @Transactional
    public Long update(Long postId, PostUpdateRequest request, User user) throws NoSuchElementException {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        validateUser(post.getUser(), user);

        post.update(request.getTitle(), request.getContent());

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        List<Comment> comments = commentRepository.findAllByPost(post);

        List<CommentResponse> commentResponseList = comments.stream()
            .filter(c -> c.getParent() == null)
            .map(CommentResponse::new)
            .collect(Collectors.toList());

        return new PostDetailResponse(post, commentResponseList);
    }

    @Transactional
    public void delete(Long postId, User user) throws NoSuchElementException {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        validateUser(post.getUser(), user);

        postRepository.delete(post);
    }

    @Transactional
    public Long saveComment(Long postId, CommentSaveRequest request, User user) {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        Comment comment = request.toEntity(post, user);
        post.addComment(comment);

        return post.getId();
    }

    @Transactional
    public Long updateComment(Long postId, Long commentId, CommentUpdateRequest request, User user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        comment.update(request.getContent());

        return postId;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAllPosts(pageable);
    }

    @Transactional
    public Long saveSubcomment(Long postId, Long commentId, CommentSaveRequest request, User user) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        Post post = comment.getPost();
        if (!post.getId().equals(postId) || !comment.getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        comment.addChildren(request.toEntity(post, comment, user));

        return postId;
    }

    @Transactional
    public Long updateSubcomment(Long postId, Long commentId, Long subcommentId, CommentUpdateRequest request, User user) {
        Comment comment = commentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getParent().getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        comment.update(request.getContent());

        return postId;
    }

    public void deleteSubcomment(Long postId, Long commentId, Long subcommentId, User user) {
        Comment comment = commentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        Comment parent = comment.getParent();
        if (!parent.getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        validateUser(comment.getUser(), user);

        commentRepository.delete(comment);
    }

    private void validateUser(User postOwner, User requestUser) {
        if (!postOwner.getEmail().equals(requestUser.getEmail())) {
            throw new BadCredentialsException("invalid user");
        }
    }
}
