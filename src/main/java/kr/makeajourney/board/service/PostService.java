package kr.makeajourney.board.service;

import kr.makeajourney.board.domain.post.Comment;
import kr.makeajourney.board.domain.post.CommentRepository;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.post.PostRepository;
import kr.makeajourney.board.domain.post.Subcomment;
import kr.makeajourney.board.domain.post.SubcommentRepository;
import kr.makeajourney.board.domain.user.User;
import kr.makeajourney.board.web.dto.CommentResponse;
import kr.makeajourney.board.web.dto.CommentSaveRequest;
import kr.makeajourney.board.web.dto.CommentUpdateRequest;
import kr.makeajourney.board.web.dto.PostDetailResponse;
import kr.makeajourney.board.web.dto.PostSaveRequest;
import kr.makeajourney.board.web.dto.PostUpdateRequest;
import kr.makeajourney.board.web.dto.SubcommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final SubcommentRepository subcommentRepository;

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
        List<Subcomment> subcomments = subcommentRepository.findAllByCommentIn(comments);

        List<CommentResponse> commentResponseList = aggregateCommentAndConvertToDto(comments, subcomments);

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

        if (!comment.getPost().getId().equals(postId) || !comment.getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        comment.addSubcomment(request.toEntity(comment, user));

        return postId;
    }

    @Transactional
    public Long updateSubcomment(Long postId, Long commentId, Long subcommentId, CommentUpdateRequest request, User user) {
        Subcomment subcomment = subcommentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        if (!subcomment.getComment().getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        validateUser(subcomment.getUser(), user);

        subcomment.update(request.getContent());

        return postId;
    }

    public void deleteSubcomment(Long postId, Long commentId, Long subcommentId, User user) {
        Subcomment subcomment = subcommentRepository.findById(subcommentId)
            .orElseThrow(NoSuchElementException::new);

        if (!subcomment.getComment().getId().equals(commentId)) {
            throw new NoSuchElementException();
        }

        validateUser(subcomment.getUser(), user);

        subcommentRepository.delete(subcomment);
    }

    private void validateUser(User postOwner, User requestUser) {
        if (!postOwner.getEmail().equals(requestUser.getEmail())) {
            throw new BadCredentialsException("invalid user");
        }
    }

    private List<CommentResponse> aggregateCommentAndConvertToDto(List<Comment> comments, List<Subcomment> subcomments) {
        Map<Long, List<Subcomment>> subcommentsMap = subcomments.stream()
            .collect(groupingBy(s -> s.getComment().getId()));

        List<CommentResponse> commentResponseList = new ArrayList<>();

        for (Comment comment : comments) {
            List<Subcomment> subcommentList = subcommentsMap.get(comment.getId());

            List<SubcommentResponse> subcommentResponseList = Objects.requireNonNullElseGet(subcommentList, ArrayList<Subcomment>::new).stream()
                .map(SubcommentResponse::new)
                .collect(Collectors.toList());

            commentResponseList.add(new CommentResponse(comment, subcommentResponseList));
        }
        return commentResponseList;
    }
}
