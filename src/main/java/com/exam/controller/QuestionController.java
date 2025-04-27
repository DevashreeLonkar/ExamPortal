package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;
import com.exam.service.QuestionService;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
		//add question 
		@PostMapping("/")
		public ResponseEntity<Question> add(@RequestBody Question question){
			return ResponseEntity.ok(this.questionService.addQuestion(question));
		}
		
		//update question 
		@PutMapping("/")
		public ResponseEntity<Question> update(@RequestBody Question question){
				return ResponseEntity.ok(this.questionService.addQuestion(question));
		}
		
		
}
