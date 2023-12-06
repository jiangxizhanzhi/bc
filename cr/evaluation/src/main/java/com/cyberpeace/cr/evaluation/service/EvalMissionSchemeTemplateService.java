package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionSchemeTemplate;

import java.util.List;

public interface EvalMissionSchemeTemplateService extends IService<EvalMissionSchemeTemplate> {

	void addEvalMissionSchemeTemplate(EvalMissionSchemeTemplate evalMissionSchemeTemplate);

	EvalMissionSchemeTemplate getEvalMissionSchemeTemplate(String resourceId);

	void deleteEvalMissionSchemeTemplate(List<String> ids);

	void updateEvalMissionSchemeTemplate(EvalMissionSchemeTemplate evalMissionSchemeTemplate);

}
