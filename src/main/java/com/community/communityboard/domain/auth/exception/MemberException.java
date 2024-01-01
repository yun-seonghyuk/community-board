package com.community.communityboard.domain.auth.exception;

import com.community.communityboard.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException {
    private ErrorCode errorCode;

}
