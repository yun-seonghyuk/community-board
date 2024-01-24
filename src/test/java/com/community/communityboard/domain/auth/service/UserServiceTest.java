package com.community.communityboard.domain.auth.service;

import com.community.communityboard.domain.auth.application.impl.UserServiceImpl;
import com.community.communityboard.domain.auth.exception.MemberException;
import com.community.communityboard.domain.auth.model.dto.SignupRequestDto;
import com.community.communityboard.domain.auth.model.entity.User;
import com.community.communityboard.domain.auth.model.type.UserRole;
import com.community.communityboard.domain.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private SignupRequestDto requestDto;
    private User user;

    @BeforeEach
    void setUp() {
        // Given
        requestDto = new SignupRequestDto("윤성혁",
                        "123456",
                        "user@example.com",
                        false,
                        "");

        String encodedPassword = passwordEncoder.encode(requestDto.password());
        user = User.createUser(requestDto, encodedPassword, UserRole.USER);
    }

    @Test
    void testSignupWithNewUser() {
        // Given
        given(userRepository.findByEmail(requestDto.email()))
                .willReturn(Optional.empty());
        given(passwordEncoder.encode(requestDto.password()))
                .willReturn(user.getPassword());

        // When
        userService.signup(requestDto);

        // Then
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSignupWithDuplicateEmail() {
        // Given
        given(userRepository.findByEmail(requestDto.email()))
                .willReturn(Optional.of(user));

        // When + Then
        assertThrows(MemberException.class, () -> userService.signup(requestDto));
    }
}
