package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalSceneNodeOpsReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalNodeLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public interface EvalNodeLogService extends IService<EvalNodeLog> {

	void logNodeOperate(@Validated @RequestBody EvalSceneNodeOpsReq nodeOpsReq, String resourceId, String mpSceneId, int operate);

}
