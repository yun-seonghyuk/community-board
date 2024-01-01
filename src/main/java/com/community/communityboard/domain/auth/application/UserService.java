package com.community.communityboard.domain.auth.application;

import com.community.communityboard.domain.auth.model.dto.SignupRequestDto;

public interface UserService {
    void signup(SignupRequestDto requestDto);

}
