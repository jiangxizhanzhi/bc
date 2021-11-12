package com.cyberpeace.oj.practice.dao.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TheoryListQuery implements Serializable {

	private List<String> resourceIds;

	private String practiceSetId;

	private List<String> categoryIds;

	private String pattern;

}
