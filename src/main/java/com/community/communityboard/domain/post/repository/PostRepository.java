package com.community.communityboard.domain.post.repository;

import com.community.communityboard.domain.post.model.entity.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.commentList c " +
            "LEFT JOIN FETCH c.user u "+
            "WHERE p.id = :postId")
    Post findPostWithCommentsById(@Param("postId") Long postId);

    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    Page<Post> findAllPost(Pageable pageable);

}
