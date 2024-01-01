package com.community.communityboard.domain.auth.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {

    @Size(min=4, max = 10, message = "4자 이상 10자 이하여야 합니다.")
    @NotNull(message = "이름을 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]*$")
    private String username;

    @Size(min=8, max = 15, message = "8자 이상 15자 이하여야 합니다.")
    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String password;

    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}
