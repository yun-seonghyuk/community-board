package com.community.communityboard.domain.auth.model.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequestDto(

        @NotNull(message = "이름을 입력해주세요.")
        String email,
        @NotNull(message = "비밀번호를 입력해주세요.")
        String password
){
}
