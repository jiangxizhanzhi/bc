package com.cyberpeace.oj.practice.service.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TheoryChangeBo implements Serializable {

	private String answer;

	private String content;

	private Integer theoryType;

	private String blank;

	private String option;
}
