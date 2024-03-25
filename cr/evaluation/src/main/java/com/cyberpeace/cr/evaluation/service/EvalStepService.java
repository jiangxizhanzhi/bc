package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStep;

import java.util.List;
import java.util.Map;

public interface EvalStepService extends IService<EvalStep> {

	Map<String, String> createByTemplate(List<String> templateIdList, String testId);

}
