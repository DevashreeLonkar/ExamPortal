package com.exam.service;

import java.util.List;
import java.util.Set;

import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;

public interface QuestionService {

	public Question addQuestion(Question question);
	
	public Question updateQuestion(Question question);
	
	public Set<Question> getQuestions();
	
	public Question getQuestion(Long questionId);
	
//	public Set<Question> getQuestionsOfQuiz(Quiz quiz);
	public List<Question> getQuestionsOfQuiz(Quiz quiz);
	
	public void deleteQuestion(Long quesId);
	
	public Question get(Long questionsId);
}
