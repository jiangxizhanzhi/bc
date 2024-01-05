package com.cyberpeace.oj.practice.service.bo.mock;

import java.util.List;

import com.cyberpeace.oj.practice.dao.entity.PresetMock;

import lombok.Data;

@Data
public class PresetMockBo extends PresetMock {

	private List<CountAndScore> theoryDist;

	private List<CountAndScore> operationDist;

	public Integer getTheoryCount(){
		return theoryDist.stream().mapToInt(CountAndScore::getCount).sum();
	}
}
