package com.community.communityboard.domain.post.repository;

import com.community.communityboard.domain.post.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post,Long> {
    
    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.commentList c "+
            "LEFT JOIN FETCH c.user u")
    Page<Post> findAllPost(Pageable pageable);

    @Query("SELECT p FROM Post p " +
            "LEFT JOIN FETCH p.commentList c "+
            "LEFT JOIN FETCH c.user u")
    Page<Post> findAllPostLikeDesc(Pageable pageable);
}
