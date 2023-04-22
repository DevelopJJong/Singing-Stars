package com.developjjong.singingstars.repository;

import com.developjjong.singingstars.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, BigInteger> {
    Page<Question> findByTypeOrderByCreatedDesc(String type, Pageable pageable);
    Page<Question> findAll(Pageable pageable);
    List<Question> findBySiteUser(BigInteger id);
    Page<Question> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Question> findByTypeOrderByViewDesc(String type, Pageable pageable);
    Page<Question> findByTypeOrderByVoterDesc(String type, Pageable pageable);
    Page<Question> findByTypeOrderByCommentListDesc(String type, Pageable pageable);

    List<Question> findFirst5ByOrderByViewDesc();
    List<Question> findFirst5ByOrderByCommentListDesc();
    List<Question> findFirst5ByOrderByVoterDesc();


//    Page<Question> findAll(String type, Pageable pageable, String nickname);
//    Page<Question> findByType(String type, Pageable pageable, Sort sort);
//    Page<Question> findByTypeAndSortById(String type, Pageable pageable, BigInteger id);
//    Page<Question> findByTypeAndSortByNickname(String type, String nickname, Pageable pageable);
    @Modifying
    @Query("update Question q set q.view = q.view + 1 where q.id = :id")
    int updateView(@Param("id") BigInteger id);
// Page<Question> findByTypeAndSortCreatedAt(String type, Pageable pageable)
}
