package com.cyberpeace.cr.evaluation.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskStepMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteTopoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalTaskServiceImpl extends ServiceImpl<EvalTaskMapper, EvalTask> implements EvalTaskService {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalTaskStepService evalTaskStepService;

	@Autowired
	private EvalTaskStepMapper evalTaskStepMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Autowired
	private EvalTemplateMapper evalTemplateMapper;

	@Override
	public EvalTask getTask(String taskId) {

		EvalTask evalTask = getById(taskId);
		if (evalTask == null) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_EXIST);
		} else if (!Objects.equals(evalTask.getCreateUserId(),
				UserInfoContext.getUserContext().getUserId())) {
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		} else {
			return evalTask;
		}
	}

	@Override
	public Boolean startTask(EvalTemplate evalTemplate) {

		String templateId = evalTemplate.getResourceId();

		// 校验模板下任务状态 todo 同一模板不允许启动多个场景
		LocalScene localSceneExisted = localSceneService.getByBusinessInstanceId(templateId);

		if (localSceneExisted != null && StringUtils.isNotBlank(localSceneExisted.getMpSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
		}

		if (localSceneExisted != null) {
			localSceneService.removeById(localSceneExisted.getResourceId());
		}

		// 构建task对象
		EvalTask evalTask = new EvalTask();
		evalTask.setTemplateId(templateId);
		evalTask.setCopyedTopoId(remoteTopoService.copyTopo(evalTemplate.getTopoId()));
		evalTask.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalTask.setEnableRecording(evalTemplate.getEnableRecording());
		evalTask.setTemplateName(evalTemplate.getName());
		evalTask.setResourceId(IDGeneratorUtil.getID());

		// 构建步骤对象
		List<EvalTaskStep> evalTaskSteps = Lists.newArrayList();
		List<EvalTaskStepConfig> evalTaskStepConfigList = JsonUtil
				.toJsonObjectArray(evalTemplate.getStepConfig(), EvalTaskStepConfig.class);
		for (EvalTaskStepConfig evalTaskStepConfig : evalTaskStepConfigList) {
			EvalTaskStep evalTaskStep = BeanUtils.copy(evalTaskStepConfig, EvalTaskStep.class);
			evalTaskStep.setTaskId(evalTask.getResourceId());
			evalTaskStep.setType(EvalTaskStep.Type.MANUAL.getCode());
			evalTaskSteps.add(evalTaskStep);
		}

		// 3、构建本地scene对象
		LocalScene localScene = createLocalScene(evalTask);

		// 4、启动mp场景
		String sceneId = localSceneService.startScene(localScene);
		localScene.setMpSceneId(sceneId);
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		Date startTime = new Date();
		localScene.setStartTime(startTime);
		evalTask.setStartTime(startTime);
		evalTask.setLocalSceneId(localScene.getResourceId());
		evalTask.setMpSceneId(sceneId);

		// 更新模板信息
		evalTemplate.setCurrentTaskId(evalTask.getResourceId());

		try {
			// 5、更新本地数据库数据
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					localSceneService.save(localScene);
					save(evalTask);
					evalTaskStepService.saveBatch(evalTaskSteps);
					// evalTemplateMapper.updateById(evalTemplate);
				}
			});

			// 5.1 本地保存数据错误，删除已启动的远程场景
		} catch (Exception e) {
			localSceneService.stopScene(localScene);
			log.error("Failed to save local data:", e);
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}

		return true;

	}

	private LocalScene createLocalScene(EvalTask evalTask) {

		LocalScene localScene = new LocalScene();

		localScene.setBusinessCode(LocalScene.BusinessCode.RANGE_EVALUATION.getCode());
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setBusinessInstanceId(evalTask.getResourceId());
		localScene.setTopoId(evalTask.getCopyedTopoId());
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.SINGLE.getCode());
		localScene.setSceneName(evalTask.getTemplateName());
		localScene.setMeta(JSON.toJSONString(
				LocalSceneMeta.builder().appResourceId(evalTask.getResourceId()).build()));
		localScene.setRecord(evalTask.getEnableRecording());
		return localScene;
	}

	@Override
	public Boolean stopTask(EvalTask evalTask) {

		LocalScene localScene = localSceneService.getById(evalTask.getLocalSceneId());

		Boolean stopResult = localSceneService.stopScene(localScene);

		// 停止场景失败时的处理 todo
		if (!stopResult) {
			return false;
		}

		evalTask.setEndTime(new Date());
		evalTask.setLocalSceneId("");

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				localSceneService.removeById(localScene);
				updateById(evalTask);
			}
		});

		return true;
	}

	@Override
	public Boolean deleteTask(EvalTask evalTask) {

		// 关闭任务关联场景
		stopTask(evalTask);

		String taskId = evalTask.getResourceId();

		// 查询任务相关的步骤记录
		List<String> stepList = evalTaskStepMapper.selectStepIdsByTaskId(taskId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				evalTaskStepService.removeByIds(stepList);
				removeById(taskId);
			}
		});

		return true;
	}

	@Override
	public Boolean validateName(String taskName, String templateId, String taskId) {

		// 校验名称长度
		CrCommonValidator.validateNameLen(taskName, CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalTaskMapper.existByNameAndTemplateId(taskName, templateId, taskId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		return true;
	}

}