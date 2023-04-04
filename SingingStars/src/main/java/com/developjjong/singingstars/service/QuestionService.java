package com.developjjong.singingstars.service;

import com.developjjong.singingstars.DataNotFoundException;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.domain.Question;
import com.developjjong.singingstars.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Question getQuestion(BigInteger id){
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public List<Question> getList(){
        return questionRepository.findAll();
    }

    public Question detail(BigInteger id){
        Optional<Question> singUp = this.questionRepository.findById(id);
        if (singUp.isPresent()){
            return singUp.get();
        } else{
            throw new DataNotFoundException("게시글이 존재하지 않습니다.");
        }
    }

    public void create(String type, String title, String content, SiteUser siteUser){
        Question s = new Question();
        s.setType(type);
        s.setTitle(title);
        s.setContent(content);
        s.setCreated_at(LocalDateTime.now());
        s.setSiteUser(siteUser);
        this.questionRepository.save(s);
    }

    public void update(Question question, String title, String content){
        question.setTitle(title);
        question.setContent(content);
        question.setModified_at(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    public Page<Question> findByType(String type, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return questionRepository.findByType(type, pageable);
    }

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created_at"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }
    public void delete(BigInteger id){
        questionRepository.deleteById(id);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

}
