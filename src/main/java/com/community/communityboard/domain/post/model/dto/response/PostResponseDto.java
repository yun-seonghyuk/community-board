package com.community.communityboard.domain.post.model.dto.response;


import com.community.communityboard.domain.comment.model.dto.CommentResponseDto;
import com.community.communityboard.domain.comment.model.entity.Comment;
import com.community.communityboard.domain.post.model.entity.Post;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Builder
public class PostResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private Integer viewCount;
    private Integer likeCount;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedAt;

    private List<CommentResponseDto> commentList = new ArrayList<>();

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
                .build();
    }

    public static PostResponseDto from(Post post, Integer likeCount, Integer viewCount) {
        return PostResponseDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(viewCount)
                .likeCount(likeCount)
                .createAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .commentList(post.getCommentList().stream()
                        .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                        .map(CommentResponseDto::of)
                        .collect(Collectors.toList()))
                .build();
    }

}
