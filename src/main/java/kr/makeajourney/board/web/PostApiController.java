package kr.makeajourney.board.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kr.makeajourney.board.domain.post.Post;
import kr.makeajourney.board.domain.user.User;
import kr.makeajourney.board.service.PostService;
import kr.makeajourney.board.service.UserService;
import kr.makeajourney.board.web.dto.CommentSaveRequest;
import kr.makeajourney.board.web.dto.CommentUpdateRequest;
import kr.makeajourney.board.web.dto.PostListResponse;
import kr.makeajourney.board.web.dto.PostSaveRequest;
import kr.makeajourney.board.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final UserService userService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity save(@RequestBody PostSaveRequest request, @ApiIgnore @AuthenticationPrincipal User user) {
        return userService.findByEmail(user.getEmail())
            .map(u -> ResponseEntity.ok(postService.save(request, u)))
            .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/api/v1/posts")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
        @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
            value = "Number of records per page.", defaultValue = "20"),
        @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
            value = "Sorting criteria in the format: property(,asc|desc). " +
                "Default sort order is ascending. " +
                "Multiple sort criteria are supported.")
    })
    public ResponseEntity findAll(@ApiIgnore Pageable pageable) {
        Page<Post> postPage = postService.findAll(pageable);

        List<PostListResponse> postListResponseList = postPage.getContent().stream()
            .map(PostListResponse::new)
            .collect(Collectors.toList());

        PageImpl<PostListResponse> response = new PageImpl<>(postListResponseList, postPage.getPageable(), postPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/posts/{postId}")
    public ResponseEntity update(@PathVariable Long postId, @RequestBody PostUpdateRequest request, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            Long updatedPostId = postService.update(postId, request, user);

            return ResponseEntity.ok(updatedPostId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity findById(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(postService.findById(postId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    public ResponseEntity delete(@PathVariable Long postId, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            postService.delete(postId, user);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity saveComment(@PathVariable Long postId, @RequestBody CommentSaveRequest request, @ApiIgnore @AuthenticationPrincipal User user) {

            return userService.findByEmail(user.getEmail())
                .map(u -> ResponseEntity.ok(postService.saveComment(postId, request, u)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequest request, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            Long commentAddedPostId = postService.updateComment(postId, commentId, request, user);

            return ResponseEntity.ok(commentAddedPostId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            postService.deleteComment(postId, commentId, user);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // subcomment

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}/subcomments")
    public ResponseEntity saveSubcomment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentSaveRequest request, @ApiIgnore @AuthenticationPrincipal User user) {

        return userService.findByEmail(user.getEmail())
            .map(u -> ResponseEntity.ok(postService.saveSubcomment(postId, commentId, request, u)))
            .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}/subcomments/{subcommentId}")
    public ResponseEntity updateSubcomment(@PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long subcommentId, @RequestBody CommentUpdateRequest request, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            Long commentAddedPostId = postService.updateSubcomment(postId, commentId, subcommentId, request, user);

            return ResponseEntity.ok(commentAddedPostId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}/subcomments/{subcommentId}")
    public ResponseEntity deleteSubcomment(@PathVariable Long postId, @PathVariable Long commentId, @PathVariable Long subcommentId, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            postService.deleteSubcomment(postId, commentId, subcommentId, user);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
