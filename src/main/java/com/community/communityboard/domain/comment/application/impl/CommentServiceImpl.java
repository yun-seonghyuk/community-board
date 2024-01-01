package com.community.communityboard.domain.comment.application.impl;


import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.comment.application.CommentService;
import com.community.communityboard.domain.comment.model.dto.CommentRequestDto;
import com.community.communityboard.domain.comment.model.dto.CommentResponseDto;
import com.community.communityboard.domain.comment.model.entity.Comment;
import com.community.communityboard.domain.comment.repository.CommentRepository;
import com.community.communityboard.domain.post.exception.PostException;
import com.community.communityboard.domain.post.model.entity.Post;
import com.community.communityboard.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.community.communityboard.global.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto,
                                            User user,
                                            Long id) {
        Post post = findPostOrElseThrow(id);

        Comment comment = commentRepository.save(
                Comment.createComment(user, post, requestDto.getContent()));

        return CommentResponseDto.of(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto updateComment(Long postId, Long commentId,
                                            CommentRequestDto requestDto,
                                            User user) {
        postExistsCheckOrElseThrow(postId);
        Comment comment = findCommentOrElseThrow(commentId);
        checkPostAuthor(comment, user);

        comment.commentUpdate(requestDto);

        return CommentResponseDto.of(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId, User user) {
        postExistsCheckOrElseThrow(postId);
        Comment comment = findCommentOrElseThrow(commentId);
        checkPostAuthor(comment, user);

        commentRepository.delete(comment);
    }


    private Post findPostOrElseThrow(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new PostException(POST_NOT_FOUND)
        );
    }

    private void postExistsCheckOrElseThrow(Long id) {
        postRepository.findById(id).orElseThrow(
                () -> new PostException(POST_NOT_FOUND)
        );
    }

    private Comment findCommentOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new PostException(COMMENT_NOT_FOUND)
        );
    }

    // 게시물 작성자 존재여부 확인
    private void checkPostAuthor(Comment comment, User user) {
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new PostException(NOT_POST_BY_USER);
        }
    }

}
