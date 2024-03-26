package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplate;
import com.cyberpeace.cr.evaluation.service.impl.bo.MissionScheme;

import java.util.List;

public interface EvalMissionTemplateService extends IService<EvalMissionTemplate> {

	String addMissionTemplate(EvalMissionTemplateReq evalMissionTemplateReq);

	void updateMissionTemplate(UpdateEvalMissionTemplateReq updateEvalMissionReq);

	EvalMissionTemplateRsp getMissionTemplate(String resourceId);

	void deleteMissionTemplate(List<String> resourceIds);

	List<String> getTaskIds(MissionScheme missionSchemeList);

	EvalMissionTemplateListRsp getMissionTemplates(String resourceId);
}
