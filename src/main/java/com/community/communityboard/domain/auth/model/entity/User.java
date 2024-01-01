package com.community.communityboard.domain.auth.model.entity;


import com.community.communityboard.domain.auth.model.dto.SignupRequestDto;
import com.community.communityboard.domain.auth.model.type.UserRole;
import com.community.communityboard.global.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "users")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    public static User createUser(SignupRequestDto requestDto, String password, UserRole role) {
        return User.builder()
                .username(requestDto.getUsername())
                .password(password)
                .email(requestDto.getEmail())
                .role(role)
                .build();

    }


}
