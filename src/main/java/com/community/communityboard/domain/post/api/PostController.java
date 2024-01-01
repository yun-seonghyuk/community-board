package com.community.communityboard.domain.post.api;


import com.community.communityboard.domain.auth.sercurity.UserDetailsImpl;
import com.community.communityboard.domain.post.application.PostService;
import com.community.communityboard.domain.post.model.dto.request.PostRequestDto;
import com.community.communityboard.domain.post.model.dto.response.PostResponseDto;
import com.community.communityboard.global.common.ServiceResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody final PostRequestDto requestDto,
                                        @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        return ResponseEntity.ok()
                .body(postService.createPost(requestDto, userDetails.getUser()));
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable final Long id,
                                      @RequestBody final PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable final Long id,
                                        @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        postService.deletePost(id, userDetails.getUser());
        return ResponseEntity.ok()
                .body(ServiceResult.success("delete success!"));
    }

    @PostMapping("post/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable final Long postId,
                                      @AuthenticationPrincipal final UserDetailsImpl userDetails) {
        postService.likePost(postId, userDetails.getUser().getId());
        return ResponseEntity.ok("Post liked successfully");
    }
}
