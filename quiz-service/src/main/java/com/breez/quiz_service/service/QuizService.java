package com.breez.quiz_service.service;

import com.breez.quiz_service.dao.QuizDAO;
import com.breez.quiz_service.feign.QuizInterface;
import com.breez.quiz_service.model.QuestionWrapper;
import com.breez.quiz_service.model.Quiz;
import com.breez.quiz_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

	@Autowired
	private QuizDAO quizDAO;
	@Autowired
	private QuizInterface quizInterface;


	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionsIDs(questions);
		quizDAO.save(quiz);
		return new ResponseEntity<>("success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
		Quiz quiz = quizDAO.findById(quizId).get();
		List<Integer> questionIDs = quiz.getQuestionsIDs();
		ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIDs);
		return questions;
	}

	public ResponseEntity<Integer> calculateResult(Integer quizId, List<Response> responses) {
		ResponseEntity<Integer> score = quizInterface.getScore(responses);
		return score;
	}
}
