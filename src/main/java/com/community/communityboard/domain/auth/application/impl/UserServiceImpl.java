package com.community.communityboard.domain.auth.application.impl;


import com.community.communityboard.domain.auth.application.UserService;
import com.community.communityboard.domain.auth.exception.MemberException;
import com.community.communityboard.domain.auth.model.dto.SignupRequestDto;
import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.auth.model.type.UserRole;
import com.community.communityboard.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.community.communityboard.global.exception.ErrorCode.DUPLICATE_EMAIL;
import static com.community.communityboard.global.exception.ErrorCode.INVALID_ADMIN_PASSWORD;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Override
    public void signup(final SignupRequestDto requestDto) {

        emailDuplicateCheck(requestDto.getEmail());

        UserRole role = userRoleCheck(requestDto);
        String password = passwordEncoder.encode(requestDto.getPassword());

        userRepository.save( User.createUser(requestDto, password, role));
    }

    private void emailDuplicateCheck(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
                    throw new MemberException(DUPLICATE_EMAIL);
                });
    }

    private UserRole userRoleCheck(SignupRequestDto requestDto) {
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new MemberException(INVALID_ADMIN_PASSWORD);
            }
            return UserRole.ADMIN;
        }
        return UserRole.USER;
    }



}
