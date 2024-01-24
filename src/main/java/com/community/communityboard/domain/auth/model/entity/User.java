package com.community.communityboard.domain.auth.model.entity;


import com.community.communityboard.domain.auth.model.dto.SignupRequestDto;
import com.community.communityboard.domain.auth.model.type.UserRole;
import com.community.communityboard.global.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public static User createUser(SignupRequestDto requestDto, String password, UserRole role) {
        return User.builder()
                .username(requestDto.username())
                .password(password)
                .email(requestDto.email())
                .role(role)
                .build();
    }

}
