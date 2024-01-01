package com.community.communityboard.domain.comment.application;

import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.comment.model.dto.CommentRequestDto;
import com.community.communityboard.domain.comment.model.dto.CommentResponseDto;

public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto requestDto, User user, Long id);

    CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto requestDto, User user);

    void deleteComment(Long postId, Long commentId, User user);
}
