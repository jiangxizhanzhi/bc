package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrScoreMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.rb.*;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalRuleService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskSlimContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateBaseInfoContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTemplateListQuery;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.util.*;

@Service
@Slf4j
public class EvalTemplateServiceImpl extends ServiceImpl<EvalTemplateMapper, EvalTemplate> implements EvalTemplateService {

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private CrConstantsService crConstantsService;

	@Autowired
	private EvalTemplateMapper evalTemplateMapper;

	@Autowired
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private CrTaskMapper crTaskMapper;
	@Autowired
	private CrEvalTrainTaskMapper evalTrainTaskMapper;
	@Autowired
	private CrTaskSubmitMapper taskSubmitMapper;
	@Autowired
	private CrEvalTaskSubmitMapper evalTaskSubmitMapper;
	@Autowired
	private CrScoreMapper scoreMapper;
	@Autowired
	private CrEvalScoreMapper evalScoreMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private EvalRuleService evalRuleService;
	@Autowired
	private EvalRuleMapper evalRuleMapper;
	@Autowired
	private CrRoleMapper crRoleMapper;
	@Autowired
	private CrEvalRoleMapper evalRoleMapper;
	@Autowired
	private RbRoomMapper rbRoomMapper;
	@Autowired
	private CrEvalRbRoomMapper evalRbRoomMapper;
	@Autowired
	private LocalSceneMapper localSceneMapper;
	@Autowired
	private CrEvalLocalSceneMapper evalLocalSceneMapper;



