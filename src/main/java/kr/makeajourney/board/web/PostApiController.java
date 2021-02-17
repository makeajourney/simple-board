package kr.makeajourney.board.web;

import kr.makeajourney.board.service.PostService;
import kr.makeajourney.board.web.dto.CommentSaveRequest;
import kr.makeajourney.board.web.dto.CommentUpdateRequest;
import kr.makeajourney.board.web.dto.PostResponse;
import kr.makeajourney.board.web.dto.PostSaveRequest;
import kr.makeajourney.board.web.dto.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public ResponseEntity save(@RequestBody PostSaveRequest request) {
        return ResponseEntity.ok(postService.save(request));
    }

    @PostMapping("/api/v1/posts/{postId}")
    public ResponseEntity update(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        try {
            Long updatedPostId = postService.update(postId, request);

            return ResponseEntity.ok(updatedPostId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity findById(@PathVariable Long postId) {

        return postService.findById(postId)
            .map(p -> ResponseEntity.ok(new PostResponse(p)))
            .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    public ResponseEntity delete(@PathVariable Long postId) {
        try {
            postService.delete(postId);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/v1/posts/{postId}/comments")
    public ResponseEntity saveComment(@PathVariable Long postId, @RequestBody CommentSaveRequest request) {
        try {
            Long commentAddedPostId = postService.saveComment(postId, request);

            return ResponseEntity.ok(commentAddedPostId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        try {
            Long commentAddedPostId = postService.updateComment(postId, commentId, request);

            return ResponseEntity.ok(commentAddedPostId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        try {
            postService.deleteComment(postId, commentId);

            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
