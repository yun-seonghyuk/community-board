package com.community.communityboard.domain.post.model.entity;


import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.comment.model.entity.Comment;
import com.community.communityboard.domain.post.model.dto.request.PostRequestDto;
import com.community.communityboard.global.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "post")
public class Post extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column
    private Integer viewCount;

    @Column
    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    private List<Comment> commentList;

    public static Post createPost(User user, PostRequestDto requestDto){
        return Post.builder()
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .viewCount(0)
                .likeCount(0)
                .build();
    }

    public void postUpdate(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.setModifiedAt(LocalDateTime.now());
    }


    public void viewCountUpdate(int viewCount) {
        this.viewCount = viewCount;
    }

    public void likeCountUpdate(int likeCount){
        this.likeCount = likeCount;
    }
}
