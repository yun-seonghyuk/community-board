package com.community.communityboard.domain.post.model.dto.response;


import com.community.communityboard.domain.comment.model.dto.CommentResponseDto;
import com.community.communityboard.domain.post.model.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;


@Builder
public record PostResponseDto(
        Long id,
        String username,
        String title,
        String content,
        Integer viewCount,
        Integer likeCount,
        LocalDateTime createAt,
        LocalDateTime modifiedAt,
        List<CommentResponseDto> commentList
) {
    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .commentList(post.getCommentList().stream()
                        .map(CommentResponseDto::of)
                        .toList())
                .build();
    }
}
