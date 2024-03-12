package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalHostAddReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalHostTemplate;

import java.util.List;

public interface EvalHostTemplateService extends IService<EvalHostTemplate> {

	String addHost(EvalHostAddReq evalHostAddReq);

	void deleteHost(List<String> ids);
}
