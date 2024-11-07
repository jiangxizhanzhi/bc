package com.cyberpeace.cr.evaluation.service.impl;

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
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 评估任务Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class EvalTaskServiceImpl extends ServiceImpl<EvalTaskMapper, EvalTask> implements EvalTaskService {

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 评估任务Mapper
	 */
	@Autowired
	private EvalTaskMapper evalTaskMapper;

	/**
	 * 评估任务步Service
	 */
	@Autowired
	private EvalTaskStepService evalTaskStepService;

	/**
	 * 评估模板Mapper
	 */
	@Autowired
	private EvalTemplateMapper evalTemplateMapper;

	/**
	 * 评估场景Service
	 */
	@Autowired
	private EvalSceneService evalSceneService;

	/**
	 * 评估记录Service
	 */
	@Autowired
	private EvalRecordService evalRecordService;

	/**
	 * 获取任务
	 *
	 * @param taskId 任务id
	 * @return {@code EvalTask }
	 */
	@Override
	public EvalTask getTask(String taskId) {

		EvalTask evalTask = getById(taskId);

		// 校验任务是否存在
		if (evalTask == null) {
			log.error("The evaluation task does not exist! taskId = [{}]", taskId);
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_EXIST);
		}

		// 校验用户权限
		if (!Objects.equals(evalTask.getCreateUserId(),
				UserInfoContext.getUserContext().getUserId())) {
			log.error("Permission denied! CreateUserId = [{}], UserId = [{}]", evalTask.getCreateUserId(), UserInfoContext.getUserContext().getUserId());
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

		return evalTask;
	}

	/**
	 * 获取积极任务
	 *
	 * @param taskId 任务id
	 * @return {@code EvalTask }
	 */
	@Override
	public EvalTask getActiveTask(String taskId) {

		EvalTask evalTask = getTask(taskId);

		if (evalTask.getEndTime() != null) {
			// 校验任务状态
			log.error("The evaluation task is not running! taskId = [{}]", taskId);
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_RUNNING);
		}

		return evalTask;
	}

	/**
	 * 获取评估任务老年 退休金 计划上下文
	 *
	 * @param taskId       任务id
	 * @param validateUser 验证用户
	 * @return {@code EvalTaskOpsContext }
	 */
	@Override
	public EvalTaskOpsContext getEvalTaskOpsContext(String taskId, Boolean validateUser) {

		EvalTaskOpsContext evalTaskOpsContext = new EvalTaskOpsContext();

		EvalTask evalTask = validateUser ? getTask(taskId) : getById(taskId);

		evalTaskOpsContext.setEvalTask(evalTask);

		evalTaskOpsContext.setLocalScene(localSceneService.getById(evalTask.getLocalSceneId()));

		return evalTaskOpsContext;
	}

	/**
	 * 启动任务
	 *
	 * @param evalTemplate eval 模板
	 * @return {@code String }
	 */
	@Override
	public String startTask(EvalTemplate evalTemplate) {

		String templateId = evalTemplate.getResourceId();

		// 构建任务对象
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

		// 构建本地scene对象
		LocalScene localScene = createLocalScene(evalTask);

		// 启动中台场景
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
			// 更新本地数据库数据
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					localSceneService.save(localScene);
					save(evalTask);
					evalTaskStepService.saveBatch(evalTaskSteps);
					evalTemplateMapper.updateById(evalTemplate);
				}
			});

		} catch (Exception e) {
			// 本地保存数据错误，删除已启动的远程场景
			localSceneService.stopScene(localScene);
			log.error("Failed to save local data:", e);
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}

		return evalTask.getResourceId();

	}

	/**
	 * 创造当地场景
	 *
	 * @param evalTask 评估任务
	 * @return {@code LocalScene }
	 */
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
		String appResourceId = evalTask.getTemplateId();
		localScene.setMeta(
				JSON.toJSONString(LocalSceneMeta.builder().appResourceId(appResourceId).build()));
		localScene.setRecord(evalTask.getEnableRecording());
		return localScene;
	}

	/**
	 * 停止任务
	 *
	 * @param evalTaskOpsContext eval task ops 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean stopTask(EvalTaskOpsContext evalTaskOpsContext) {

		// 关闭场景
		evalSceneService.stopScene(evalTaskOpsContext.getLocalScene());

		// 处理数据库数据
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				clearTaskScene(evalTaskOpsContext);
				clearCurrentTask(evalTaskOpsContext.getEvalTask().getTemplateId(),
						evalTaskOpsContext.getEvalTask().getResourceId());
			}
		});

		return true;
	}

	/**
	 * 删除任务
	 *
	 * @param evalTaskOpsContext eval task ops 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean deleteTask(EvalTaskOpsContext evalTaskOpsContext) {

		// 清除未结束的任务数据
		if (evalTaskOpsContext.getLocalScene() != null) {

			// 关闭场景
			evalSceneService.stopScene(evalTaskOpsContext.getLocalScene());

			// 清除任务及其场景数据
			clearTaskAndScene(evalTaskOpsContext);
			return true;

		}

		// 清除已结束的任务数据
		String taskId = evalTaskOpsContext.getEvalTask().getResourceId();
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				clearTask(taskId);
				evalRecordService.deleteRecord(Lists.newArrayList(taskId));
			}
		});

		if (StringUtils.isNotBlank(evalTaskOpsContext.getEvalTask().getCopyedTopoId())) {
			try {
				remoteTopoService.deleteById(evalTaskOpsContext.getEvalTask().getCopyedTopoId());
			} catch (Exception e) {
				log.error(
						"Evaluation delete topo failed! eval_task_id=[{}], topo_id=[{}], catch error:",
						evalTaskOpsContext.getEvalTask().getResourceId(),
						evalTaskOpsContext.getEvalTask().getCopyedTopoId(), e);
			}
		}

		return true;
	}

	/**
	 * 清楚任务场景
	 *
	 * @param evalTaskOpsContext eval task ops 上下文
	 */
	@Override
	public void clearTaskScene(EvalTaskOpsContext evalTaskOpsContext) {

		EvalTask evalTask = evalTaskOpsContext.getEvalTask();

		evalTask.setEndTime(new Date());
		evalTask.setLocalSceneId("");

		localSceneService.removeById(evalTaskOpsContext.getLocalScene());
		updateById(evalTask);

	}

	/**
	 * 清除任务及其步骤数据
	 *
	 * @param taskId 任务id
	 */
	private void clearTask(String taskId) {
		evalTaskStepService.removeByTaskId(taskId);
		removeById(taskId);
	}

	/**
	 * 清楚任务和场景
	 *
	 * @param evalTaskOpsContext eval task ops 上下文
	 */
	@Override
	public void clearTaskAndScene(EvalTaskOpsContext evalTaskOpsContext) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				localSceneService.removeById(evalTaskOpsContext.getLocalScene());
				clearTask(evalTaskOpsContext.getEvalTask().getResourceId());
				clearCurrentTask(evalTaskOpsContext.getEvalTask().getTemplateId(),
						evalTaskOpsContext.getEvalTask().getResourceId());
			}
		});

	}

	/**
	 * 清除当前任务
	 *
	 * @param templateId 模板id
	 * @param taskId     任务id
	 */
	@Override
	public void clearCurrentTask(String templateId, String taskId) {
		EvalTemplate evalTemplate = evalTemplateMapper.selectById(templateId);

		if (StringUtils.equals(taskId, evalTemplate.getCurrentTaskId())) {
			evalTemplate.setCurrentTaskId("");
			evalTemplateMapper.updateById(evalTemplate);
		}
	}

	/**
	 * 验证名称
	 *
	 * @param taskName   任务名称
	 * @param templateId 模板id
	 * @param taskId     任务id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean validateName(String taskName, String templateId, String taskId) {

		// 校验名称长度
		CrCommonValidator.validateNameLen(taskName, CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalTaskMapper.existByNameAndTemplateId(taskName, templateId, taskId)) {
			log.error("Name duplicate! taskName = [{}], templateId = [{}], taskId = [{}]", taskName, templateId, taskId);
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		return true;
	}

}