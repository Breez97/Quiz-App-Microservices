package com.breez.quiz_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuestionWrapper {

	private Integer id;
	private String questionTitle;
	private String option_1;
	private String option_2;
	private String option_3;
	private String option_4;

}
