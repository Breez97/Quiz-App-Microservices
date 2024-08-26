package com.breez.question_service.service;

import com.breez.question_service.model.Response;
import com.breez.question_service.repository.QuestionDAO;
import com.breez.question_service.model.Question;
import com.breez.question_service.model.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

	@Autowired
	private QuestionDAO questionDAO;

	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		try {
			return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(Question question) {
		questionDAO.save(question);
		return new ResponseEntity<>("success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, int numQuestions) {
		List<Integer> questions = questionDAO.findRandomQuestionsByCategory(categoryName, numQuestions);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsIds) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>();
		for (Integer id : questionsIds) {
			Optional<Question> question = questionDAO.findById(id);
			if (question.isPresent()) {
				questions.add(question.get());
			}
		}
		for (Question question : questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption_1(question.getOption_1());
			wrapper.setOption_2(question.getOption_2());
			wrapper.setOption_3(question.getOption_3());
			wrapper.setOption_4(question.getOption_4());
			wrappers.add(wrapper);
		}
		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int right = 0;
		for (Response response : responses) {
			Question question = questionDAO.findById(response.getId()).get();
			if (response.getResponse().equals(question.getRightAnswer())) {
				right++;
			}
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}
}
