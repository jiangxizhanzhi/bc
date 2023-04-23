package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

import java.util.List;

@Data
public class TheoryListQueryBo extends PatternBase {

	private String practiceSetId;

	private List<String> categoryIds;

	private Integer solved;

	private Integer difficulty;

	private Integer theoryType;

}
