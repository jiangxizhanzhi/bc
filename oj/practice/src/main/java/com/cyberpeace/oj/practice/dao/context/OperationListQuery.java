package com.cyberpeace.oj.practice.dao.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class OperationListQuery implements Serializable {

	private List<String> resourceIds;

	private List<String> practiceSetIds;

	private List<String> categoryIds;

	private Integer difficulty;

	private String pattern;

	private String knowledge;

}
