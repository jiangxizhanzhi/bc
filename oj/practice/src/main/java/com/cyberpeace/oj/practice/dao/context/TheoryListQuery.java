package com.cyberpeace.oj.practice.dao.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TheoryListQuery extends OperationListQuery{

	private Integer questionType;

}
