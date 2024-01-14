package com.community.communityboard.global.common;

import com.community.communityboard.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResult {

    private int status;
    private HttpStatus statusCode;
    private String message;

    public static ServiceResult fail(ErrorCode errorCode) {
        return ServiceResult.builder()
                .status(errorCode.getStatusCode().value())
                .statusCode(errorCode.getStatusCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ServiceResult success(String message) {
        return ServiceResult.builder()
                .status(HttpStatus.OK.value())
                .statusCode(HttpStatus.OK)
                .message(message)
                .build();
    }

}
