package com.developjjong.singingstars.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(length=500)
    private String content;

    private LocalDateTime created_at;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser siteUser;

    public String getNickname(){
        return this.siteUser.getNickname();
    }


    @ManyToMany
    Set<SiteUser> voter;
}
