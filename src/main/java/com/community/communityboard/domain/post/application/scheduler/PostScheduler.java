package com.community.communityboard.domain.post.application.scheduler;


import com.community.communityboard.domain.post.repository.PostRepository;
import com.community.communityboard.global.config.RedisCacheConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostScheduler {

    private final RedisCacheConfig redisCacheConfig;
    private final PostRepository postRepository;


    // Write-Back
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void ViewLikeCountDBUpdate() {
        Set<String> keys = redisCacheConfig.redisTemplate().keys("post:*:view_count");
        for (String key : Objects.requireNonNull(keys)) {
            Long postId = Long.parseLong(key.split(":")[1]);
            String viewCount = redisCacheConfig.redisTemplate().opsForValue().get(key);

            postRepository.findById(postId).ifPresent(view ->
                    view.viewCountUpdate(Integer.parseInt(Objects.requireNonNull(viewCount))));
        }

        Set<String> likeKeys = redisCacheConfig.redisTemplate().keys("post:*:likes");
        for (String key : Objects.requireNonNull(likeKeys)) {
            Long postId = Long.parseLong(key.split(":")[1]);
            String likeCount = redisCacheConfig.redisTemplate().opsForValue().get(key);
            postRepository.findById(postId).ifPresent(like ->
                    like.likeCountUpdate(Integer.parseInt(Objects.requireNonNull(likeCount))));
        }
    }

}
