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

    @Column(length=225, nullable = false)
    private String title;

    @Column(length=500)
    private String content;

    @Column(length=20000)
    private String video;

    @Column(nullable = false)
    private LocalDateTime created;

    private LocalDateTime modified_at;

    @Column(nullable = false)
    private String type;


    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

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
