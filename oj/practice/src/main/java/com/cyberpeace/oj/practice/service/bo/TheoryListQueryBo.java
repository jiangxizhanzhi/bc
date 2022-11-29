package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

@Data
public class TheoryListQueryBo extends PatternBase {

	private String practiceSetId;

	private List<String> categoryIds;

	private Boolean solved;

	private Integer difficulty;

	private Integer theoryType;

}
