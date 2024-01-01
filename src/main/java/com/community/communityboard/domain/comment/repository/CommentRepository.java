package com.community.communityboard.domain.comment.repository;

import com.community.communityboard.domain.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
