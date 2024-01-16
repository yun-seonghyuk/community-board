package com.community.communityboard.domain.comment.model.dto;

import jakarta.validation.constraints.NotNull;

public record CommentRequestDto(

        @NotNull(message = "내용을 입력해주세요.")
        String content
) {
}
