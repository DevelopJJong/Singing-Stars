package com.developjjong.singingstars.domain;

import com.developjjong.singingstars.type.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(length=225, unique = true)
    private String email;

    @Column(length=225)
    private String password;

    @Column(length=50, unique = true)
    private String nickname;

    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String provider;

    private String providerId;
}
