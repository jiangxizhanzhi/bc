package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalInstanceStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestInstanceMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestMapper;
import com.cyberpeace.cr.evaluation.service.EvalTestInstanceService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

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
			evalTest.setCurrentInstanceId(null);
			evalTestMapper.updateById(evalTest);
		}
	}

	private void clearInstance(String instanceId) {
		removeById(instanceId);
		evalInstanceStepMapper.deleteByInstanceId(instanceId);
	}



}
