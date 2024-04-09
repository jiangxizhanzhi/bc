package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalGuideScheme;

import java.util.List;

public interface EvalGuideService extends IService<EvalGuideScheme> {

	String addSituationGuide(EvalGuideAddReq req);

	void modifySituationGuide(EvalGuideModifyReq req);

	List<EvalGuideListRsp> getGuideList(String testId);

	void runGuideScheme(String guideId);

	void deleteGuideScheme(String guideId);

	List<EvalStepGuideListRsp> getStepGuideList(String testId);

	EvalStepGuideDetailRsp getGuideDetail(String stepId);

	void deleteByStepId(String stepId);

	List<EvalGuideAttackEnumRsp> getAttackEnum();

	EvalTestElementAreaRsp getElementArea(String testId);

	void pushDataToRoomByScheme(Boolean isInstance, String sceneId, EvalGuideScheme evalGuideScheme);
}
