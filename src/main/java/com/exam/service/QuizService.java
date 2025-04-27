package com.exam.service;

import java.util.Set;

import com.exam.entity.exam.Quiz;

public interface QuizService {

	public Quiz addQuiz(Quiz quiz);
	
	public Quiz updateQuiz(Quiz quiz);
	
	public Set<Quiz> getQuizes();
	
	public Quiz getQuiz(Long quizID);
	
	public void deleteQuiz(Long quizID);
}
