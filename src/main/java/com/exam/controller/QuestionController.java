package com.exam.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import com.exam.entity.Users;
import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;
import com.exam.repository.QuestionRepository;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;
import com.exam.service.UserService;
import com.exam.utils.Util;

@RestController
@RequestMapping("/question")
@CrossOrigin("*")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private Util util;
	
	@Autowired
	private UserService userService;
	
		//add question 
//		@PostMapping("/")
//		public ResponseEntity<Question> add(@RequestBody Question question){
//			return ResponseEntity.ok(this.questionService.addQuestion(question));
//		}
	

	@PostMapping("/{qid}")
	public ResponseEntity<Question> add(@RequestBody Question question,@PathVariable("qid") Long qid){
	    try {
	    	Quiz quiz = this.quizService.getQuiz(qid);
	        // 1. Get the quiz id from incoming question
//	        Long quizId = question.getQuiz().getQId();

	        // 2. Fetch quiz from database (full object)
//	        Quiz quiz = this.quizService.getQuiz(quizId);

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
//		@PutMapping("/")
//		public ResponseEntity<Question> update(@RequestBody Question question){
//				return ResponseEntity.ok(this.questionService.addQuestion(question));
//		}
	
	@PutMapping("/")
	public ResponseEntity<?> update(@RequestBody Question question) {
	    Question existing = this.questionService.getQuestion(question.getQuesId());
	    if (existing == null) {
	        return ResponseEntity.status(404).body("Question not found");
	    }
	    return ResponseEntity.ok(this.questionService.updateQuestion(question));
	}

		
		//get all questions of all qid
		@GetMapping("/quiz/{qid}")
		public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
		    Quiz quiz = this.quizService.getQuiz(qid);
		    List<Question> questions=questionRepository.findByQuesId(quiz.getQId());
//		    Set<Question> questions = quiz.getQuestions();
		    List<Question> list = new ArrayList<>(questions);

		    int noOfQuestions = Integer.parseInt(quiz.getNumberOfQuestions());
		    if (list.size() > noOfQuestions) {
		        list = list.subList(0, noOfQuestions);
		    }
		    Collections.shuffle(list);
		    return ResponseEntity.ok(list);
		}

		
		@GetMapping("/quiz/all/{qid}")
		public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {
		    Quiz quiz = this.quizService.getQuiz(qid);

//		    List<Question> questions=questionRepository.findByQuesId(quiz.getQId());
		    List<Question> questions=questionRepository.findByQuiz(quiz);
//		    Set<Question> questions = quiz.getQuestions();
		    List<Question> list = new ArrayList<>(questions);
		    return ResponseEntity.ok(questions);
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
		
		//eval quiz
		@PostMapping("/eval-quiz")
		public ResponseEntity<?> evalQuiz(@RequestBody List<Question> questions,Principal principal){
			System.out.println(questions);
			Users users=userService.getUser(principal.getName());
			  double marksGot=0;
			  int correctAnswers=0;
			  int attempted=0;
			  String quizName = null;
			for(Question q: questions){
				//System.out.println(q.getGivenAnswer());
				quizName=q.getQuiz().getTitle();
				Question question= this.questionService.get(q.getQuesId());
				if(question.getAnswer().equals(q.getGivenAnswer())) {
					correctAnswers++;
					   double marksSingle= Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/ questions.size();
					    marksGot+= marksSingle;
				}
				  if(q.getGivenAnswer()!=null){
					        attempted++;
					       }
				
			};
			
			Map<String,Object> map= Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted);
//			String body = "Dear "+users.getFirstName() +",\n You have marksGot "+marksGot +" correctAnswers "+correctAnswers +" attempted "+attempted +".";
//			String body = """
//			        <html>
//			        <body>
//			            <h2>Dear %s,</h2>
//			            <p>You have completed the quiz with the following results:</p>
//			            <ul>
//			                <li><b>Marks Got:</b> %.2f</li>
//			                <li><b>Correct Answers:</b> %d</li>
//			                <li><b>Attempted Questions:</b> %d</li>
//			            </ul>
//			            <p>Thank you for participating!</p>
//			        </body>
//			        </html>
//			        """.formatted(users.getFirstName(), marksGot, correctAnswers, attempted);
			String body = """
					<html>
					<body style="background-color:#e0ffe0; padding:20px; font-family:Arial, sans-serif;">
					    <div style="text-align:center; font-size:24px; font-weight:bold; color:green; margin-bottom:20px;">
					        🎉 Congratulations! 🎉
					    </div>
					    <h2>Dear %s,</h2>
					    <p>You have completed the quiz with the following results:</p>
					    <ul>
					        <li><b>Marks Got:</b> %.2f</li>
					        <li><b>Correct Answers:</b> %d</li>
					        <li><b>Attempted Questions:</b> %d</li>
					    </ul>
					    <p>Thank you for participating!</p>
					</body>
					</html>
					""".formatted(users.getFirstName(), marksGot, correctAnswers, attempted);

			util.sendEmail(users.getEmail(), "Your Result of "+quizName, body);
			return ResponseEntity.ok(map);
		}
}
