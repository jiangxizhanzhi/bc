package com.cyberpeace.oj.practice.service.bo.mock;

import lombok.Data;

@Data
public class MockQuestionDist {

	private CountAndScore single;

	private CountAndScore multiple;

	private CountAndScore judgment;

	private CountAndScore fillBlank;

	private CountAndScore shortAnswer;

	private CountAndScore operation;

	private CountAndScore ctf;
}
