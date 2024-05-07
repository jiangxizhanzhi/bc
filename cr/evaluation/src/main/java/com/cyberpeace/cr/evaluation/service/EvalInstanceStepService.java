package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAllTimeLineRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskStatisticsRes;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTimeNodeRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalInstanceStep;

import java.util.List;

public interface EvalInstanceStepService extends IService<EvalInstanceStep> {

	List<CodeNameRsp> getAllStages(String instanceId);

}
