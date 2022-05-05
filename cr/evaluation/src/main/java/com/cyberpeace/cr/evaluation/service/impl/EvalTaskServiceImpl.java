package com.cyberpeace.cr.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskStepMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalTaskServiceImpl implements EvalTaskService {

	@Autowired
	private EvalTaskStepMapper evalTaskStepMapper;

	@Override
	public Boolean getStepsByTaskId(String taskId) {
		return true;
	}

}