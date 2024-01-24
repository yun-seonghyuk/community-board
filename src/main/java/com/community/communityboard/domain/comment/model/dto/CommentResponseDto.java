package com.community.communityboard.domain.comment.model.dto;

import com.community.communityboard.domain.comment.model.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponseDto(
        Long id,
        String username,
        String content,
        LocalDateTime createAt,
        LocalDateTime modifiedAt
) {

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .username(comment.getUser().getUsername())
                .content(comment.getContent())
                .createAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
