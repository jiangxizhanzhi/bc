package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCloudTemplate;

import java.util.List;

public interface EvalCloudTemplateService extends IService<EvalCloudTemplate> {

	void addCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq);

	void modifyCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq);

	void deleteCloudTemplate(List<String> ids);

	List<EvalCloudTemplateListRsp> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq);

	EvalCloudTemplateEnumRsp getCloudTemplateEnum();

	EvalCloudTemplateAddReq getCloudTemplate(String resourceId);

}
