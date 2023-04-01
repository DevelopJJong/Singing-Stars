package com.developjjong.singingstars.domain.singup;

import com.developjjong.singingstars.domain.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class SingUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(length=225)
    private String title;

    @Column(length=500)
    private String content;

    private LocalDateTime created_at;

    private LocalDateTime modified_at;

    @OneToMany(mappedBy = "singUp", cascade = CascadeType.REMOVE)
    private List<SingUpComment> answerList;

    @ManyToOne
    private SiteUser siteUser;
}
