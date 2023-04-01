package com.developjjong.singingstars.domain.singup;

import com.developjjong.singingstars.domain.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class SingUpComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(length=500)
    private String content;

    private LocalDateTime created_at;

    @ManyToOne
    private SingUp singUp;

    @ManyToOne
    private SiteUser siteUser;
}
