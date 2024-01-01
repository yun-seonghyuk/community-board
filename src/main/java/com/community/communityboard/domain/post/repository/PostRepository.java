package com.community.communityboard.domain.post.repository;

import com.community.communityboard.domain.post.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

}
