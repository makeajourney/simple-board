package kr.makeajourney.board.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import kr.makeajourney.board.domain.user.User;
import kr.makeajourney.board.dto.PostDto;
import kr.makeajourney.board.service.PostService;
import kr.makeajourney.board.web.model.CommentSaveRequestModel;
import kr.makeajourney.board.web.model.CommentUpdateRequestModel;
import kr.makeajourney.board.web.model.PostDetailResponseModel;
import kr.makeajourney.board.web.model.PostListResponseModel;
import kr.makeajourney.board.web.model.PostSaveRequestModel;
import kr.makeajourney.board.web.model.PostUpdateRequestModel;
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

    @PostMapping("/api/v1/posts")
    public ResponseEntity save(@RequestBody PostSaveRequestModel request, @ApiIgnore @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.save(request.toDto(), user));
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
        Page<PostDto> postPage = postService.findAll(pageable);

        List<PostListResponseModel> postListResponseListModel = postPage.getContent().stream()
            .map(PostListResponseModel::new)
            .collect(Collectors.toList());

        PageImpl<PostListResponseModel> response = new PageImpl<>(postListResponseListModel, postPage.getPageable(), postPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/posts/{postId}")
    public ResponseEntity update(@PathVariable Long postId, @RequestBody PostUpdateRequestModel request, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            Long updatedPostId = postService.update(postId, request.toDto(), user);

            return ResponseEntity.ok(updatedPostId);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/v1/posts/{postId}")
    public ResponseEntity findById(@PathVariable Long postId) {
        try {
            PostDto postDto = postService.findById(postId);

            return ResponseEntity.ok(new PostDetailResponseModel(postDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.noContent().build();
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
    public ResponseEntity saveComment(@PathVariable Long postId, @RequestBody CommentSaveRequestModel request, @ApiIgnore @AuthenticationPrincipal User user) {

        try {
            return ResponseEntity.ok(postService.saveComment(postId, request.toDto(), user));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/api/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentUpdateRequestModel request, @ApiIgnore @AuthenticationPrincipal User user) {
        try {
            Long commentAddedPostId = postService.updateComment(postId, commentId, request.toDto(), user);

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
    public ResponseEntity saveSubcomment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentSaveRequestModel request, @ApiIgnore @AuthenticationPrincipal User user) {

        try {
            return ResponseEntity.ok(postService.saveSubcomment(postId, commentId, request.toDto(), user));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
