package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalSceneNodeReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeNodesRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestInstanceMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestMapper;
import com.cyberpeace.cr.evaluation.service.EvalTestInstanceService;
import com.cyberpeace.cr.evaluation.service.context.EvalTestInstanceOpsContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EvalTestInstanceServiceImpl extends ServiceImpl<EvalTestInstanceMapper, EvalTestInstance> implements EvalTestInstanceService {

	@Autowired
	private EvalTestMapper evalTestMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalTestInstanceMapper evalTestInstanceMapper;

	@Autowired
	private EvalInstanceStepMapper evalInstanceStepMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	private static final String PROBES = "probes"; // 探针

	private static final String TOPOLOGY = "topology"; // 拓扑

	private static final String CPCOLLECTION = "cpcollection"; // 采集

	private static final String PLUGINS = "plugins"; // 插件

	private static final String SCENE = "scene"; // 场景

	private static final String ELEMENTS = "elements"; // 元素

	@Override
	public Boolean deleteTestInstance(String instanceId) {

		EvalTestInstance evalTestInstance = evalTestInstanceMapper.selectById(instanceId);
		LocalScene localScene = localSceneService.getById(evalTestInstance.getLocalSceneId());

		//清除未结束的试验实例数据
		if (localScene != null) {
			localSceneService.stopScene(localScene);

			clearInstanceAndScene(localScene.getResourceId(), evalTestInstance.getTestId(), instanceId);
			return true;
		}

		//清除已结束的任务数据
		clearInstance(instanceId);

		EvalTestInstance instance = evalTestInstanceMapper.selectById(instanceId);
		if (instance.getCopyedTopoId() != null) {
			remoteTopoService.deleteById(instance.getCopyedTopoId());
		}
		return true;
	}

	@Override
	public Boolean stopInstance(String testId, String instanceId, String instanceName) {
		EvalTestInstance evalTestInstance = evalTestInstanceMapper.selectById(instanceId);
		LocalScene localScene = localSceneService.getById(evalTestInstance.getLocalSceneId());
		localSceneService.stopScene(localScene);
		evalTestInstance.setEndTime(new Date());
		evalTestInstance.setInstanceName(instanceName);
		evalTestInstance.setLocalSceneId(null);


		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				clearCurrentInstance(testId, instanceId);
				localSceneService.removeById(localScene.getResourceId());
				updateById(evalTestInstance);
			}
		});
		return true;
	}

	@Override
	public EvalTaskTimeNodesRsp getTimeNodes(String instanceId) {
		EvalTaskTimeNodesRsp evalTaskTimeNodesRsp = new EvalTaskTimeNodesRsp();
		evalTaskTimeNodesRsp.setTaskId(instanceId);
		evalTaskTimeNodesRsp.setData(evalInstanceStepMapper.selectTimeNodes(instanceId));
		return evalTaskTimeNodesRsp;
	}

	@Override
	public List<EvalRoomPairRsp> getNodes(String instanceId) {
		EvalTestInstance instance = evalTestInstanceMapper.selectById(instanceId);
		if (instance == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		List<EvalRoomPairRsp> results = Lists.newArrayList();

		JSONObject jsonObject = remoteSceneService.getById(instance.getMpSceneId(), UserInfoContext.getUserContext()
																								   .getUserId());
		JSONArray nodes = jsonObject.getJSONObject(SCENE).getJSONObject(TOPOLOGY).getJSONArray(ELEMENTS);
		nodes.forEach(item -> {
			JSONObject nodeItem = (JSONObject) item;

			EvalRoomPairRsp result = new EvalRoomPairRsp();
			result.setId(nodeItem.getString("id"));
			result.setLabel(nodeItem.getString("name"));
			results.add(result);
		});
		return results;
	}

	@Override
	public Boolean recreateNode(EvalSceneNodeReq nodeReq) {
		EvalTestInstance instance = evalTestInstanceMapper.selectById(nodeReq.getInstanceId());
		Boolean result = localSceneService.recreateNode(instance.getLocalSceneId(),
				nodeReq.getNodeId());
		if (Boolean.FALSE.equals(result)) {
			log.error("failed_to_restart_node: LocalSceneId = [{}]", instance.getLocalSceneId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return true;
	}

	@Override
	public Boolean restartNode(EvalSceneNodeReq nodeReq) {
		EvalTestInstance instance = evalTestInstanceMapper.selectById(nodeReq.getInstanceId());
		Boolean result = localSceneService.restartNode(instance.getLocalSceneId(),
				nodeReq.getNodeId());
		if (Boolean.FALSE.equals(result)) {
			log.error("failed_to_restart_node: LocalSceneId = [{}]", instance.getLocalSceneId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return true;
	}

	@Override
	public EvalTestInstanceOpsContext getEvalTaskOpsContext(String testInstanceId, Boolean validateUser) {

		EvalTestInstanceOpsContext evalTaskOpsContext = new EvalTestInstanceOpsContext();

//		EvalTask evalTask = Boolean.TRUE.equals(validateUser) ? getTask(taskId) : getById(taskId);
		EvalTestInstance evalTestInstance = getById(testInstanceId);

		evalTaskOpsContext.setEvalTestInstance(evalTestInstance);

		evalTaskOpsContext.setLocalScene(localSceneService.getById(evalTestInstance.getLocalSceneId()));

		return evalTaskOpsContext;
	}

	@Override
	public void clearInstanceAndScene(EvalTestInstanceOpsContext evalTestInstanceOpsContext) {

		clearInstanceAndScene(evalTestInstanceOpsContext.getLocalScene().getResourceId(),
				evalTestInstanceOpsContext.getEvalTestInstance().getTestId(),
				evalTestInstanceOpsContext.getLocalScene().getBusinessInstanceId());

	}

	public void clearInstanceAndScene(String localSceneId,String testId, String instanceId) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				localSceneService.removeById(localSceneId);
				clearInstance(instanceId);
				clearCurrentInstance(testId, instanceId);
			}
		});
	}

	public void clearCurrentInstance(String testId, String instanceId) {
		EvalTest evalTest = evalTestMapper.selectById(testId);

		if (StringUtils.equals(instanceId, evalTest.getCurrentInstanceId())) {
			evalTest.setCurrentInstanceId("");
			evalTestMapper.updateById(evalTest);
		}
	}

	private void clearInstance(String instanceId) {
		removeById(instanceId);
		evalInstanceStepMapper.deleteByInstanceId(instanceId);
	}



}
