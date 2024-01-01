package com.community.communityboard.domain.auth.api;


import com.community.communityboard.domain.auth.application.UserService;
import com.community.communityboard.domain.auth.model.dto.SignupRequestDto;
import com.community.communityboard.global.common.ServiceResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid final SignupRequestDto requestDto){
        userService.signup(requestDto);
        return ResponseEntity.ok()
                .body(ServiceResult.success("회원가입을 축하드립니다."));
    }


}
