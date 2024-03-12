package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalActionTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalActionTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalActionTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalActionTemplate;

import java.util.List;

public interface EvalActionTemplateService extends IService<EvalActionTemplate> {

	String addAction(EvalActionTemplateAddReq evalActionTemplateAddReq);

	void deleteAction(List<String> ids);

	void modifyAction(EvalActionTemplateModifyReq evalActionTemplateModifyReq);

	EvalActionTemplateEnumRsp getActionEnum();
}
