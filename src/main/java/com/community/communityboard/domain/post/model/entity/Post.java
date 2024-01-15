package com.community.communityboard.domain.post.model.entity;


import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.comment.model.entity.Comment;
import com.community.communityboard.domain.post.model.dto.request.PostRequestDto;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer viewCount;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "post", cascade = {CascadeType.ALL})
    private List<Comment> commentList;

    public static Post createPost(User user, PostRequestDto requestDto){
        return Post.builder()
                .user(user)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .viewCount(0)
                .likeCount(0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    public void postUpdate(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }


    public void viewCountUpdate(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public void likeCountUpdate(Integer likeCount){
        this.likeCount = likeCount;
    }
}
