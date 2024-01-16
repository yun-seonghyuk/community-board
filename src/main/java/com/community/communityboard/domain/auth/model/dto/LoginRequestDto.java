package com.community.communityboard.domain.auth.model.dto;

public record LoginRequestDto(
        String email,
        String password
){
}
