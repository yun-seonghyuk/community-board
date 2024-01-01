package com.community.communityboard.domain.post.application.impl;


import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.post.application.PostService;
import com.community.communityboard.domain.post.exception.PostException;
import com.community.communityboard.domain.post.model.dto.request.PostRequestDto;
import com.community.communityboard.domain.post.model.dto.response.PostResponseDto;
import com.community.communityboard.domain.post.model.entity.Post;
import com.community.communityboard.domain.post.repository.PostRepository;
import com.community.communityboard.global.config.RedisCacheConfig;
import com.community.communityboard.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.community.communityboard.global.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final RedisCacheConfig redisCacheConfig;

    @Override
    public PostResponseDto createPost(PostRequestDto requestDto, User user) {
        Post post = postRepository.save(Post.createPost(user, requestDto));
        return PostResponseDto.of(post);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = findPostOrElseThrow(id);
        postViewCount(id);
        return PostResponseDto.from(post, getLikesCountForPost(id), getViewsCountForPost(id));
    }

    private void postViewCount(Long postId) {
        redisCacheConfig.redisTemplate()
                .opsForValue()
                .increment(RedisUtil.postViewKey(postId), 1);
    }
    // dto 반환 좋아요
    private Integer getLikesCountForPost(Long postId) {
        String likes = redisCacheConfig.redisTemplate().opsForValue()
                .get(RedisUtil.postLikesKey(postId));
        return likes != null ? Integer.parseInt(likes) : 0;
    }

    // dto 반환 조회수
    private Integer getViewsCountForPost(Long postId) {
        String views = redisCacheConfig.redisTemplate().opsForValue()
                .get(RedisUtil.postViewKey(postId));
        return views != null ? Integer.parseInt(views) : 0;
    }


    @Transactional
    @Override
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = findPostOrElseThrow(id);
        checkPostAuthor(post, user);
        post.postUpdate(postRequestDto);

        return PostResponseDto.of(post);
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

    @Override
    @Async
    public void likePost(Long postId, Long userId) {
        // 사용자가 이미 해당 게시물에 대해 좋아요를 눌렀는지 확인
        try {

            Boolean alreadyLiked = redisCacheConfig.redisTemplate()
                    .opsForSet()
                    .isMember(RedisUtil.userLikedPostsKey(userId, postId), String.valueOf(postId));

            if (alreadyLiked != null && alreadyLiked) {
                // 이미 좋아요를 누른 경우 좋아요 취소
                redisCacheConfig.redisTemplate().opsForValue().decrement(RedisUtil.postLikesKey(postId));
                redisCacheConfig.redisTemplate().opsForSet().remove(RedisUtil.userLikedPostsKey(userId, postId), String.valueOf(postId));
            } else {
                // 아직 좋아요를 누르지 않은 경우 좋아요 추가
                redisCacheConfig.redisTemplate().opsForValue().increment(RedisUtil.postLikesKey(postId));
                redisCacheConfig.redisTemplate().opsForSet().add(RedisUtil.userLikedPostsKey(userId, postId), String.valueOf(postId));
            }

        } catch (PostException e) {
            throw new PostException(FAILED__TO_PROCESS_LIKE_FOR_POST_ID);
        }
    }
}
