package kr.makeajourney.board.service;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.CommentRepository;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.post.PostRepository;
import kr.makeajourney.board.domain.post.Subcomment;
import kr.makeajourney.board.domain.post.SubcommentRepository;
import kr.makeajourney.board.web.dto.CommentSaveRequest;
import kr.makeajourney.board.web.dto.CommentUpdateRequest;
import kr.makeajourney.board.web.dto.PostDetailResponse;
import kr.makeajourney.board.web.dto.PostSaveRequest;
import kr.makeajourney.board.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SubcommentRepository subcommentRepository;

    @Transactional
    public Long save(PostSaveRequest request) {

        return postRepository.save(request.toEntity()).getId();
    }

    @Transactional
    public Long update(Long postId, PostUpdateRequest request) throws NoSuchElementException {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        post.update(request.getTitle(), request.getContent());

        return post.getId();
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findById(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);
        List<Comment> comments = commentRepository.findByPost(post);

        return new PostDetailResponse(post, comments);
    }

    @Transactional
    public void delete(Long postId) throws NoSuchElementException {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        postRepository.delete(post);
    }

    @Transactional
    public Long saveComment(Long postId, CommentSaveRequest request) {
        Post post = postRepository.findById(postId)
            .orElseThrow(NoSuchElementException::new);

        Comment comment = request.toEntity(post);
        post.addComment(comment);

        return post.getId();
    }

    @Transactional
    public Long updateComment(Long postId, Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchElementException();
        }

        comment.update(request.getContent());

        return postId;
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getPost().getId().equals(postId)) {
            throw new NoSuchElementException();
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    public Long saveSubcomment(Long postId, Long commentId, CommentSaveRequest request) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NoSuchElementException::new);

        if (!comment.getPost().getId().equals(postId) || !comment.getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        comment.addSubcomment(request.toEntity(comment));

        return postId;
    }

    @Transactional
    public Long updateSubcomment(Long postId, Long commentId, Long subcommentId, CommentUpdateRequest request) {
        Subcomment subcomment = subcommentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        if (!subcomment.getComment().getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        subcomment.update(request.getContent());

        return postId;
    }

    public void deleteSubcomment(Long postId, Long commentId, Long subcommentId) {
        Subcomment subcomment = subcommentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        if (!subcomment.getComment().getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        subcommentRepository.delete(subcomment);
    }
}
