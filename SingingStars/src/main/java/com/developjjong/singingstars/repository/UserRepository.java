package com.developjjong.singingstars.repository;

import com.developjjong.singingstars.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, BigInteger>{
    Optional<SiteUser> findByEmail(String email);
    Optional<SiteUser> findByNickname(String nickname);
}
