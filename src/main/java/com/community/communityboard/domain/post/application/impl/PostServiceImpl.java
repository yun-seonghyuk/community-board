package com.community.communityboard.domain.post.application.impl;


import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.post.application.PostService;
import com.community.communityboard.domain.post.exception.PostException;
import com.community.communityboard.domain.post.model.dto.request.PostRequestDto;
import com.community.communityboard.domain.post.model.dto.response.PostAllResponseDto;
import com.community.communityboard.domain.post.model.dto.response.PostResponseDto;
import com.community.communityboard.domain.post.model.entity.Post;
import com.community.communityboard.domain.post.repository.PostRepository;
import com.community.communityboard.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.community.communityboard.global.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(Post.createPost(user, requestDto));
        return PostResponseDto.of(post);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostAllResponseDto> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllPost(pageable);
        return postPage.map(PostAllResponseDto::of);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<PostAllResponseDto> getAllLikePosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAllPost(pageable);
        return postPage.map(PostAllResponseDto::of);
    }

    @Override
    public PostResponseDto getPost(Long id) {

        Post post = postRepository.findPostWithCommentsById(id);
        postViewCount(id);
        return PostResponseDto.of(post);
    }

    private void postViewCount(Long postId) {
        redisTemplate
                .opsForValue()
                .increment(RedisUtil.postViewKey(postId), 1);
    }

    @Override
    public void likePost(Long postId, Long userId) {
        // 사용자가 이미 해당 게시물에 대해 좋아요를 눌렀는지 확인
        try {

            Boolean alreadyLiked = redisTemplate
                    .opsForSet()
                    .isMember(RedisUtil.userLikedPostsKey(userId, postId), String.valueOf(postId));

            if (alreadyLiked != null && alreadyLiked) {
                // 이미 좋아요를 누른 경우 좋아요 취소
                redisTemplate.opsForValue().decrement(RedisUtil.postLikesKey(postId));
                redisTemplate.opsForSet().remove(RedisUtil.userLikedPostsKey(userId, postId), String.valueOf(postId));
            } else {
                // 아직 좋아요를 누르지 않은 경우 좋아요 추가
                redisTemplate.opsForValue().increment(RedisUtil.postLikesKey(postId));
                redisTemplate.opsForSet().add(RedisUtil.userLikedPostsKey(userId, postId), String.valueOf(postId));
            }

        } catch (PostException e) {
            throw new PostException(FAILED__TO_PROCESS_LIKE_FOR_POST_ID);
        }
    }

    @Transactional
    @Override
    public PostAllResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = findPostOrElseThrow(id);
        checkPostAuthor(post, user);
        post.postUpdate(postRequestDto);

        return PostAllResponseDto.of(post);
    }

    @Override
    public void deletePost(Long id, User user) {
        Post post = findPostOrElseThrow(id);
        checkPostAuthor(post, user);
        postRepository.delete(post);
    }

    private Post findPostOrElseThrow(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostException(POST_NOT_FOUND));
    }

    // 게시물 작성자 존재여부 확인
    private void checkPostAuthor(Post post, User user) {
        if (!Objects.equals(post.getUser().getId(), user.getId())) {
            throw new PostException(NOT_POST_BY_USER);
        }
    }

}
