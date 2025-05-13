package com.exam.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.entity.exam.Category;
import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;
import com.exam.repository.QuestionRepository;
import com.exam.repository.QuizRepository;
import com.exam.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired 
    private QuestionRepository questionRepository;
    
    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizes() {
        return new HashSet<>(this.quizRepository.findAll());
    }

    @Override
    public Quiz getQuiz(Long quizID) {
        Quiz quiz = quizRepository.findById(quizID)
                         .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizID));
        List<Question> questions=questionRepository.findByQuesId(quiz.getQId());
        System.out.println("------------->"+questions.size());
        // ✨ FORCE fetch questions inside the Quiz
//        quiz.getQuestions().size();  // <-- ADD THIS LINE
        questions.size();
        return quiz;
    }

    @Override
    public void deleteQuiz(Long quizID) {
//        Quiz quiz = new Quiz();
//        quiz.setQId(quizID);
        this.quizRepository.deleteById(quizID);
    }

	@Override
	public List<Quiz> getQuizzesOfCategory(Category category) {
		return this.quizRepository.findBycategory(category);
	}
}
