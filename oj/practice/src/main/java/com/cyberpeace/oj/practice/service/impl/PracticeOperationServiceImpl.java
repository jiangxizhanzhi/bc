package com.cyberpeace.oj.practice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.AttachmentMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import com.cyberpeace.oj.practice.service.PracticeEvaluateHandler;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PracticeOperationServiceImpl extends ServiceImpl<PracticeOperationMapper, PracticeOperation> implements PracticeOperationService {

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Resource(name = "operation")
	private PracticeEvaluateHandler practiceEvaluateHandler;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Override
	public Integer addPracticeOperation(PracticeOperation practiceOperation,
			JSONObject ojSceneConfig, AuthContext authContext) {

		validatePracticeSet(practiceOperation.getPracticeSetId(), authContext);
		validateCategory(practiceOperation.getCategoryId());

		practiceOperation.setBuiltin(CommonConstant.FALSE);

		if (null != ojSceneConfig) {
			String topoId = remoteTopoService.create(
					JsonUtil.toJsonObject(JsonUtil.toJsonString(ojSceneConfig), SceneConfig.class));
			practiceOperation.setTopoId(topoId);
		}

		return practiceOperationMapper.insert(practiceOperation);
	}

	/**
	 * 习题集校验
	 * @param practiceSetId
	 * @param authContext
	 * @return
	 */
	private PracticeSet validatePracticeSet(String practiceSetId, AuthContext authContext) {

		List<String> authSetIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authSetIds)) {
			log.error("When add operation practice, all practice set no permission!");
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		if (!authSetIds.contains(practiceSetId)) {
			log.error(
					"When add operation practice, practice set no permission! practice_set_id=[{}]",
					practiceSetId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(practiceSetId);
		if (practiceSet.getPracticeType().intValue() != PracticeSet.PracticeType.OPERATION.getCode()
				.intValue()) {
			log.error(
					"practice set type is inconsistent with the operation practice type! practice_set_id=[{}]",
					practiceSetId);
			throw new InvalidParamException("习题集类型与习题类型不一致！");
		}

		return practiceSet;
	}

	/**
	 * 习题分类校验
	 * @param categoryId
	 * @return
	 */
	private PracticeCategory validateCategory(String categoryId) {

		PracticeCategory category = practiceCategoryMapper.selectById(categoryId);
		if (null == category) {
			throw new InvalidParamException("习题分类不存在！");
		}

		if (category.getPracticeType().intValue() != PracticeCategory.PracticeType.OPERATION
				.getCode().intValue()) {
			log.error(
					"practice set type is inconsistent with the operation practice type! practice_category_id=[{}]",
					categoryId);
			throw new InvalidParamException("习题分类与习题类型不一致！");
		}

		return category;
	}

	@Override
	public Integer updateOperation(PracticeOperation updateOperation, JSONObject ojSceneConfig) {

		if (null != ojSceneConfig) {
			// TODO 场景修改 修改失败 ？继续修改习题信息 ： 修改失败
			// remoteTopoService.create(
			// JsonUtil.toJsonObject(JsonUtil.toJsonString(ojSceneConfig),
			// SceneConfig.class));
		}

		return practiceOperationMapper.updateById(updateOperation);
	}

	@Override
	public OperationBo selectById(String resourceId) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("Operation practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}
		OperationBo operationBo = BeanUtils.copy(operation, OperationBo.class);

		if (operation.getDynamicEnv() && StringUtils.isNotBlank(operation.getTopoId())) {
			SceneConfig sceneConfig = remoteTopoService.getById(operation.getTopoId());
			operationBo.setOjSceneConfig(JsonUtil.toJsonString(sceneConfig));
		}

		// 获取附件信息
		Attachment attachment = attachmentMapper.selectById(operation.getAttachmentId());
		operationBo.setFileName(attachment.getFileName());
		operationBo.setFileUrl(attachment.getFilePath());
		return operationBo;
	}

	@Override
	public WebOperationBo selectById(String resourceId, String userId) {

		OperationBo operationBo = selectById(resourceId);
		WebOperationBo webOperationBo = BeanUtils.copy(operationBo, WebOperationBo.class);

		// 收藏信息 解题状态查询
		Boolean result = resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.OPERATION.getCode(), resourceId);

		webOperationBo.setIsCollection(result);

		// 解题状态
		PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.OPERATION.getCode(), resourceId);
		if (null == practiceSolved) {
			webOperationBo.setAnswer(null);
		} else {
			if (practiceSolved.getSolved()) {
				webOperationBo.setIsSolved(practiceSolved.getSolved());
				webOperationBo.setSubmitAnswer(practiceSolved.getSubmitAnswer());
			} else {
				webOperationBo.setIsSolved(practiceSolved.getSolved());
				webOperationBo.setAnswer(null);
			}
		}

		// 场景数据
		if (operationBo.getDynamicEnv() && StringUtils.isNotBlank(operationBo.getTopoId())) {
			OjLocalScene localScene = operationBo.getSceneShareMode()
					.intValue() == SceneShare.PRIVATE.getCode()
							? ojLocalSceneMapper.selectByUserAndBid(userId, resourceId,
									CommonConstant.TRUE)
							: ojLocalSceneMapper.selectByBidAndShared(resourceId,
									CommonConstant.TRUE, CommonConstant.TRUE);

			if (null != localScene) {
				// todo 场景数据获取
				webOperationBo.setSceneData(new JSONObject());
			}

		}

		return webOperationBo;
	}

	@Override
	public IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery,
			AuthContext authContext) {

		// 过滤习题集
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		OperationListQuery operationListQuery = BeanUtils.copy(listQuery, OperationListQuery.class);

		if (CollectionUtils.isEmpty(authIds)
				|| (StringUtils.isNotBlank(listQuery.getPracticeSetId())
						&& !authIds.contains(listQuery.getPracticeSetId()))) {
			log.error("The current practice set no permission！practice_set_id=[{}]",
					listQuery.getPracticeSetId());
			return null;
		}

		// 习题集过滤
		if (StringUtils.isBlank(listQuery.getPracticeSetId())
				&& CollectionUtils.isNotEmpty(authIds)) {
			List<String> operationIds = practiceOperationMapper.selectBySetIds(authIds,
					authContext.getIsWeb());
			if (CollectionUtils.isEmpty(operationIds)) {
				return null;
			}
			operationListQuery.setResourceIds(operationIds);
		}

		// 根据解题状态过滤
		if (null != listQuery.getSolved() && listQuery.getSolved()) {
			List<String> operationIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.OPERATION.getCode(),
					listQuery.getSolved());
			if (CollectionUtils.isEmpty(operationIds)) {
				return null;
			}
			operationListQuery.getResourceIds().addAll(operationIds);
			operationListQuery
					.setResourceIds(operationIds.stream().distinct().collect(Collectors.toList()));
		}

		IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page,
				operationListQuery, authContext.getIsWeb());

		IPage<AdminOperationListBo> listBoIPage = iPage.convert(practiceOperation -> {
			AdminOperationListBo operationListBo = BeanUtils.copy(practiceOperation,
					AdminOperationListBo.class);
			if (authContext.getIsWeb()) {
				if (null != listQuery.getSolved()) {
					operationListBo.setSolved(listQuery.getSolved());
				} else {
					PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(
							authContext.getUserId(),
							PracticeCategory.PracticeType.OPERATION.getCode(),
							practiceOperation.getResourceId());
					operationListBo.setSolved(null != practiceSolved ? practiceSolved.getSolved()
							: CommonConstant.FALSE);
				}
			}

			PracticeSet practiceSet = practiceSetMapper
					.selectById(practiceOperation.getPracticeSetId());
			operationListBo.setPracticeSetName(null != practiceSet ? practiceSet.getName() : "");
			UserInfo userInfo = remoteUserService.getById(practiceOperation.getCreateUserId());

			operationListBo.setCreateUserName(userInfo.getUserName());
			return operationListBo;
		});

		return listBoIPage;
	}

	@Override
	public EvaluateResult submitAnswer(String userId, String resourceId, String submitAnswer) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("Operation practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		PracticeSolved solved = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.OPERATION.getCode(), resourceId);
		if (solved != null && solved.getSolved()) {
			EvaluateResult evaluateResult = BeanUtils.copy(solved, EvaluateResult.class);
			return evaluateResult;
		}

		EvaluateResult evaluateResult = practiceEvaluateHandler.evaluateByBusiness(userId,
				operation, submitAnswer);

		// 将评估记录存储
		PracticeSolved practiceSolved = BeanUtils.copy(evaluateResult, PracticeSolved.class);
		practiceSolved.setPracticeId(operation.getResourceId());
		practiceSolved.setSubmitUserId(userId);
		practiceSolved.setPracticeType(PracticeCategory.PracticeType.OPERATION.getCode());
		practiceSolved.setSubmitAnswer(submitAnswer);

		practiceSolved.setCollection(resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.OPERATION.getCode(), operation.getResourceId()));
		practiceSolvedMapper.insert(practiceSolved);

		return evaluateResult;
	}
}
