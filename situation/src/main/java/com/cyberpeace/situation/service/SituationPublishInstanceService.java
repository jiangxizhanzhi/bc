package com.cyberpeace.situation.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.integratation.model.situation.BcContext;
import com.cyberpeace.situation.constant.dashboard.PublicStatus;
import com.cyberpeace.situation.controller.req.ReplayInstanceReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;
import com.cyberpeace.situation.model.outer.Command;

public interface SituationPublishInstanceService extends IService<SituationPublishInstance> {

	SituationPublishInstance selectReleasedXdDashboardBySubjectId(String taskId, String subjectId);

	SituationPublishInstance addReplayInstance(ReplayInstanceReq replayInstanceReq);

	SituationPublishInstance updateReplayInstance(ReplayInstanceReq replayInstanceReq);

	SituationPublishInstance
			insertOne(SituationPublishInstanceAddReq situationPublishInstanceAddReq, Boolean isXd);

	SituationPublishInstance
			updateOne(SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq);

	/**
	 * 清除未使用的态势
	 */
	void deleteUnusedSituation();

	/**
	 * 分页查询
	 *
	 * @return
	 */
	ListRsp<SituationPublishInstancePageRes> getSituationPublishInstancePage(
			SituationPublishInstanceFilterReq situationPublishInstanceFilterReq);

	String getEnvIdByDashboardId(String dashboardId);

	SituationPublishInstance createBcInstance(BcContext bcContext);

	Boolean changeInstancePublicStatus(String dashboardId, PublicStatus publicStatus);

	Boolean changeInstancePublicStatus(SituationPublishInstance situationPublishInstance,
			PublicStatus publicStatus);

	void updatePosition(String subRangeId, String taskId, String subjectId, String envId);

	Command getTemplateList(JSONObject jsonObject);

	Command addDashboard(JSONObject jsonObject);

	Command checkDashboard(JSONObject jsonObject);

	Command deleteDashboard(JSONObject jsonObject);
}
