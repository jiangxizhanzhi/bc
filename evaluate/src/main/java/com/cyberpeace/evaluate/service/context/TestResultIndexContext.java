package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class TestResultIndexContext implements Serializable {

	private String indexName;

	private String indexId;

	private String value;
}
