package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestStepListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalStep;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public interface EvalStepService extends IService<EvalStep> {

	Map<String, String> createByTemplate(List<String> templateIdList, String testId);

	List<EvalStep> selectByTestId(String testId);

	List<EvalTestStepListRsp> getSteps(String testId);

	List<EvalTestStepListRsp> getInstanceSteps(String instanceId);

	Boolean startStep(String stepId);

	Boolean finishStep(String stepId);

	void deleteLoad(EvalStepLoadDeleteReq evalStepLoadDeleteReq);

	void addLoad(EvalStepLoadAddReq addReq);

	String addLoadExecutionResult(EvalLoadResultAddReq addReq);

	void pushLoadResult(String sceneId, Integer interval, String endTime);

	void buildAndSendSituationAttackData(String sceneId, String nodeName, String sourceId, String targetId);

	void packageAndSendAttackData(String scene, String srcNode, String targetId);

	void updateLoadExecutionResultByPk(EvalMpDevopsLoadSceneLogAddReq addReq);

	JSONObject deliveryLoadFile(DeliveryLoadFileReq deliveryLoadFileReq);

	JSONArray getDeliveryNodes(String sceneId);
}
