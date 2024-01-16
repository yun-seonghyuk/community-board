package com.community.communityboard.domain.post.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record PostRequestDto(

        @NotNull(message = "제목을 입력해주세요.")
        String title,

        @NotNull(message = "내용을 입력해주세요.")
        String content
) {

}
