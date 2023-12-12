package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionSchemeTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSchemeTemplate;

import java.io.IOException;
import java.util.List;

public interface EvalSchemeTemplateService extends IService<EvalSchemeTemplate> {

	void addEvalSchemeTemplate(EvalSchemeTemplate evalSchemeTemplate);

	EvalSchemeTemplate getEvalSchemeTemplate(String resourceId);

	void deleteEvalSchemeTemplate(List<String> ids);

	void updateEvalSchemeTemplate(EvalSchemeTemplate evalSchemeTemplate);

	List<EvalMissionSchemeTemplateListRsp> getEvalSchemeTemplateList(PageReqBase pageReqBase);

	String exportSchemeTemplate(List<String> resourceIds, String tempDir) throws IOException;

	void importSchemeTemplate(String unZipPath);

}
