package com.community.communityboard.domain.post.model.dto.response;


import com.community.communityboard.domain.post.model.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class PostAllResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static PostAllResponseDto of(Post post) {
        return PostAllResponseDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
