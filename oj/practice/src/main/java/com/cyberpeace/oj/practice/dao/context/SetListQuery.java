package com.cyberpeace.oj.practice.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetListQuery extends PatternBase {

	private List<String> setIds;

	private Integer practiceType;

}
