package com.cyberpeace.oj.practice.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cyberpeace.integratation.service.*;
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
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.oj.ojbase.constant.*;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeEvaluateHandler;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PracticeOperationServiceImpl extends ServiceImpl<PracticeOperationMapper, PracticeOperation> implements PracticeOperationService {

	private final Boolean IS_COLLECTED = Boolean.FALSE;

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

	@Autowired
	private UtilService utilService;

	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private SystemConfigService systemConfigService;

	@Override
	public Integer addPracticeOperation(PracticeOperation practiceOperation,
			JSONObject ojSceneConfig, AuthContext authContext) {

		// 数据校验
		PracticeSet practiceSet = validatePracticeSet(practiceOperation.getPracticeSetId(),
				authContext);
		validateCategory(practiceOperation.getCategoryId());
		practiceValidateHandler.validateOperationTitleExist(practiceOperation.getPracticeSetId(),
				practiceOperation.getTitle());

		// 数据设置
		practiceOperation.setBuiltin(CommonConstant.FALSE);
		if (practiceOperation.getDynamicEnv() && null != ojSceneConfig) {
			String topoId = localSceneService.saveTopo(ojSceneConfig,
					LocalScene.BusinessCode.PRACTICE.getCode());
			practiceOperation.setTopoId(topoId);
		}

		// 习题集习题 数量加一
		practiceSet.setPracticeCount(practiceSet.getPracticeCount() + CommonConstant.ONE);

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				practiceSetMapper.updateById(practiceSet);
				return practiceOperationMapper.insert(practiceOperation);
			}
		});
	}

	/**
	 * 习题集校验
	 *
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
	 *
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

		// 数据校验
		PracticeOperation operation = practiceValidateHandler
				.validateOperationExist(updateOperation.getResourceId());
		practiceValidateHandler.validateOperationUpdateTitleExist(operation,
				updateOperation.getTitle());
		if (operation.getDynamicEnv() && null != operation.getSceneShareMode()
				&& StringUtils.isNotBlank(operation.getTopoId())) {
			practiceValidateHandler.validateOperationUpdateSceneExist(operation);
		}

		// topo更新
		if (updateOperation.getDynamicEnv() && null != ojSceneConfig) {
			localSceneService.saveTopo(ojSceneConfig, updateOperation.getTopoId());
		}

		return practiceOperationMapper.updateById(updateOperation);

	}

	@Override
	public OperationBo selectById(String resourceId) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("Operation practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
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
			operationBo.setFileUrl(utilService.handlerFileUrl(attachment.getFilePath()));
		}
		return operationBo;
	}

	@Override
	public WebOperationBo selectById(String resourceId, AuthContext authContext,
			Boolean isCollected) {

		OperationBo operationBo = selectById(resourceId);
		WebOperationBo webOperationBo = BeanUtils.copy(operationBo, WebOperationBo.class);

		// 收藏信息 解题状态查询
		Boolean result = resourceCollectionService.selectByUnique(authContext.getUserId(),
				ResourceCollection.BusinessType.OPERATION.getCode(), resourceId);

		webOperationBo.setIsCollection(result);

		// 解题状态
		PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(
				authContext.getUserId(), PracticeCategory.PracticeType.OPERATION.getCode(),
				resourceId, isCollected);
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
				JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
				webOperationBo.setSceneData(sceneData);
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
					CommonConstant.TRUE, IS_COLLECTED);

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

		IPage<AdminOperationListBo> listBoIPage = iPage
				.convert(practiceOperation -> entityToListBo(practiceOperation, authContext,
						listQuery.getSolved()));

		return listBoIPage;
	}

	/**
	 * 数据转换
	 * @param practiceOperation
	 * @param authContext 
	 * @param solved 
	 * @return
	 */
	private AdminOperationListBo entityToListBo(PracticeOperation practiceOperation,
			AuthContext authContext, Boolean solved) {

		AdminOperationListBo operationListBo = BeanUtils.copy(practiceOperation,
				AdminOperationListBo.class);
		if (practiceOperation.getDynamicEnv()
				&& StringUtils.isNotBlank(practiceOperation.getTopoId())) {
			OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(
					practiceOperation.getResourceId(), practiceOperation.getSceneShareMode(),
					authContext);
			operationListBo.setHasSceneData(null != ojLocalScene);
		}

		if (authContext.getIsWeb()) {
			if (null != solved) {
				operationListBo.setSolved(solved);
			} else {
				PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(
						authContext.getUserId(), PracticeCategory.PracticeType.OPERATION.getCode(),
						practiceOperation.getResourceId(), IS_COLLECTED);
				operationListBo.setSolved(
						null != practiceSolved ? practiceSolved.getSolved() : CommonConstant.FALSE);
			}
		}

		PracticeSet practiceSet = practiceSetMapper
				.selectById(practiceOperation.getPracticeSetId());
		operationListBo.setPracticeSetName(null != practiceSet ? practiceSet.getName() : "");
		UserInfo userInfo = remoteUserService.getById(practiceOperation.getCreateUserId());
		operationListBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");
		return operationListBo;

	}

	@Override
	public EvaluateResult submitAnswer(String userId, String resourceId, String submitAnswer,
			Boolean isCollected) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("Operation practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		PracticeSolved solved = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.OPERATION.getCode(), resourceId, isCollected);
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

		// 场景启动数量校验
		ojLocalSceneService.checkSceneStartCount(authContext.getUserId(), CommonConstant.ONE);

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("StartScene operation practice not found! operation_practice_id=[{}]");
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
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
			return ojLocalSceneService.getSceneData(existOjLocalScene);
		}

		// 初始化场景基本数据
		OjLocalScene ojLocalScene = initOjLocalScene(operation, authContext);
		LocalScene localScene = initLocalScene(operation, ojLocalScene, authContext);
		ojLocalScene.setLocalSceneId(localScene.getResourceId());

		// 场景数据启动
		String sceneId = authContext.getIsWeb() ? localSceneService.startScene(localScene)
				: localSceneService.testScene(localScene);
		if (StringUtils.isBlank(sceneId)) {
			log.error("Operation startScene: Scene start failed! scene_id is null");
			throw new BusinessException(ErrorCode.SCENE_RUNNING_FAILED);
		}

		// 数据存储
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.insert(ojLocalScene);

				localScene.setMpSceneId(sceneId);
				localSceneMapper.insert(localScene);
			}
		});

		// 场景数据查询
		return remoteSceneService.getById(localScene.getMpSceneId(), localScene.getUserId());
	}

	@Override
	public Boolean deleteScene(String resourceId, AuthContext authContext) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(
				operation.getResourceId(), operation.getSceneShareMode(), authContext);

		return ojLocalSceneService.deleteLocalAndRemoteScene(ojLocalScene);
	}

	@Override
	public Boolean nodeRestart(String resourceId, String nodeId, AuthContext authContext) {

		LocalScene localScene = sceneNodeCheck(resourceId, authContext);
		Boolean result = localSceneService.restartNode(localScene.getResourceId(), nodeId);
		return result;
	}

	@Override
	public Boolean nodeRecreate(String resourceId, String nodeId, AuthContext authContext) {

		LocalScene localScene = sceneNodeCheck(resourceId, authContext);
		Boolean result = localSceneService.recreateNode(localScene.getResourceId(), nodeId);
		return result;
	}

	@Override
	public Boolean pauseScene(String resourceId, Integer status, AuthContext authContext) {

		LocalScene localScene = sceneNodeCheck(resourceId, authContext);
		if (status.intValue() == LocalScene.SceneStatusEnum.PAUSE.getCode().intValue()) {
			return localSceneService.pauseScene(localScene.getResourceId());
		} else {
			return localSceneService.unpauseScene(localScene.getResourceId());
		}
	}

	@Override
	public TopoSceneDataBo getTopoScene(String operationId, AuthContext authContext) {

		PracticeOperation operation = practiceOperationMapper.selectById(operationId);
		if (null == operation) {
			log.error("Operation not found! practice_operation_id=[{}]", operationId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (!operation.getDynamicEnv()) {
			return null;
		}

		// 拓扑数据查询
		TopoSceneDataBo topoSceneDataBo = new TopoSceneDataBo();
		if (StringUtils.isNotBlank(operation.getTopoId())) {
			JSONObject topoData = remoteTopoService.getTopoById(operation.getTopoId());
			topoSceneDataBo.setSceneConfigData(topoData);
		}

		// 场景数据查询
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(operationId,
				operation.getSceneShareMode(), authContext);
		if (null != ojLocalScene) {
			JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
			topoSceneDataBo.setSceneData(sceneData);
		}

		return topoSceneDataBo;
	}

	@Override
	public Integer batchDelete(List<String> practiceIds, AuthContext authContext) {

		// 查询前后台场景
		List<OjLocalScene> adminOjScenes = StreamApiUtils.map(practiceIds, operationId -> {
			PracticeOperation operation = practiceOperationMapper.selectById(operationId);
			return ojLocalSceneService.selectOjLocalScene(operationId,
					operation.getSceneShareMode(), authContext);
		});

		authContext.setIsWeb(true);
		List<OjLocalScene> webOjScene = StreamApiUtils.map(practiceIds, operationId -> {
			PracticeOperation operation = practiceOperationMapper.selectById(operationId);
			return ojLocalSceneService.selectOjLocalScene(operationId,
					operation.getSceneShareMode(), authContext);
		});
		adminOjScenes.addAll(webOjScene);

		// 获取全部远程场景并删除
		List<String> ojLocalSceneIds = StreamApiUtils.map(adminOjScenes,
				OjLocalScene::getResourceId);
		List<String> localSceneIds = StreamApiUtils.map(adminOjScenes,
				OjLocalScene::getLocalSceneId);

		if (CollectionUtils.isNotEmpty(localSceneIds)) {
			List<LocalScene> localScenes = localSceneMapper.selectBatchIds(localSceneIds);
			localScenes.forEach(localScene -> localSceneService.deleteRemoteScene(localScene));
		}

		String practiceId = practiceIds.get(CommonConstant.ZERO);
		PracticeOperation operation = practiceValidateHandler.validateOperationExist(practiceId);
		PracticeSet practiceSet = practiceValidateHandler
				.validatePracticeSetExist(operation.getPracticeSetId());
		practiceSet.setPracticeCount(null == practiceSet.getPracticeCount()
				|| practiceSet.getPracticeCount() <= practiceIds.size() ? CommonConstant.ZERO
						: practiceSet.getPracticeCount() - practiceIds.size());

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				if (CollectionUtils.isNotEmpty(localSceneIds)) {
					localSceneMapper.deleteBatchIds(localSceneIds);
				}
				if (CollectionUtils.isNotEmpty(ojLocalSceneIds)) {
					ojLocalSceneMapper.deleteBatchIds(ojLocalSceneIds);
				}

				practiceSetMapper.updateById(practiceSet);
				return practiceOperationMapper.deleteBatchIds(practiceIds);
			}
		});
	}

	@Override
	public PracticeOperation parseRmCtf(String path, Map<String, String> envReqs,
			PracticeSet practiceSet) {
		File operationFile = new File(path, ResourceImportConstant.README_NAME);
		JSONObject operationData = MdUtil.parseJsonMarkdown(operationFile);

		JSONObject convertData = JsonUtil.filedConvert(operationData,
				ResourceImportConstant.OPERATION_PRACTICE_DATA_MAPPING);
		String envKey = new File(path).getName();
		String topoId = envReqs.get(envKey);
		PracticeOperation operation = JsonParseOperation(convertData, practiceSet, topoId);

		// 校验实操习题标题
		practiceValidateHandler.validateOperationTitleExist(practiceSet.getResourceId(),
				operation.getTitle());

		// 附件处理
		String attachmentFilePath = FileUtil.getOneFile(
				new File(path, ResourceImportConstant.PRACTICE_ATTACHMENT).getAbsolutePath(), null);
		if (StringUtils.isNotBlank(attachmentFilePath)) {
			AttachmentBo attachmentBo = attachmentService.copyFile(attachmentFilePath,
					AttachmentPath.PRACTICE_ATTACHMENT.getCode(), true, true);
			operation.setAttachmentId(attachmentBo.getResourceId());
		}

		// 题解数据处理
		File writeUpDirFile = new File(path, ResourceImportConstant.WRITEUP);
		String writeFilePath = FileUtil.getOneFile(writeUpDirFile.getAbsolutePath(),
				FileSunffixConstant.MD.getDesc());
		if (StringUtils.isNotBlank(writeFilePath)) {
			String writeup = MdUtil.parseMarkdown(writeFilePath);
			writeup = utilService.handlerMarkdownFile(writeup,
					AttachmentPath.PRACTICE_ATTACHMENT.getDesc());
			operation.setWriteup(writeup);
		}
		operation.setWriteupPublic(CommonConstant.TRUE);

		return operation;
	}

	@Override
	public PracticeOperationDumpLoadBo handlerDumpOperation(PracticeOperation operation,
			String fileStorePath, Map<String, String> filePathMap) {
		PracticeOperationDumpLoadBo dumpLoadBo = BeanUtils.copy(operation,
				PracticeOperationDumpLoadBo.class);
		// 分类
		PracticeCategory category = practiceValidateHandler
				.validateCategoryExist(operation.getCategoryId());
		dumpLoadBo.setCategoryName(category.getName());

		// 附件
		if (StringUtils.isNotBlank(operation.getAttachmentId())) {
			Attachment attachment = attachmentMapper.selectById(operation.getAttachmentId());
			if (null != attachment) {
				dumpLoadBo.setAttachmentPath(attachment.getFilePath());
				// 重命名文件防止重复
				String realPath = utilService.getRealPath(attachment.getFilePath());
				String fileName = FileUtil.getFileName(realPath, true);
				String destFilePath = fileStorePath + File.separatorChar
						+ FileUtil.getNewName(fileName);
				FileUtil.copyFile(realPath, destFilePath, false);

				String relativePath = destFilePath.replace(fileStorePath,
						CommonConstant.EMPTY_STRING);
				filePathMap.put(attachment.getFilePath(), relativePath);
			}
		}

		// 场景
		if (operation.getDynamicEnv() && StringUtils.isNotBlank(operation.getTopoId())) {
			JSONObject topoData = remoteTopoService.exportTopo(operation.getTopoId());
			dumpLoadBo.setTopoConfig(topoData);
		}

		// 题解 题解文件拷贝
		if (StringUtils.isNotBlank(operation.getWriteup())) {
			List<String> matchList = LocalStringUtil.getMatchStr(operation.getWriteup(),
					PatternConstant.MARKDOWN_IMAGE);
			List<String> matchPathList = StreamApiUtils.map(matchList, matchStr -> LocalStringUtil
					.getFirstMatchStr(matchStr, PatternConstant.MARKDOWN_IMAGE_URL));

			matchPathList.forEach(str -> {
				// 文件拷贝
				String realPath = utilService.getRealPath(str);
				String fileName = FileUtil.getFileName(realPath, true);
				String newName = FileUtil.getNewName(fileName);
				String destFilePath = fileStorePath + File.separatorChar + newName;
				FileUtil.copyFile(realPath, destFilePath, false);

				String relativePath = destFilePath.replace(fileStorePath,
						CommonConstant.EMPTY_STRING);
				filePathMap.put(str, relativePath);
			});
		}

		return dumpLoadBo;
	}

	@Override
	public PracticeOperation loadPracticeOperation(String practiceSetId,
			PracticeOperationDumpLoadBo dumpBo, Map<String, String> filePathMap, String filePath,
			String userId) {
		// 数据重复性判断
		String practiceId = practiceOperationMapper.selectByTitle(practiceSetId, dumpBo.getTitle());
		if (StringUtils.isNotBlank(practiceId)) {
			log.error("Practice operation exist! practice_id=[{}], practice_name=[{}]", practiceId,
					dumpBo.getTitle());
			return null;
		}

		PracticeOperationDumpLoadBo dumpLoadBo = BeanUtils.copy(dumpBo,
				PracticeOperationDumpLoadBo.class);
		PracticeOperation operation = BeanUtils.copy(dumpBo, PracticeOperation.class);
		operation.setPracticeSetId(practiceSetId);
		operation.setBuiltin(CommonConstant.TRUE);
		operation.setCreateUserId(userId);
		operation.setResourceId(IDGeneratorUtil.getID());

		// 分类
		String categoryId = practiceCategoryService.selectCategoryOrCreate(dumpBo.getCategoryName(),
				PracticeCategory.PracticeType.OPERATION.getCode(), userId);
		operation.setCategoryId(categoryId);

		// 附件
		if (StringUtils.isNotBlank(dumpLoadBo.getAttachmentPath())) {
			String relativePath = filePathMap.get(dumpLoadBo.getAttachmentPath());
			String attachmentPath = filePath + File.separatorChar + relativePath;

			String fileSuffix = FileUtil.getFileSuffix(attachmentPath);
			String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
			String destFilePath = AttachmentPath.PRACTICE_ATTACHMENT.getDesc() + fileName;
			FileUtil.copyFile(attachmentPath, destFilePath, false);
			String fileUrl = utilService.replaceFileUrl(destFilePath);

			AttachmentBo attachmentBo = attachmentService.create(fileName, fileUrl);
			operation.setAttachmentId(attachmentBo.getResourceId());
		}
		// 场景
		if (dumpLoadBo.getDynamicEnv()) {
			String topoId = localSceneService.saveTopo(dumpLoadBo.getTopoConfig(),
					LocalScene.BusinessCode.PRACTICE.getCode());
			operation.setTopoId(topoId);
		}

		// 题解
		if (StringUtils.isNotBlank(dumpLoadBo.getWriteup())) {
			List<String> matchList = LocalStringUtil.getMatchStr(dumpLoadBo.getWriteup(),
					PatternConstant.MARKDOWN_IMAGE);
			List<String> matchPathList = StreamApiUtils.map(matchList, matchStr -> LocalStringUtil
					.getFirstMatchStr(matchStr, PatternConstant.MARKDOWN_IMAGE_URL));

			List<String> urlList = StreamApiUtils.map(matchPathList, url -> {
				// 获取文件路径
				String relativePath = filePathMap.get(url);
				String imagePath = filePath + File.separatorChar + relativePath;

				// copy文件
				String fileSuffix = FileUtil.getFileSuffix(imagePath);
				String destFilePath = AttachmentPath.NOTE.getDesc() + IDGeneratorUtil.getID()
						+ CommonConstant.PERIOD + fileSuffix;
				FileUtil.copyFile(imagePath, destFilePath, false);
				// 替换文件路径为数据库可存储
				String storePath = utilService.replaceFileUrl(destFilePath);
				// 获取页面可访问
				String fileUrl = utilService.handlerFileUrl(storePath);
				return fileUrl;
			});
			String content = LocalStringUtil.replaceAll(dumpLoadBo.getWriteup(), matchList,
					urlList);
			operation.setWriteup(content);
		}
		return operation;
	}

	/**
	 * 格式化实操习题
	 *
	 * @param operationData
	 * @param practiceSet
	 * @return
	 */
	private PracticeOperation JsonParseOperation(JSONObject operationData, PracticeSet practiceSet,
			String topoId) {

		PracticeOperation operation = new PracticeOperation();

		// 分类数据处理
		String categoryName = (String) operationData.remove("category_id");
		String categoryId = practiceCategoryMapper
				.selectByName(PracticeCategory.PracticeType.OPERATION.getCode(), categoryName);
		if (StringUtils.isBlank(categoryId)) {
			PracticeCategory practiceCategory = new PracticeCategory();
			practiceCategory.setResourceId(IDGeneratorUtil.getID());
			practiceCategory.setName(categoryName);
			practiceCategory.setPracticeType(PracticeCategory.PracticeType.OPERATION.getCode());
			practiceCategory.setCode(practiceCategory.getResourceId());
			practiceCategory.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
			practiceCategoryMapper.insert(practiceCategory);

			operation.setCategoryId(practiceCategory.getResourceId());
		} else {
			operation.setCategoryId(categoryId);
		}

		// 知识点数据处理
		String knowledgeStr = (String) operationData.remove("knowledge");
		String[] knowledgeArray = knowledgeStr.split(PatternConstant.KNOWLEDGE_SPLIT_STR);
		// 经过剪切 第一个为空字符 删掉
		if (knowledgeArray.length > CommonConstant.ZERO) {
			String[] knowledges = Arrays.copyOfRange(knowledgeArray, CommonConstant.ONE,
					knowledgeArray.length);
			operation.setKnowledge(JsonUtil.toJsonString(knowledges));
		}

		// 场景flag相关操作
		String flag = (String) operationData.remove("answer");
		operation.setDynamicEnv(StringUtils.isNotBlank(topoId));

		if (StringUtils.isNotBlank(topoId)) {
			operation.setTopoId(topoId);
			String sceneShareMode = (String) operationData.remove("scene_share_mode");
			operation.setSceneShareMode(
					sceneShareMode.equals(CommonConstant.RIGHT) ? SceneShare.SHARED.getCode()
							: SceneShare.PRIVATE.getCode());
			operation.setDestroyDelay(CommonConstant.TWO);

			// 场景flag类型
			if (StringUtils.isBlank(flag)) {
				operation.setDynamicFlag(CommonConstant.TRUE);
			} else {
				operation.setDynamicFlag(CommonConstant.FALSE);
				operation.setAnswer(flag);
			}
		}

		// 难度处理
		String difficultyStr = (String) operationData.remove("difficulty");
		Integer difficulty = EnumUtils.getEnumCodeByDesc(difficultyStr, Difficulty.class);
		operation.setDifficulty(difficulty);

		operation.setScore(CommonConstant.ZERO.doubleValue());
		operation.setResourceId(IDGeneratorUtil.getID());
		operation.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
		operation.setBuiltin(CommonConstant.TRUE);
		operation.setPracticeSetId(practiceSet.getResourceId());
		operation.setIsPublic(CommonConstant.TRUE);

		PracticeOperation practiceOperation = operationData.toJavaObject(PracticeOperation.class);
		operation.setTitle(practiceOperation.getTitle());
		operation.setContent(practiceOperation.getTitle());

		return operation;

	}

	/**
	 * 节点操作前校验
	 *
	 * @param practiceId
	 * @param authContext
	 * @return
	 */
	private LocalScene sceneNodeCheck(String practiceId, AuthContext authContext) {

		PracticeOperation operation = practiceOperationMapper.selectById(practiceId);
		if (null == operation) {
			log.error(
					"PracticeOperationServiceImpl：Practice operation not found! practice_operation_id=[{}]",
					practiceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(practiceId,
				operation.getSceneShareMode(), authContext);
		if (null == ojLocalScene) {
			log.error(
					"PracticeOperationServiceImpl: ojLocalScene not found! operation_id=[{}], shared=[{}], fromWeb=[{}], user_id=[{}]",
					practiceId, operation.getSceneShareMode(), authContext.getIsWeb(),
					authContext.getUserId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		if (null == localScene || StringUtils.isBlank(localScene.getMpSceneId())) {
			log.error(
					"PracticeOperationServiceImpl: localScene not found ot scene_id is null! local_scene_id=[{}]",
					ojLocalScene.getLocalSceneId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return localScene;
	}

	/**
	 * 初始化实训场景数据
	 *
	 * @param operation
	 * @param authContext
	 * @return
	 */
	private OjLocalScene initOjLocalScene(PracticeOperation operation, AuthContext authContext) {
		// 初始化场景数据
		InitOjSceneContext initOjSceneContext = InitOjSceneContext.builder()
				.businessId(operation.getResourceId())
				.businessType(LocalScene.BusinessCode.PRACTICE.getCode())
				.dynamicFlag(operation.getDynamicFlag()).flags(operation.getAnswer())
				.sharedType(operation.getSceneShareMode()).fromWeb(authContext.getIsWeb())
				.userId(authContext.getUserId()).build();
		OjLocalScene ojLocalScene = ojLocalSceneService.initOjLocalScene(initOjSceneContext);

		return ojLocalScene;
	}

	/**
	 * 组装localScene数据
	 *
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
		localScene.setSceneTtl(operation.getDestroyDelay() * 60);
		localScene.setSceneName(operation.getTitle());
		localScene.setSceneAttr(
				operation.getSceneShareMode() == SceneShare.SHARED.getCode().intValue()
						? SceneStartContext.SceneAttrEnum.NULL.getCode()
						: SceneStartContext.SceneAttrEnum.SINGLE.getCode());
		localScene.setRecord(Integer.parseInt(systemConfigService
				.getSystemConfig(JsonKeyConstant.SCREEN_RECORDING)) == CommonConstant.ONE);

		LocalSceneMeta sceneMeta = LocalSceneMeta.builder().appResourceId(operation.getResourceId())
				.isWeb(ojLocalScene.getFromWeb()).build();
		// 设置场景类型
		JSONObject topoData = remoteTopoService.getTopoById(operation.getTopoId());
		topoData.put(JsonKeyConstant.REFERENCE_TYPE, LocalScene.BusinessCode.PRACTICE.getCode());

		// 动态环境flag设置
		if (StringUtils.isNotBlank(operation.getTopoId())) {
			JSONObject dynamicFlag = ojLocalSceneService.setDynamicFlag(ojLocalScene.getFlags(),
					topoData);
			sceneMeta.setTopoData(dynamicFlag);
		}
		localScene.setMeta(JsonUtil.toJsonString(sceneMeta));
		return localScene;
	}

	@Override
	public void createMpLog(String practiceId, LogContent logContent) {
		PracticeOperation operation = practiceOperationMapper.selectById(practiceId);
		String content = LogContent.PRACTICE_FORMAT.format(operation.getContent(), logContent);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.EXERCISE, LogContent.EXERCISE.getMsg(), content);
	}

}
