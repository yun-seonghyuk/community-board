package com.community.communityboard.domain.post.model.dto.response;


import com.community.communityboard.domain.post.model.entity.Post;
import lombok.Builder;
import java.time.LocalDateTime;


@Builder
public record PostAllResponseDto (
        Long id,
        String username,
        String title,
        String content,
        Integer viewCount,
        Integer likeCount,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
){
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
