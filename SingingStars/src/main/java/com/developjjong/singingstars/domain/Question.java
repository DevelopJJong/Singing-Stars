package com.developjjong.singingstars.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(length=225)
    private String title;

    @Column(length=500)
    private String content;

    private LocalDateTime created_at;

    private LocalDateTime modified_at;

    private String type;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    @ManyToOne
    private SiteUser siteUser;

    public String getNickname(){
        return this.siteUser.getNickname();
    }

    @ManyToMany
    Set<SiteUser> voter;
}
