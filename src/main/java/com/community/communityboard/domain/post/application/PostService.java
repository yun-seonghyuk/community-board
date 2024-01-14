package com.community.communityboard.domain.post.application;

import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.post.model.dto.request.PostRequestDto;
import com.community.communityboard.domain.post.model.dto.response.PostAllResponseDto;
import com.community.communityboard.domain.post.model.dto.response.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostResponseDto createPost(PostRequestDto requestDto, User user);

    Page<PostAllResponseDto> getAllPosts(Pageable pageable);

    Page<PostAllResponseDto> getAllLikePosts(Pageable pageable);

    PostResponseDto getPost(Long id);

    PostAllResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user);

    void deletePost(Long id, User user);

    void likePost(Long postId, Long id);
}
