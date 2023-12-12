package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemTemplatePageReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemTemplateRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemTemplate;

import java.util.List;


public interface EvalTemTemplateService extends IService<EvalTemTemplate> {

	String createTemplate(EvalTemTemplateReq evalTemTemplateReq);

	void updateTemplate(UpdateEvalTemTemplateReq updateEvalTemTemplateReq);

	void deleteTemplates(List<String> resourceIds);

	EvalTemTemplateRsp getTemplate(String resourceId);

	List<EvalTemTemplateListRsp> getTemplateList(EvalTemTemplatePageReq pageReqBase);
}
