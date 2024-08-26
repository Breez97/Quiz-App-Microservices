package com.breez.question_service.controller;

import com.breez.question_service.model.Question;
import com.breez.question_service.model.QuestionWrapper;
import com.breez.question_service.model.Response;
import com.breez.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private Environment environment;

	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category) {
		return questionService.getQuestionsByCategory(category);
	}

	@PostMapping("/add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) {
		return questionService.addQuestion(question);
	}

	@GetMapping("/generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam int numQuestions) {
		return questionService.getQuestionsForQuiz(categoryName, numQuestions);
	}

	@PostMapping("/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsIds) {
		System.out.println(environment.getProperty("local.server.port"));
		return questionService.getQuestionsFromId(questionsIds);
	}

	@PostMapping("/getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses) {
		return questionService.getScore(responses);
	}

}
