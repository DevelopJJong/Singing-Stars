package com.developjjong.singingstars.repository.singup;

import com.developjjong.singingstars.domain.singup.SingUpComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SingUpCommentRepository extends JpaRepository<SingUpComment, BigInteger> {
}
