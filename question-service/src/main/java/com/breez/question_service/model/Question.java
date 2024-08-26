package com.breez.question_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String category;
	private String questionTitle;
	private String option_1;
	private String option_2;
	private String option_3;
	private String option_4;
	private String rightAnswer;
	private String difficultyLevel;

}