	@Override
	public EvalTemplate getTemplate(String templateId) {

		EvalTemplate evalTemplate = getById(templateId);

		// 校验模板是否存在
		if (evalTemplate == null) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		// 校验用户权限
		if (!Objects.equals(evalTemplate.getCreateUserId(),
				UserInfoContext.getUserContext().getUserId())) {
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

		return evalTemplate;
	}

	@Override
	public IPage<EvalTemplate> list(@Validated EvalTemplateListQuery evalTemplateListQuery) {

		Page page = evalTemplateListQuery.toPageParam();

		// 根据用户获取模板列表
        return evalTemplateMapper.listEvalTemplates(page, evalTemplateListQuery,
                UserInfoContext.getUserContext().getUserId());
	}

	@Override
	public List<EvalTaskSlimContext> listTask(EvalTemplate evalTemplate, Boolean isFinished) {

		if (isFinished != null && isFinished) {
			return evalTaskMapper.selectSlimTaskListByTemplateIdAndStatus(
					evalTemplate.getResourceId(), EvalTask.Status.FINISHED.getCode());
		}

		List<EvalTaskSlimContext> evalTaskSlimContexts = evalTaskMapper
				.selectSlimTaskListByTemplateIdAndStatus(evalTemplate.getResourceId(), null);

		if (StringUtils.isEmpty(evalTemplate.getCurrentTaskId())) {
			evalTaskSlimContexts.add(new EvalTaskSlimContext());
		}

		return evalTaskSlimContexts;
	}

	@Override
	public String getLogoPath(String attachmentId) {
		Attachment attachment = attachmentService.getById(attachmentId);
		if (attachment != null) {
			return utilService.handlerFileUrl(attachment.getFilePath());
		}
		return "";
	}

	@Override
	public Boolean batchDelete(List<String> templateIds) {

		for (String templateId : templateIds) {
			// 删除task及相关场景
			List<String> evalTaskList = evalTaskMapper.selectIdsByTemplateIdAndStatus(templateId,
					null);
			for (String taskId : evalTaskList) {
				evalTaskService.deleteTask(evalTaskService.getEvalTaskOpsContext(taskId, true));
			}

			EvalTemplate evalTemplate = evalTemplateMapper.selectById(templateId);
			if (StringUtils.isNotBlank(evalTemplate.getTopoId())) {
				try {
					remoteTopoService.deleteById(evalTemplate.getTopoId());
				} catch (Exception e) {
					log.error(
							"Evaluate template delete topo failed! template_id=[{}], topo_id=[{}], catch error:",
							evalTemplate.getResourceId(), evalTemplate.getTopoId(), e);
				}
			}

			evalTemplateMapper.deleteById(templateId);
		}

		return true;

	}

	@Override
	public Boolean validateExistTask(String templateId, EvalTask.Status taskStatus) {

		List<String> evalTaskList = evalTaskMapper.selectIdsByTemplateIdAndStatus(templateId,
				taskStatus.getCode());

		return !evalTaskList.isEmpty();

	}

	@Override
	public Boolean validateBaseInfo(EvalTemplateBaseInfoContext evalTemplateBaseInfoContext) {

		String templateName = evalTemplateBaseInfoContext.getName();
		String templateId = evalTemplateBaseInfoContext.getResourceId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(templateName, CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalTemplateMapper.existByNameAndUserIdAndId(templateName,
				UserInfoContext.getUserContext().getUserId(), templateId)) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述
		CrCommonValidator.validateLength(evalTemplateBaseInfoContext.getDescription(),
				CrCommonConstants.ZERO_STR_LEN, CrCommonConstants.MAX_DESC_LEN,
				CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		return true;
	}

	@Override
	public Boolean validateStepConfig(List<EvalTaskStepConfig> evalTaskStepConfigList) {

		if (evalTaskStepConfigList.isEmpty()) {
			return true;
		}

		List<Integer> stageList = Lists.newArrayList();
		List<String> titleList = Lists.newArrayList();

		// 校验步骤配置是否为空
		for (EvalTaskStepConfig evalTaskStepConfig : evalTaskStepConfigList) {
			evalTaskStepConfig.validate();
			stageList.add(evalTaskStepConfig.getStage());
			titleList.add(evalTaskStepConfig.getTitle());
		}

		// 校验步骤标题是否重复
		if (new HashSet<>(titleList).size() != titleList.size()) {
			throw new BusinessException(CrBusinessErrorCode.TITLE_DUPLICATE);
		}

		// 校验步骤配置的stage
		List<Integer> expectStageList = ListUtil.listRangeInt(1, stageList.size());
		if (new HashSet<>(stageList).size() != expectStageList.size()
				|| !ListUtil.compare2List(stageList, expectStageList)) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}

		return true;
	}

	@Override
	public Boolean add(EvalTemplate evalTemplate) {
		String evalTemplateId = IDGeneratorUtil.getID();
		evalTemplate.setResourceId(evalTemplateId);

		LambdaQueryWrapper<CrTask> taskQuery = new LambdaQueryWrapper<>();
		taskQuery.eq(CrTask::getRoomId, evalTemplate.getRedVsBlueId());
		List<CrTask> taskList = crTaskMapper.selectList(taskQuery);
		List<CrEvalTrainTask> crEvalTrainList = BeanUtils.deepCopyList(taskList, CrEvalTrainTask.class);
		Map<String, String> taskIdMatchMap = new HashMap<>();
		for (CrEvalTrainTask item : crEvalTrainList) {
			item.setEvalTemplateId(evalTemplateId);
			String resourceId = item.getResourceId();
			String taskId = IDGeneratorUtil.getID();
			taskIdMatchMap.put(resourceId, taskId);
			item.setResourceId(taskId);
		}

		LambdaQueryWrapper<CrTaskSubmit> taskSubmitQuery = new LambdaQueryWrapper<>();
		taskSubmitQuery.eq(CrTaskSubmit::getRoomId, evalTemplate.getRedVsBlueId());
		List<CrTaskSubmit> taskSubmitList = taskSubmitMapper.selectList(taskSubmitQuery);
		List<CrEvalTaskSubmit> evalTaskSubmitList = BeanUtils.deepCopyList(taskSubmitList, CrEvalTaskSubmit.class);
		for (CrEvalTaskSubmit item : evalTaskSubmitList) {
			item.setEvalTemplateId(evalTemplateId);
			String newTaskId = taskIdMatchMap.get(item.getTaskId());
			item.setTaskId(newTaskId);
			item.setResourceId(null);
		}

		LambdaQueryWrapper<CrScore> scoreQuery = new LambdaQueryWrapper<>();
		scoreQuery.eq(CrScore::getRoomId, evalTemplate.getRedVsBlueId());
		List<CrScore> scoreList = scoreMapper.selectList(scoreQuery);
		List<CrEvalScore> evalScoreList = BeanUtils.deepCopyList(scoreList, CrEvalScore.class);
		for (CrEvalScore item : evalScoreList) {
			item.setEvalTemplateId(evalTemplateId);
			item.setResourceId(null);
		}

		List<CrRole> roleList = null;
		RbRoom rbRoom = null;
		LocalScene localScene = null;

		LambdaQueryWrapper<CrEvalRole> evalRoleQuery = new LambdaQueryWrapper<>();
		evalRoleQuery.eq(CrEvalRole::getRoomId, evalTemplate.getRedVsBlueId());
		Integer existRole = evalRoleMapper.selectCount(evalRoleQuery);
		if (existRole == 0) {
			LambdaQueryWrapper<CrRole> roleQuery = new LambdaQueryWrapper<>();
			roleQuery.eq(CrRole::getRoomId, evalTemplate.getRedVsBlueId());
			roleList = crRoleMapper.selectList(roleQuery);
		}

		CrEvalRbRoom existRbRoom = evalRbRoomMapper.selectById(evalTemplate.getRedVsBlueId());
		if (Objects.isNull(existRbRoom)) {
			rbRoom = rbRoomMapper.selectById(evalTemplate.getRedVsBlueId());
			CrEvalLocalScene evalLocalScene = evalLocalSceneMapper.selectById(rbRoom.getLocalSceneId());
			if (Objects.isNull(evalLocalScene)) {
				localScene = localSceneMapper.selectById(rbRoom.getLocalSceneId());
			}
		}
		List<CrEvalRole> addRoleList = existRole == 0 ? BeanUtils.deepCopyList(roleList, CrEvalRole.class) : null;
		CrEvalRbRoom addRbRoom = Objects.isNull(existRbRoom) ? BeanUtils.copy(rbRoom, CrEvalRbRoom.class) : null;
		CrEvalLocalScene addLocalScene = Objects.nonNull(localScene) ? BeanUtils.copy(localScene, CrEvalLocalScene.class) : null;

		//初始化评估规则数据
		List<EvalRule> initialEvalRuleList = evalRuleService.getInitialData(evalTemplateId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				save(evalTemplate);
				if (!CollectionUtils.isEmpty(crEvalTrainList)) {
					evalTrainTaskMapper.batchInsert(crEvalTrainList);
				}
				if (!CollectionUtils.isEmpty(evalTaskSubmitList)) {
					evalTaskSubmitMapper.batchInsert(evalTaskSubmitList);
				}
				if (!CollectionUtils.isEmpty(evalScoreList)) {
					evalScoreMapper.batchInsert(evalScoreList);
				}

				evalRuleMapper.batchInsert(initialEvalRuleList);

				if (!CollectionUtils.isEmpty(addRoleList)) {
					evalRoleMapper.batchInsert(addRoleList);
				}

				if (Objects.nonNull(addRbRoom)) {
					evalRbRoomMapper.insert(addRbRoom);
				}

				if (Objects.nonNull(addLocalScene)) {
					evalLocalSceneMapper.insert(addLocalScene);
				}
			}
		});
		return true;
	}

}