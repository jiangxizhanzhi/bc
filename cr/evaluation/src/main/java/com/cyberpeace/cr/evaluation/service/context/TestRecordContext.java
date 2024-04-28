package com.cyberpeace.cr.evaluation.service.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestRecordContext implements Serializable {

	private Integer count;

	private Integer score;

	private String testId;
}
