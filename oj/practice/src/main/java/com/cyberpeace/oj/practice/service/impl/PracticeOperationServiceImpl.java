package com.cyberpeace.oj.practice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.AttachmentMapper;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
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
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PracticeOperationServiceImpl extends ServiceImpl<PracticeOperationMapper, PracticeOperation> implements PracticeOperationService {

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private AttachmentMapper attachmentMapper;

	@Autowired
	private AttachmentService attachmentService;

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

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Override
	public Integer addPracticeOperation(PracticeOperation practiceOperation,
			JSONObject ojSceneConfig, AuthContext authContext) {

		validatePracticeSet(practiceOperation.getPracticeSetId(), authContext);
		validateCategory(practiceOperation.getCategoryId());

		practiceOperation.setBuiltin(CommonConstant.FALSE);

		if (practiceOperation.getDynamicEnv() && null != ojSceneConfig) {
			String topoId = remoteTopoService.save(ojSceneConfig);
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

		PracticeOperation practiceOperation = practiceOperationMapper
				.selectById(updateOperation.getResourceId());

		if (updateOperation.getDynamicEnv() && null != ojSceneConfig) {
			remoteTopoService.save(ojSceneConfig);
		}

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				// 附件删除更新
				if (StringUtils.isBlank(updateOperation.getAttachmentId())) {
					attachmentService.deleteAttachment(practiceOperation.getAttachmentId());
					updateOperation.setAttachmentId("");
				}

				return practiceOperationMapper.updateById(updateOperation);
			}
		});

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
			JSONObject sceneConfig = remoteTopoService.getTopoById(operation.getTopoId());
			operationBo.setSceneConfigData(sceneConfig);
		}

		// 获取附件信息
		Attachment attachment = attachmentMapper.selectById(operation.getAttachmentId());
		if (null != attachment) {
			operationBo.setFileName(attachment.getFileName());
			operationBo.setFileUrl(attachment.getFilePath());
		}
		return operationBo;
	}

	@Override
	public WebOperationBo selectById(String resourceId, AuthContext authContext) {

		OperationBo operationBo = selectById(resourceId);
		WebOperationBo webOperationBo = BeanUtils.copy(operationBo, WebOperationBo.class);

		// 收藏信息 解题状态查询
		Boolean result = resourceCollectionService.selectByUnique(authContext.getUserId(),
				ResourceCollection.BusinessType.OPERATION.getCode(), resourceId);

		webOperationBo.setIsCollection(result);

		// 解题状态
		PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(
				authContext.getUserId(), PracticeCategory.PracticeType.OPERATION.getCode(),
				resourceId);
		if (null == practiceSolved) {
			webOperationBo.setAnswer(null);
			PracticeSolvedDetailBo detailBo = new PracticeSolvedDetailBo();
			detailBo.setIsSolved(CommonConstant.FALSE);
			webOperationBo.setResolvedDetail(detailBo);
		} else {
			PracticeSolvedDetailBo detailBo = new PracticeSolvedDetailBo();
			if (practiceSolved.getSolved()) {
				detailBo.setCorrectAnswer(operationBo.getAnswer());
				detailBo.setIsSolved(practiceSolved.getSolved());
				detailBo.setSubmitAnswer(practiceSolved.getSubmitAnswer());
			} else {
				detailBo.setIsSolved(practiceSolved.getSolved());
				webOperationBo.setAnswer(null);
			}
			webOperationBo.setResolvedDetail(detailBo);
		}

		// 场景数据
		if (operationBo.getDynamicEnv() && StringUtils.isNotBlank(operationBo.getTopoId())) {
			JSONObject sceneConfig = remoteTopoService.getTopoById(operationBo.getTopoId());
			webOperationBo.setSceneConfigData(sceneConfig);

			OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(
					operationBo.getResourceId(), operationBo.getSceneShareMode(), authContext);

			if (null != ojLocalScene) {
				LocalScene sceneLocal = localSceneMapper
						.getByBusinessInstanceId(ojLocalScene.getResourceId());
				if (null != sceneLocal && StringUtils.isNotBlank(sceneLocal.getMpSceneId())) {
					JSONObject jsonObject = remoteSceneService.getById(sceneLocal.getMpSceneId(),
							authContext.getUserId());
					webOperationBo.setSceneData(jsonObject);
				}
			}
		}

		// 题解数据获取
		if (!webOperationBo.getWriteupPublic()) {
			webOperationBo.setWriteup(null);
		}
		return webOperationBo;
	}

	@Override
	public IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery,
			AuthContext authContext) {

		// 过滤无权限习题集
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authIds)) {
			return null;
		}

		// 过滤非公开习题集
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.OPERATION.getCode(), authContext.getIsWeb());
		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
		authIds.retainAll(setIds);
		if (CollectionUtils.isEmpty(authIds)) {
			return null;
		}

		if (StringUtils.isNotBlank(listQuery.getPracticeSetId())
				&& !authIds.contains(listQuery.getPracticeSetId())) {
			return null;
		}

		// 根据习题集过滤习题编号
		OperationListQuery operationListQuery = BeanUtils.copy(listQuery, OperationListQuery.class);
		List<String> operationIds = practiceOperationMapper.selectBySetIds(authIds,
				authContext.getIsWeb());
		if (CollectionUtils.isEmpty(operationIds)) {
			return null;
		}
		operationListQuery.setResourceIds(operationIds);

		// 根据解题状态过滤
		if (authContext.getIsWeb() && null != listQuery.getSolved()) {

			// 查询已解决习题编号
			List<String> solvedIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.OPERATION.getCode(),
					CommonConstant.TRUE);

			// 正确取交集
			if (listQuery.getSolved()) {
				operationListQuery.getResourceIds().retainAll(solvedIds);
			} else {
				operationListQuery.getResourceIds().removeAll(solvedIds);
			}

			if (CollectionUtils.isEmpty(operationListQuery.getResourceIds())) {
				return null;
			}
		}

		IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page,
				operationListQuery, authContext.getIsWeb());

		IPage<AdminOperationListBo> listBoIPage = iPage.convert(practiceOperation -> {
			AdminOperationListBo operationListBo = BeanUtils.copy(practiceOperation,
					AdminOperationListBo.class);

			operationListBo.setHasSceneData(practiceOperation.getDynamicEnv()
					&& StringUtils.isNotBlank(practiceOperation.getTopoId()));

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
			operationListBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");
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
		try {
			practiceSolvedMapper.insert(practiceSolved);
		} catch (DuplicateKeyException e) {
			practiceSolvedMapper.updateSolved(practiceSolved);
		}

		return evaluateResult;
	}

	@Override
	public List<String> selectPracticeIds(AuthContext authContext) {
		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return Lists.newArrayList();
		}

		return practiceOperationMapper.selectBySetIds(setIds, authContext.getIsWeb());
	}

	@Override
	public JSONObject startScene(String resourceId, AuthContext authContext) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("StartScene operation practice not found! operation_practice_id=[{}]");
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		if (!operation.getDynamicEnv() || StringUtils.isBlank(operation.getTopoId())) {
			log.error("StartScene operation is not dynamic env! dynamic_env=[{}], topo_id=[{}]",
					operation.getDynamicEnv(), operation.getTopoId());
			throw new InvalidParamException("非动态环境，无法进行场景启动！");
		}

		// 判断是否存在已启动场景
		OjLocalScene existOjLocalScene = ojLocalSceneService.selectOjLocalScene(
				operation.getResourceId(), operation.getSceneShareMode(), authContext);

		if (null != existOjLocalScene) {
			LocalScene localScene = localSceneMapper
					.selectById(existOjLocalScene.getLocalSceneId());
			if (null != localScene) {
				return remoteSceneService.getById(localScene.getMpSceneId(),
						authContext.getUserId());
			} else {
				log.error(
						"StartScene not found localScene data! oj_local_scene=[{}], local_scene_id=[{}]",
						JsonUtil.toJsonString(existOjLocalScene),
						existOjLocalScene.getLocalSceneId());
				throw new IllegalArgumentException("未检索到场景数据！");
			}
		}

		// 初始化场景数据
		OjLocalScene ojLocalScene = initOjLocalScene(operation, authContext);
		LocalScene localScene = initLocalScene(operation, ojLocalScene, authContext);
		ojLocalScene.setLocalSceneId(localScene.getResourceId());
		localScene.setBusinessInstanceId(ojLocalScene.getResourceId());

		// 场景数据启动
		String sceneId = localSceneService.startScene(localScene);
		if (StringUtils.isBlank(sceneId)) {
			log.error("Operation startScene: Scene start failed! scene_id is null");
			throw new InvalidParamException("场景启动失败！");
		}

		// 数据存储
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.insert(ojLocalScene);
				localSceneMapper.insert(localScene);
			}
		});

		// 场景数据查询
		return remoteSceneService.getById(localScene.getMpSceneId(), localScene.getUserId());
	}

	/**
	 * 组装localScene数据
	 * @param operation
	 * @param ojLocalScene
	 * @param authContext
	 * @return
	 */
	private LocalScene initLocalScene(PracticeOperation operation, OjLocalScene ojLocalScene,
			AuthContext authContext) {

		LocalScene localScene = new LocalScene();
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setBusinessCode(LocalScene.BusinessCode.PRACTICE.getCode());
		localScene.setBusinessInstanceId(ojLocalScene.getResourceId());
		localScene.setDestroyMode(LocalScene.DestroyMode.AUTO.getCode());
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setTopoId(operation.getTopoId());
		localScene.setUserId(authContext.getUserId());
		return localScene;
	}

	/**
	 * 组装local_scene数据 未绑定localScene编号
	 * @param operation
	 * @param authContext
	 * @return
	 */
	private OjLocalScene initOjLocalScene(PracticeOperation operation, AuthContext authContext) {

		OjLocalScene localScene = new OjLocalScene();
		localScene.setBusinessId(operation.getResourceId());
		localScene.setBusinessType(OjLocalScene.BusinessType.OPERATION.getCode());
		localScene.setFlags(operation.getAnswer());
		localScene.setFromWeb(authContext.getIsWeb());
		localScene.setShared(
				operation.getSceneShareMode().intValue() == SceneShare.SHARED.getCode().intValue());
		localScene.setUserId(authContext.getUserId());
		localScene.setResourceId(IDGeneratorUtil.getID());
		if (operation.getDynamicFlag()) {
			localScene.setFlags(getDynamicFlags());
		} else {
			localScene.setFlags(operation.getAnswer());
		}

		return localScene;
	}

	private String getDynamicFlags() {
		return "flag{" + IDGeneratorUtil.getID() + "}";
	}
}
