package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationTarget;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationDumpSlimBo {

	private List<PracticeOperation> operationList;

	private List<PracticeOperationTarget> targetList;
}
