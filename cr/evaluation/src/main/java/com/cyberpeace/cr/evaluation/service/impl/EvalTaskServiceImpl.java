package com.cyberpeace.cr.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.evaluation.dao.mapper.TaskStepMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalTaskServiceImpl implements EvalTaskService {

	@Autowired
	private TaskStepMapper taskStepMapper;

	@Override
	public Boolean getStepsByTaskId(String taskId) {
		return true;
	}

}