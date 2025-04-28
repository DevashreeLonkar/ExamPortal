package com.exam.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
		//add question 
//		@PostMapping("/")
//		public ResponseEntity<Question> add(@RequestBody Question question){
//			return ResponseEntity.ok(this.questionService.addQuestion(question));
//		}
	

	@PostMapping("/")
	public ResponseEntity<Question> add(@RequestBody Question question){
	    try {
	        // 1. Get the quiz id from incoming question
	        Long quizId = question.getQuiz().getQId();

	        // 2. Fetch quiz from database (full object)
	        Quiz quiz = this.quizService.getQuiz(quizId);

	        // 3. Set the full quiz back to question
	        question.setQuiz(quiz);

	        // 4. Now save
	        Question savedQuestion = this.questionService.addQuestion(question);

	        return ResponseEntity.ok(savedQuestion);
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(null); // or better: custom error message
	    }
	}


		//update question 
		@PutMapping("/")
		public ResponseEntity<Question> update(@RequestBody Question question){
				return ResponseEntity.ok(this.questionService.addQuestion(question));
		}
		
		//get all questions of all qid
		@GetMapping("/quiz/{qid}")
		public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
		    Quiz quiz = this.quizService.getQuiz(qid);
		    Set<Question> questions = quiz.getQuestions();
		    List<Question> list = new ArrayList<>(questions);

		    int noOfQuestions = Integer.parseInt(quiz.getNumberOfQuestions());
		    if (list.size() > noOfQuestions) {
		        list = list.subList(0, noOfQuestions);
		    }
		    Collections.shuffle(list);
		    return ResponseEntity.ok(list);
		}

		
		//get a single question
		@GetMapping("/{quesId}")
		public Question get(@PathVariable("quesId") Long quesId) {
			return this.questionService.getQuestion(quesId);
		}
		
		//delete a question
		@DeleteMapping("/{quesId}")
		public void delete(@PathVariable ("quesId") Long quesId) {
			this.questionService.deleteQuestion(quesId);
		}
}
