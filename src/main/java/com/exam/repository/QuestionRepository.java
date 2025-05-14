package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;

public interface QuestionRepository extends JpaRepository<Question, Long> {

//	Set<Question> findByQuiz(Quiz quiz);
	List<Question> findByQuiz(Quiz quiz);
	
	List<Question> findByQuesId(Long qId);

}
