package com.community.communityboard.domain.post.application;


import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.post.model.dto.request.PostRequestDto;
import com.community.communityboard.domain.post.model.dto.response.PostResponseDto;

public interface PostService {

    PostResponseDto createPost(PostRequestDto requestDto, User user);

    PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user);

    void deletePost(Long id, User user);

}
