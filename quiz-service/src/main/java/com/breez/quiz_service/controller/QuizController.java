package com.breez.quiz_service.controller;

import com.breez.quiz_service.model.QuestionWrapper;
import com.breez.quiz_service.model.QuizDTO;
import com.breez.quiz_service.model.Response;
import com.breez.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@PostMapping("/create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) {
		return quizService.createQuiz(quizDTO.getCategoryName(), quizDTO.getNumQuestions(), quizDTO.getTitle());
	}

	@GetMapping("/get/{quizId}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("quizId") Integer quizId) {
		return quizService.getQuizQuestions(quizId);
	}

	@PostMapping("/submit/{quizId}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable("quizId") Integer quizId, @RequestBody List<Response> responses) {
		return quizService.calculateResult(quizId, responses);
	}

}
