package com.developjjong.singingstars.repository;

import com.developjjong.singingstars.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, BigInteger> {
    Page<Question> findByType(String type, Pageable pageable);

    @Modifying
    @Query("update Question q set q.view = q.view + 1 where q.id = :id")
    int updateView(@Param("id") BigInteger id);
}
