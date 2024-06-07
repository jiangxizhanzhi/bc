package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAllTimeLineRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCodeStageRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskStatisticsRes;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTimeNodeRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalInstanceStep;

import java.util.List;

public interface EvalInstanceStepService extends IService<EvalInstanceStep> {

	List<EvalCodeStageRsp> getAllStages(String instanceId);

	List<EvalTimeNodeRsp> getPlaybackTime(String instanceId);

	EvalAllTimeLineRsp getTimeLine(String instanceId);

	EvalTaskStatisticsRes getTaskStatistics(String instanceId);

	List<EvalCodeStageRsp> getAllStagesBySceneId(String sceneId);

}
