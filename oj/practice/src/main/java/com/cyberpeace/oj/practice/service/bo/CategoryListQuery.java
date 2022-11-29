package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryListQuery extends PatternBase {

	private Integer practiceType;

}
