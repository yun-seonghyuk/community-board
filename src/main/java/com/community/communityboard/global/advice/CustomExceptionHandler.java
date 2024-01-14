package com.community.communityboard.global.advice;

import com.community.communityboard.domain.auth.exception.MemberException;
import com.community.communityboard.domain.post.exception.PostException;
import com.community.communityboard.global.common.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<?> postExceptionHandler(PostException e){
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ServiceResult.fail(e.getErrorCode()));
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<?> memberExceptionHandler(MemberException e){
        return ResponseEntity.status(e.getErrorCode().getStatusCode())
                .body(ServiceResult.fail(e.getErrorCode()));
    }


}
