package com.community.communityboard.domain.comment.model.entity;

import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.comment.model.dto.CommentRequestDto;
import com.community.communityboard.domain.post.model.entity.Post;
import com.community.communityboard.global.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "comment")
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static Comment createComment(User user, Post post, String content){
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();
    }

    public void commentUpdate(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.setModifiedAt(LocalDateTime.now());
    }


}



