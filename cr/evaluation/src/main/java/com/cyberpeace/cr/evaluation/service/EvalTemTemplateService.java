package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemTemplate;


public interface EvalTemTemplateService extends IService<EvalTemTemplate> {

	String createTemplate(EvalTemTemplateReq evalTemTemplateReq);

	void updateTemplate(UpdateEvalTemTemplateReq updateEvalTemTemplateReq);

}
