package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

@Data
public class LibPracticeQueryBo extends PatternBase {

	private List<String> resourceIds;

	private String practiceSetId;

	private List<String> categoryIds;

	private Integer questionType;

}
