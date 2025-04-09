package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.PageReqBase;
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
import com.cyberpeace.integratation.model.scene.SceneDumpContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.IndicatorService;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.OperationRecordRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeEvaluateHandler;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeOperationTargetService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalOperationCountBo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 实践实操Service实现
 *
 * @author chenz@date 2024/11/08@see ServiceImpl
 * @version 1.0.0
 * @date 2024/12/04
 * @see PracticeOperationService
 * @since 1.0.0
 */
@Service
@Slf4j
public class PracticeOperationServiceImpl extends ServiceImpl<PracticeOperationMapper, PracticeOperation> implements PracticeOperationService {

	/**
	 * 被收集
	 *
	 * @see Boolean
	 */
	private final Boolean IS_COLLECTED = Boolean.FALSE;

	/**
	 * 实践实操Mapper
	 *
	 * @see PracticeOperationMapper
	 */
	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	/**
	 * 附件Mapper
	 *
	 * @see AttachmentMapper
	 */
	@Autowired
	private AttachmentMapper attachmentMapper;

	/**
	 * 附件Service
	 *
	 * @see AttachmentService
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 实践设置Mapper
	 *
	 * @see PracticeSetMapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 实践评估处理器
	 *
	 * @see PracticeEvaluateHandler
	 */
	@Resource(name = "operation")
	private PracticeEvaluateHandler practiceEvaluateHandler;

	/**
	 * 资源收集Service
	 *
	 * @see ResourceCollectionService
	 */
	@Autowired
	private ResourceCollectionService resourceCollectionService;

	/**
	 * 实践解决Mapper
	 *
	 * @see PracticeSolvedMapper
	 */
	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	/**
	 * 远程拓扑Service
	 *
	 * @see RemoteTopoService
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 实践类别Mapper
	 *
	 * @see PracticeCategoryMapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * OJ当地场景Mapper
	 *
	 * @see OjLocalSceneMapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 当地场景Mapper
	 *
	 * @see LocalSceneMapper
	 */
	@Autowired
	private LocalSceneMapper localSceneMapper;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 当地场景Service
	 *
	 * @see LocalSceneService
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * OJ当地场景Service
	 *
	 * @see OjLocalSceneService
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * utilService
	 *
	 * @see UtilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 实践验证处理器
	 *
	 * @see PracticeValidateHandler
	 */
	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	/**
	 * 实践类别Service
	 *
	 * @see PracticeCategoryService
	 */
	@Autowired
	private PracticeCategoryService practiceCategoryService;

	/**
	 * 远程日志Service
	 *
	 * @see RemoteLogService
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 实践随机旗Mapper
	 *
	 * @see PracticeRandomFlagMapper
	 */
	@Autowired
	private PracticeRandomFlagMapper practiceRandomFlagMapper;

	/**
	 * 推送消息处理程序
	 *
	 * @see PushMessageHandler
	 */
	@Autowired
	private PushMessageHandler pushMessageHandler;

	/**
	 * 实践错误Mapper
	 *
	 * @see PracticeMistakeMapper
	 */
	@Autowired
	private PracticeMistakeMapper practiceMistakeMapper;

	/**
	 * 实践实操目标Mapper
	 *
	 * @see PracticeOperationTargetMapper
	 */
	@Autowired
	private PracticeOperationTargetMapper practiceOperationTargetMapper;

	/**
	 * 实践实操目标Service
	 *
	 * @see PracticeOperationTargetService
	 */
	@Autowired
	private PracticeOperationTargetService practiceOperationTargetService;

	@Autowired
	private IndicatorService indicatorService;

	/**
	 * 导出日志
	 *
	 * @see Logger
	 */
	private static final Logger EXPORT_LOG = LoggerFactory.getLogger("resourceImport");

	/**
	 * 共享练习缓存
	 *
	 * @see Cache<String, Boolean>
	 */
	private static Cache<String, Boolean> sharedPracticeCache = CacheBuilder.newBuilder()
			.expireAfterWrite(10, TimeUnit.SECONDS).build();

	/**
	 * 锁
	 *
	 * @see Object
	 */
	private final Object lock = new Object();

	/**
	 * 下载 URL
	 *
	 * @see String
	 */
	private String downloadUrl = "http://" + SystemPropUtil.getProp("BC_HOST") + ConfigUtil.getContextProperty("cr.local.file.prefix");

	/**
	 * 新增实践实操
	 *
	 * @param practiceOperation 实践实操
	 * @param targetJson        目标 JSON
	 * @param authContext       auth 上下文
	 * @return {@code Integer }
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see AuthContext
	 * @see Integer
	 */
	@Override
	public Integer addPracticeOperation(PracticeOperation practiceOperation,
			String targetJson, AuthContext authContext) {

		// 数据校验
//		PracticeSet practiceSet = validatePracticeSet(practiceOperation.getPracticeSetId(),
//				authContext);
		validateCategory(practiceOperation.getCategoryId());
		practiceValidateHandler.validateOperationTitleExist("",
				practiceOperation.getTitle());

		practiceOperation.setResourceId(IDGeneratorUtil.getID());
		List<OperationTargetAddBo> targetBoList = JsonUtil.toJsonObjectArray(targetJson,
				OperationTargetAddBo.class);
		if (practiceOperation.getDynamicFlag().intValue() == PracticeOperation.DynamicFlag.STATIC
				.getCode().intValue()) {
			practiceValidateHandler.validateFlagLength(practiceOperation.getAnswer());
		}

		// 数据设置
		practiceOperation.setBuiltin(CommonConstant.FALSE);
		// if (practiceOperation.getDynamicEnv() && null != ojSceneConfig) {
		// String topoId = localSceneService.saveTopo(ojSceneConfig,
		// LocalScene.BusinessCode.PRACTICE.getCode());
		// practiceOperation.setTopoId(topoId);
		// }

		// 习题集习题 数量加一
//		practiceSet.setPracticeCount(practiceSet.getPracticeCount() + CommonConstant.ONE);

		List<PracticeOperationTarget> targetList = targetBo2Entity(targetBoList,
				practiceOperation.getResourceId());
		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
//				practiceSetMapper.updateById(practiceSet);
				if (!targetList.isEmpty()) {
					practiceOperationTargetService.saveBatch(targetList);
				}
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
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see PracticeSet
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
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_TYPE_ERROR);
		}

		return practiceSet;
	}

	/**
	 * 习题分类校验
	 *
	 * @param categoryId
	 * @return
	 * @throws
	 * @see String
	 * @see PracticeCategory
	 */
	private PracticeCategory validateCategory(String categoryId) {

		PracticeCategory category = practiceCategoryMapper.selectById(categoryId);
		if (null == category) {
			log.error("category is null,categoryId =[{}]", categoryId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (category.getPracticeType().intValue() != PracticeCategory.PracticeType.OPERATION
				.getCode().intValue()) {
			log.error(
					"practice set type is inconsistent with the operation practice type! practice_category_id=[{}]",
					categoryId);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_CATEGORY_TYPE_ERROR);
		}

		return category;
	}

	/**
	 * 更新实操
	 *
	 * @param updateOperation 更新实操
	 * @param targetJson      目标 JSON
	 * @return {@code Integer }
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see Integer
	 */
	@Override
	public Integer updateOperation(PracticeOperation updateOperation, String targetJson) {

		// 数据校验
		PracticeOperation operation = practiceValidateHandler
				.validateOperationExist(updateOperation.getResourceId());

		List<OperationTargetAddBo> targetBoList = JsonUtil.toJsonObjectArray(targetJson,
				OperationTargetAddBo.class);
		if (updateOperation.getDynamicFlag().intValue() == PracticeOperation.DynamicFlag.STATIC
				.getCode().intValue()) {
			practiceValidateHandler.validateFlagLength(updateOperation.getAnswer());
		}
		practiceValidateHandler.validateOperationUpdateTitleExist(operation,
				updateOperation.getTitle());

//		if (operation.getDynamicEnv() && null != operation.getSceneShareMode()
//				&& StringUtils.isNotBlank(operation.getTopoId())) {
//			practiceValidateHandler.validateOperationUpdateSceneExist(operation, targetJson);
//		}

		// topo更新
//		if (updateOperation.getDynamicEnv() && null != targetJson) {
//			updateOperation.setTopoId(
//					localSceneService.saveTopo(targetJson, practiceOperation.getTopoId()));
//		}

		final Boolean updateRandom = operation.getDynamicFlag()
				.intValue() == PracticeOperation.DynamicFlag.RANDOM.getCode().intValue()
				&& !operation.getAttachmentId().equals(updateOperation.getAttachmentId());

		PracticeOperation originalBean = BeanUtils.copy(operation, PracticeOperation.class);
		PracticeOperation changeBean = BeanUtils.copy(updateOperation, PracticeOperation.class);
		originalBean.setBuiltin(null);
		originalBean.setCreateUserId(null);
		changeBean.setBuiltin(null);
		changeBean.setCreateUserId(null);

		List<PracticeOperationTarget> targetList = targetBo2Entity(targetBoList,
				operation.getResourceId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				practiceOperationMapper.updateById(updateOperation);

				if (updateRandom) {
					practiceRandomFlagMapper.deleteByPractice(updateOperation.getResourceId(),
							PracticeRandomFlag.FlagType.PRACTICE.getCode());
				}

//				if (updateOperation.getDynamicFlag()
//						.intValue() == PracticeOperation.DynamicFlag.DYNAMIC.getCode().intValue()) {
				practiceOperationTargetMapper
						.deleteByOperationId(updateOperation.getResourceId());
				if (!targetList.isEmpty()) {
					practiceOperationTargetService.saveBatch(targetList);
				}
//				}

				// 如果修改了额定字段
				if (isModifySpecify(updateOperation, operation)) {
					// 删除题解和错题集
					delMappedSolvedAndMistake(updateOperation);
				}
			}
		});

		return null;

	}

	/**
	 * 是否修改了特殊字段
	 *
	 * @param reqOperation 请求
	 * @param dbOperation  数据源
	 * @return 是否修改
	 * @throws
	 * @see PracticeOperation
	 * @see PracticeOperation
	 * @see boolean
	 */
	private boolean isModifySpecify(PracticeOperation reqOperation, PracticeOperation dbOperation) {
		if(!reqOperation.getDynamicEnv().equals(dbOperation.getDynamicEnv())) {
			return true;
		}
		if(!reqOperation.getDynamicFlag().equals(dbOperation.getDynamicFlag())) {
			return true;
		}
		if(reqOperation.getDynamicFlag().equals(dbOperation.getDynamicFlag()) &&
		!reqOperation.getAnswer().equals(dbOperation.getAnswer())) {
			return true;
		}
		if(!reqOperation.getAttachmentId().equals(dbOperation.getAttachmentId())) {
			return true;
		}
		return false;
	}

	/**
	 * 如果对应的几个字段被修改则删除对应的错题记录和做题记录
	 *
	 * @param updateOperation 更新实操
	 * @throws
	 * @see PracticeOperation
	 */
	private void delMappedSolvedAndMistake(PracticeOperation updateOperation) {
		// 删除解题记录
		LambdaQueryWrapper<PracticeSolved> practiceSolvedLambdaQueryWrapper = new LambdaQueryWrapper<>();
		practiceSolvedLambdaQueryWrapper.eq(PracticeSolved::getPracticeId, updateOperation.getResourceId());
		practiceSolvedMapper.delete(practiceSolvedLambdaQueryWrapper);

		// 删除错题记录
		LambdaQueryWrapper<PracticeMistake> practiceMistakeLambdaQueryWrapper = new LambdaQueryWrapper<>();
		practiceMistakeLambdaQueryWrapper.eq(PracticeMistake::getPracticeId, updateOperation.getResourceId());
		practiceMistakeMapper.delete(practiceMistakeLambdaQueryWrapper);
	}

	/**
	 * 查询由id
	 *
	 * @param resourceId  资源id
	 * @param authContext auth 上下文
	 * @return {@code OperationBo }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see OperationBo
	 */
	@Override
	public OperationBo selectById(String resourceId, AuthContext authContext) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("Operation practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		OperationBo operationBo = BeanUtils.copy(operation, OperationBo.class);
		operationBo.setAttackTag(indicatorService.selectIndicators(operation.getAttack()));
		operationBo.setDefenseTag(indicatorService.selectIndicators(operation.getDefense()));

//		if (operation.getDynamicEnv() && StringUtils.isNotBlank(operation.getTopoId())) {
//			JSONObject sceneConfig = remoteTopoService.getTopoById(operation.getTopoId());
//			operationBo.setSceneConfigData(sceneConfig);
//		}

		// 获取附件信息
		if (StringUtils.isNotBlank(operation.getAttachmentId())) {

			if (!authContext.getIsWeb()) {
				List<PracticeOperationFileBo> fileBos = getOperationFile(
						operation.getAttachmentId());
				operationBo.setFiles(fileBos);
			}

			// 随机flag文件查询
			if (operation.getDynamicFlag().intValue() == PracticeOperation.DynamicFlag.RANDOM
					.getCode().intValue()) {
				// 查询是否已经分配附件
				PracticeRandomFlag randomFlag = practiceRandomFlagMapper.selectByUserAndPractice(
						UserInfoContext.getUserContext().getUserId(), resourceId,
						PracticeRandomFlag.FlagType.PRACTICE.getCode());

				if (null == randomFlag) {
					randomFlag = getRandomFlag(operation);
					// 更改处
					operationBo.setAttachmentId(randomFlag.getAttachmentId());
					practiceRandomFlagMapper.insert(randomFlag);
				} else {
					operationBo.setAttachmentId(randomFlag.getAttachmentId());
				}

			}
			setFile(operationBo);
		}

		// 前面做了更改 这时候要重新设置一下
		operationBo.setAttachmentId(operation.getAttachmentId());

		List<PracticeOperationTarget> operationTargetList = practiceOperationTargetMapper.getAllByOperationIds(Collections.singletonList(operationBo.getResourceId()));
		List<TargetBo> targetBoList = new ArrayList<>();
		List<String> attachmentIds = operationTargetList.stream().map(PracticeOperationTarget::getAttachmentId).collect(Collectors.toList());
		List<Attachment> attachments = attachmentMapper.selectBatchIds(attachmentIds, false);

		// 数据类型转换
		Map<String, Attachment> attachmentMap = StreamApiUtils.toMap(attachments,
				Attachment::getResourceId, attachment -> attachment);
		for (PracticeOperationTarget target : operationTargetList) {
			TargetBo bo = new TargetBo();
			bo.setNodeId(target.getNodeId());
			bo.setNodeName(target.getNodeName());
			bo.setNodeCommand(target.getNodeCommand());
			bo.setAttachmentId(target.getAttachmentId());
			if (target.getAttachmentId() != null) {
				Attachment attachment = attachmentMap.getOrDefault(target.getAttachmentId(), null);
				if (attachment != null) {
					bo.setFileName(attachment.getFileName());
					bo.setFileUrl(utilService.handlerFileUrl(attachment.getFilePath()));
				}
			}
			targetBoList.add(bo);
		}
		operationBo.setTargetList(targetBoList);

		return operationBo;
	}

	/**
	 * 获取实操文件
	 *
	 * @param attachmentId 附件id
	 * @return {@code List<PracticeOperationFileBo> }
	 * @throws
	 * @see String
	 * @see List<PracticeOperationFileBo>
	 */
	@Override
	public List<PracticeOperationFileBo> getOperationFile(String attachmentId) {

		if (StringUtils.isBlank(attachmentId)) {
			return Lists.newArrayList();
		}

		String[] fileIds = attachmentId.split(CommonConstant.CODE_COMMA);
		List<String> attachmentIds = Lists.newArrayList(fileIds);
		List<Attachment> attachments = attachmentMapper.selectBatchIds(attachmentIds, false);

		// 附件数量判断
		if (attachments.size() != attachmentIds.size()) {
			log.error("attachments.size() != attachmentIds.size(),attachmentId =[{}]",attachmentId);
			throw new BusinessException(OjBusinessErrorCode.ATTACHMENT_MISSION);
		}

		// 数据类型转换
		Map<String, Attachment> attachmentMap = StreamApiUtils.toMap(attachments,
				Attachment::getResourceId, attachment -> attachment);

		return StreamApiUtils.map(attachmentIds, attachment -> fileToBo(attachment, attachmentMap));
	}

	/**
	 * 类型计数
	 *
	 * @param authContext auth 上下文
	 * @param listQueryBo 列表查询 bo
	 * @return {@code ExternalOperationCountBo }
	 * @throws
	 * @see AuthContext
	 * @see OperationListQueryBo
	 * @see ExternalOperationCountBo
	 */
	@Override
	public ExternalOperationCountBo typeCount(AuthContext authContext,
			OperationListQueryBo listQueryBo) {
		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return new ExternalOperationCountBo();
		}
		String searchSet = listQueryBo.getPracticeSetId();
		if (StringUtils.isNotBlank(searchSet)) {
			if (setIds.contains(searchSet)) {
				setIds.clear();
				setIds.add(searchSet);
			} else {
				return new ExternalOperationCountBo();
			}
		}

		OperationListQuery listQuery = BeanUtils.copy(listQueryBo, OperationListQuery.class);
		listQuery.setPracticeSetIds(setIds);

		List<PracticeOperation> operationList = practiceOperationMapper.listByQuery(listQuery,
				true);
		Map<Integer, List<PracticeOperation>> typeMap = StreamApiUtils.groupBy(operationList,
				PracticeOperation::getDynamicFlag);
		return new ExternalOperationCountBo(typeMap);
	}

	/**
	 * 文件到 bo
	 *
	 * @param fileId  文件id
	 * @param fileMap 文件映射
	 * @return {@code PracticeOperationFileBo }
	 * @throws
	 * @see String
	 * @see Map<String, Attachment>
	 * @see PracticeOperationFileBo
	 */
	private PracticeOperationFileBo fileToBo(String fileId, Map<String, Attachment> fileMap) {
		PracticeOperationFileBo fileBo = new PracticeOperationFileBo();
		Attachment attachment = fileMap.get(fileId);

		fileBo.setFileName(attachment.getFileName());
		fileBo.setFileUrl(utilService.handlerFileUrl(attachment.getFilePath()));
		return fileBo;
	}

	/**
	 * 获取随机flag数据
	 *
	 * @param operation
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see PracticeRandomFlag
	 */
	private PracticeRandomFlag getRandomFlag(PracticeOperation operation) {

		if (StringUtils.isBlank(operation.getAttachmentId())) {
			return null;
		}

		String[] strings = StringUtils.split(operation.getAttachmentId(),
				CommonConstant.CODE_COMMA);
		int index = MathUtils.randomRange(strings.length);

		List<String> flags = JsonUtil.toJsonObjectArray(operation.getAnswer(), String.class);
		PracticeRandomFlag randomFlag = new PracticeRandomFlag();
		randomFlag.setFlag(flags.get(index));
		randomFlag.setPracticeId(operation.getResourceId());
		randomFlag.setUserId(UserInfoContext.getUserContext().getUserId());
		randomFlag.setAttachmentId(strings[index]);
		randomFlag.setType(PracticeRandomFlag.FlagType.PRACTICE.getCode());

		return randomFlag;
	}

	/**
	 * 设置文件
	 *
	 * @param operationBo 实操博
	 * @throws
	 * @see OperationBo
	 */
	private void setFile(OperationBo operationBo) {

		Attachment attachment = attachmentMapper.selectById(operationBo.getAttachmentId());
		if (null != attachment) {
			operationBo.setFileName(attachment.getFileName());
			operationBo.setFileUrl(utilService.handlerFileUrl(attachment.getFilePath()));
		}
	}

	/**
	 * 查询由id
	 *
	 * @param resourceId  资源id
	 * @param authContext auth 上下文
	 * @param isCollected 被收集
	 * @return {@code WebOperationBo }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 * @see WebOperationBo
	 */
	@Override
	public WebOperationBo selectById(String resourceId, AuthContext authContext,
			Boolean isCollected) {

		OperationBo operationBo = selectById(resourceId, authContext);
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

		PracticeSet practiceSet = practiceSetMapper.selectById(webOperationBo.getPracticeSetId());
		webOperationBo.setPracticeSetName(practiceSet.getName());

		return webOperationBo;
	}

	/**
	 * 查询由id
	 *
	 * @param resourceId 资源id
	 * @return {@code WebOperationBo }
	 * @throws
	 * @see String
	 * @see WebOperationBo
	 */
	@Override
	public WebOperationBo selectById(String resourceId) {
		AuthContext authContext = new AuthContext();
		authContext.setIsWeb(false);
		OperationBo operationBo = selectById(resourceId, authContext);
		WebOperationBo webOperationBo = BeanUtils.copy(operationBo, WebOperationBo.class);

		// 收藏信息 解题状态查询
		Boolean result = resourceCollectionService.selectByUnique(authContext.getUserId(),
				ResourceCollection.BusinessType.OPERATION.getCode(), resourceId);

		webOperationBo.setIsCollection(result);

		// 解题状态
//		PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(
//				authContext.getUserId(), PracticeCategory.PracticeType.OPERATION.getCode(),
//				resourceId, isCollected);
//		if (null == practiceSolved) {
//			webOperationBo.setAnswer(null);
//			PracticeSolvedDetailBo detailBo = new PracticeSolvedDetailBo();
//			detailBo.setIsSolved(CommonConstant.FALSE);
//			webOperationBo.setResolvedDetail(detailBo);
//		} else {
//			PracticeSolvedDetailBo detailBo = new PracticeSolvedDetailBo();
//			if (practiceSolved.getSolved()) {
//				detailBo.setCorrectAnswer(operationBo.getAnswer());
//				detailBo.setIsSolved(practiceSolved.getSolved());
//				detailBo.setSubmitAnswer(practiceSolved.getSubmitAnswer());
//			} else {
//				detailBo.setIsSolved(practiceSolved.getSolved());
//				webOperationBo.setAnswer(null);
//			}
//			webOperationBo.setResolvedDetail(detailBo);
//		}

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

		return webOperationBo;
	}

	/**
	 * 分页由查询
	 *
	 * @param page        分页
	 * @param listQuery   列表查询
	 * @param authContext auth 上下文
	 * @return {@code IPage<AdminOperationListBo> }
	 * @throws
	 * @see Page
	 * @see OperationListQueryBo
	 * @see AuthContext
	 * @see IPage<AdminOperationListBo>
	 */
	@Override
	public IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery,
			AuthContext authContext) {

		// 过滤无权限习题集
//		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
//		if (CollectionUtils.isEmpty(authIds)) {
//			return null;
//		}

		// 过滤非公开习题集
//		List<PracticeSet> practiceSets = practiceSetMapper
//				.selectByType(PracticeSet.PracticeType.OPERATION.getCode(), authContext.getIsWeb());
//		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
//		authIds.retainAll(setIds);
//		if (CollectionUtils.isEmpty(authIds)) {
//			return null;
//		}

//		if (StringUtils.isNotBlank(listQuery.getPracticeSetId())
//				&& !authIds.contains(listQuery.getPracticeSetId())) {
//			return null;
//		}

		// 根据习题集过滤习题编号
		OperationListQuery operationListQuery = BeanUtils.copy(listQuery, OperationListQuery.class);
		if (StringUtils.isNotBlank(listQuery.getCourseCategoryId())) {
			operationListQuery
					.setCourseCategoryIds(Lists.newArrayList(listQuery.getCourseCategoryId()));
		}
//		if (StringUtils.isNotBlank(listQuery.getPracticeSetId())) {
//			authIds.clear();
//			authIds.add(listQuery.getPracticeSetId());
//		}

//		List<String> operationIds = practiceOperationMapper.selectBySetIds(authIds,
//				authContext.getIsWeb());
//		if (CollectionUtils.isEmpty(operationIds)) {
//			return null;
//		}
//		operationListQuery.setResourceIds(operationIds);

		// 根据解题状态过滤
//		if (authContext.getIsWeb() && null != listQuery.getSolved()) {
//
//			// 查询已解决习题编号
//			List<String> solvedIds = practiceSolvedMapper.selectUserAndSolved(
//					authContext.getUserId(), PracticeCategory.PracticeType.OPERATION.getCode(),
//					CommonConstant.TRUE, IS_COLLECTED);
//
//			// 正确取交集
//			if (listQuery.getSolved()) {
//				operationListQuery.getResourceIds().retainAll(solvedIds);
//			} else {
//				operationListQuery.getResourceIds().removeAll(solvedIds);
//			}
//
//			if (CollectionUtils.isEmpty(operationListQuery.getResourceIds())) {
//				return null;
//			}
//		}

		IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page,
				operationListQuery, authContext.getIsWeb());

		List<String> operationIds = iPage.getRecords().stream().map(PracticeOperation::getResourceId).collect(Collectors.toList());
		List<String> attachmentIds = new ArrayList<>();
		for (PracticeOperation operation : iPage.getRecords()) {
			String attachmentId = operation.getAttachmentId();
			if (StringUtils.isNotBlank(attachmentId)) {
				attachmentIds.addAll(Arrays.asList(attachmentId.split(CommonConstant.CODE_COMMA)));
			}
		}

		List<PracticeOperationTarget> operationTargets = practiceOperationTargetMapper.getAllByOperationIds(operationIds);
		if (operationTargets == null) {
			operationTargets = new ArrayList<>();
		}
		Map<String, List<PracticeOperationTarget>> operationTargetMap = new HashMap<>();
		for (PracticeOperationTarget operationTarget : operationTargets) {
			List<PracticeOperationTarget> operations = operationTargetMap.get(operationTarget.getOperationId());
			if (operations == null) {
				operationTargetMap.put(operationTarget.getOperationId(), Lists.newArrayList(operationTarget));
			} else {
				operations.add(operationTarget);
			}
		}


		attachmentIds.addAll(operationTargets.stream().map(PracticeOperationTarget::getAttachmentId).collect(Collectors.toList()));
		List<Attachment> attachments = attachmentMapper.selectBatchIds(attachmentIds, false);


		// 数据类型转换
		Map<String, Attachment> attachmentMap = StreamApiUtils.toMap(attachments,
				Attachment::getResourceId, attachment -> attachment);


		return iPage.convert(practiceOperation -> entityToListBo(practiceOperation, authContext, listQuery.getSolved(), operationTargetMap, attachmentMap));
	}

	/**
	 * 分页由查询
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<AdminOperationListBo> }
	 * @throws
	 * @see Page
	 * @see OperationListQueryBo
	 * @see IPage<AdminOperationListBo>
	 */
	@Override
	public IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery) {

		List<String> authIds = new ArrayList<>();


		// 根据习题集过滤习题编号
		OperationListQuery operationListQuery = BeanUtils.copy(listQuery, OperationListQuery.class);
		if (StringUtils.isNotBlank(listQuery.getPracticeSetId())) {
			authIds.add(listQuery.getPracticeSetId());
		}

		List<String> operationIds = practiceOperationMapper.selectOnlyBySetIds(authIds);
		if (CollectionUtils.isEmpty(operationIds)) {
			return null;
		}
		operationListQuery.setResourceIds(operationIds);


		IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page,
				operationListQuery, null);

		operationIds = iPage.getRecords().stream().map(PracticeOperation::getResourceId).collect(Collectors.toList());
		List<String> attachmentIds = new ArrayList<>();
		for (PracticeOperation operation : iPage.getRecords()) {
			String attachmentId = operation.getAttachmentId();
			if (StringUtils.isNotBlank(attachmentId)) {
				attachmentIds.addAll(Arrays.asList(attachmentId.split(CommonConstant.CODE_COMMA)));
			}
		}

		List<PracticeOperationTarget> operationTargets = practiceOperationTargetMapper.getAllByOperationIds(operationIds);
		if (operationTargets == null) {
			operationTargets = new ArrayList<>();
		}
		Map<String, List<PracticeOperationTarget>> operationTargetMap = new HashMap<>();
		for (PracticeOperationTarget operationTarget : operationTargets) {
			List<PracticeOperationTarget> operations = operationTargetMap.get(operationTarget.getOperationId());
			if (operations == null) {
				operationTargetMap.put(operationTarget.getOperationId(), Lists.newArrayList(operationTarget));
			} else {
				operations.add(operationTarget);
			}
		}


		attachmentIds.addAll(operationTargets.stream().map(PracticeOperationTarget::getAttachmentId).collect(Collectors.toList()));
		List<Attachment> attachments = attachmentMapper.selectBatchIds(attachmentIds, false);


		// 数据类型转换
		Map<String, Attachment> attachmentMap = StreamApiUtils.toMap(attachments,
				Attachment::getResourceId, attachment -> attachment);


		return iPage.convert(practiceOperation -> entityToListBo(practiceOperation, operationTargetMap, attachmentMap));
	}

	/**
	 * 数据转换
	 *
	 * @param practiceOperation
	 * @param authContext
	 * @param solved
	 * @param operationTargetMap 实操目标地图
	 * @param attachmentMap      附件映射
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see AuthContext
	 * @see Boolean
	 * @see Map<String, List<PracticeOperationTarget>>
	 * @see Map<String, Attachment>
	 * @see AdminOperationListBo
	 */
	private AdminOperationListBo entityToListBo(PracticeOperation practiceOperation,
			AuthContext authContext, Boolean solved, Map<String, List<PracticeOperationTarget>> operationTargetMap,
			Map<String, Attachment> attachmentMap) {

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
//		operationListBo.setPracticeSetName(null != practiceSet ? practiceSet.getName() : "");
		UserInfo userInfo = remoteUserService.getById(practiceOperation.getCreateUserId());
		operationListBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");
		List<PracticeOperationTarget> operationTargetList = operationTargetMap.getOrDefault(practiceOperation.getResourceId(), new ArrayList<>());
		List<TargetBo> targetBoList = new ArrayList<>();
		for (PracticeOperationTarget target : operationTargetList) {
			TargetBo bo = new TargetBo();
			bo.setNodeId(target.getNodeId());
			bo.setNodeName(target.getNodeName());
			bo.setNodeCommand(target.getNodeCommand());
			bo.setAttachmentId(target.getAttachmentId());
			if (target.getAttachmentId() != null) {
				Attachment attachment = attachmentMap.getOrDefault(target.getAttachmentId(), null);
				if (attachment != null) {
					bo.setFileName(attachment.getFileName());
					bo.setFileUrl(attachment.getFilePath());
				}
			}
			targetBoList.add(bo);
		}
		operationListBo.setTargetList(targetBoList);

		String attachmentId = practiceOperation.getAttachmentId();
		List<PracticeOperationFileBo> fileBoList = new ArrayList<>();
		if (StringUtils.isNotBlank(attachmentId)) {
			for (String id : attachmentId.split(CommonConstant.CODE_COMMA)) {
				PracticeOperationFileBo fileBo = new PracticeOperationFileBo();
				Attachment attachment = attachmentMap.get(id);
				fileBo.setFileName(attachment.getFileName());
				fileBo.setFileUrl(attachment.getFilePath());
				fileBoList.add(fileBo);
			}
		}
		operationListBo.setFiles(fileBoList);

		return operationListBo;

	}

	/**
	 * 实体列出 bo
	 *
	 * @param practiceOperation  实践实操
	 * @param operationTargetMap 实操目标地图
	 * @param attachmentMap      附件映射
	 * @return {@code AdminOperationListBo }
	 * @throws
	 * @see PracticeOperation
	 * @see Map<String, List<PracticeOperationTarget>>
	 * @see Map<String, Attachment>
	 * @see AdminOperationListBo
	 */
	private AdminOperationListBo entityToListBo(PracticeOperation practiceOperation, Map<String, List<PracticeOperationTarget>> operationTargetMap,
												Map<String, Attachment> attachmentMap) {

		AdminOperationListBo operationListBo = BeanUtils.copy(practiceOperation,
				AdminOperationListBo.class);


		UserInfo userInfo = remoteUserService.getById(practiceOperation.getCreateUserId());
		operationListBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");
		List<PracticeOperationTarget> operationTargetList = operationTargetMap.getOrDefault(practiceOperation.getResourceId(), new ArrayList<>());
		List<TargetBo> targetBoList = new ArrayList<>();
		for (PracticeOperationTarget target : operationTargetList) {
			TargetBo bo = new TargetBo();
			bo.setNodeId(target.getNodeId());
			bo.setNodeName(target.getNodeName());
			bo.setNodeCommand(target.getNodeCommand());
			if (target.getAttachmentId() != null) {
				Attachment attachment = attachmentMap.getOrDefault(target.getAttachmentId(), null);
				if (attachment != null) {
					bo.setFileName(attachment.getFileName());
					bo.setFileUrl(downloadUrl + attachment.getFilePath());
				}
			}
			targetBoList.add(bo);
		}
		operationListBo.setTargetList(targetBoList);

		String attachmentId = practiceOperation.getAttachmentId();
		List<PracticeOperationFileBo> fileBoList = new ArrayList<>();
		if (StringUtils.isNotBlank(attachmentId)) {
			for (String id : attachmentId.split(CommonConstant.CODE_COMMA)) {
				PracticeOperationFileBo fileBo = new PracticeOperationFileBo();
				Attachment attachment = attachmentMap.get(id);
				fileBo.setFileName(attachment.getFileName());
				fileBo.setFileUrl(downloadUrl + attachment.getFilePath());
				fileBoList.add(fileBo);
			}
		}
		operationListBo.setFiles(fileBoList);

		return operationListBo;

	}

	/**
	 * 提交答案
	 *
	 * @param userId       用户id
	 * @param resourceId   资源id
	 * @param submitAnswer 提交答案
	 * @param isCollected  被收集
	 * @return {@code EvaluateResult }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Boolean
	 * @see EvaluateResult
	 */
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
			return BeanUtils.copy(solved, EvaluateResult.class);
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

	/**
	 * 查询实践id列表
	 *
	 * @param authContext   auth 上下文
	 * @param practiceSetId 实践设置id
	 * @return {@code List<String> }
	 * @throws
	 * @see AuthContext
	 * @see String
	 * @see List<String>
	 */
	@Override
	public List<String> selectPracticeIds(AuthContext authContext, String practiceSetId) {
		List<String> setIds;
		if (practiceSetId == null) {
			setIds = practiceSetMapper.selectByAuthContext(authContext);
			if (CollectionUtils.isEmpty(setIds)) {
				return Lists.newArrayList();
			}
		}else {
			setIds = Arrays.asList(practiceSetId);
		}

		return practiceOperationMapper.selectBySetIds(setIds, authContext.getIsWeb());
	}

	/**
	 * 开始场景
	 *
	 * @param resourceId  资源id
	 * @param authContext auth 上下文
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see JSONObject
	 */
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
			throw new BusinessException(OjBusinessErrorCode.NON_DYNAMIC_ENVIRONMENT);
		}

		// 实操题共享场景避免多人同时点击导致启动多个场景
		if (operation.getSceneShareMode().equals(SceneShare.SHARED.getCode())) {
			synchronized (lock) {
				if (null != sharedPracticeCache.getIfPresent(resourceId)) {
					throw new BusinessException(OjBusinessErrorCode.SCENE_IS_RUNNING);
				} else {
					sharedPracticeCache.put(resourceId, true);
				}
			}
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

	/**
	 * 删除场景
	 *
	 * @param resourceId  资源id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	@Override
	public Boolean deleteScene(String resourceId, AuthContext authContext) {

		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(
				operation.getResourceId(), operation.getSceneShareMode(), authContext);

		ojLocalSceneService.deleteLocalAndRemoteScene(ojLocalScene);
		pushMessageHandler.pushOperationMessage(pushMessageHandler.getDeleteMessage(),
				ojLocalScene);

		return CommonConstant.TRUE;
	}

	/**
	 * 节点重启
	 *
	 * @param resourceId  资源id
	 * @param nodeId      节点id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	@Override
	public Boolean nodeRestart(String resourceId, String nodeId, AuthContext authContext) {

		LocalScene localScene = sceneNodeCheck(resourceId, authContext);
		return localSceneService.restartNode(localScene.getResourceId(), nodeId);
	}

	/**
	 * 节点重新创建
	 *
	 * @param resourceId  资源id
	 * @param nodeId      节点id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	@Override
	public Boolean nodeRecreate(String resourceId, String nodeId, AuthContext authContext) {

		LocalScene localScene = sceneNodeCheck(resourceId, authContext);
		return localSceneService.recreateNode(localScene.getResourceId(), nodeId);
	}

	/**
	 * 暂停场景
	 *
	 * @param resourceId  资源id
	 * @param status      状态
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see AuthContext
	 * @see Boolean
	 */
	@Override
	public Boolean pauseScene(String resourceId, Integer status, AuthContext authContext) {

		LocalScene localScene = sceneNodeCheck(resourceId, authContext);
		if (status.intValue() == LocalScene.SceneStatusEnum.PAUSE.getCode().intValue()) {

			remoteLogService.addOjLog(UserInfoContext.getUserContext(),
					OperationModule.CAPTURE_FLAG, OperationType.PAUSE, LogContent.EXERCISE.getMsg(),
					LogContent.CR_FORMAT.format(OperationType.PAUSE, LogContent.EXERCISE.getMsg(),
							practiceOperationMapper.selectById(resourceId).getTitle()));

			return localSceneService.pauseScene(localScene.getResourceId());
		} else {

			remoteLogService.addOjLog(UserInfoContext.getUserContext(),
					OperationModule.CAPTURE_FLAG, OperationType.UNPAUSE,
					LogContent.EXERCISE.getMsg(),
					LogContent.CR_FORMAT.format(OperationType.UNPAUSE, LogContent.EXERCISE.getMsg(),
							practiceOperationMapper.selectById(resourceId).getTitle()));

			return localSceneService.unpauseScene(localScene.getResourceId());
		}
	}

	/**
	 * 获取拓扑场景
	 *
	 * @param operationId 实操id
	 * @param authContext auth 上下文
	 * @return {@code TopoSceneDataBo }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see TopoSceneDataBo
	 */
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

	/**
	 * 批量删除
	 *
	 * @param practiceIds 实践id列表
	 * @param authContext auth 上下文
	 * @return {@code Integer }
	 * @throws
	 * @see List<String>
	 * @see AuthContext
	 * @see Integer
	 */
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
			for (LocalScene localScene : localScenes) {
				try {
					localSceneService.deleteRemoteScene(localScene);
				} catch (Exception e) {
					log.warn("Scene delete failed! local_scene_id=[{}], catch error:",
							localScene.getResourceId(), e);
					continue;
				}
			}
		}

//		String practiceId = practiceIds.get(CommonConstant.ZERO);
//		PracticeOperation operation = practiceValidateHandler.validateOperationExist(practiceId);
//		PracticeSet practiceSet = practiceValidateHandler
//				.validatePracticeSetExist(operation.getPracticeSetId());
//		practiceSet.setPracticeCount(null == practiceSet.getPracticeCount()
//				|| practiceSet.getPracticeCount() <= practiceIds.size() ? CommonConstant.ZERO
//						: practiceSet.getPracticeCount() - practiceIds.size());

		List<String> topoIds = practiceOperationMapper.topoIdsByIds(practiceIds);

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				practiceMistakeMapper.deleteByPracticeIds(practiceIds, null);
				practiceSolvedMapper.deleteByPracticeIds(practiceIds);

				if (CollectionUtils.isNotEmpty(localSceneIds)) {
					localSceneMapper.deleteBatchIds(localSceneIds);
				}
				if (CollectionUtils.isNotEmpty(ojLocalSceneIds)) {
					ojLocalSceneMapper.deleteBatchIds(ojLocalSceneIds);
				}

//				practiceSetMapper.updateById(practiceSet);
				try {
					remoteTopoService.batchDeleteTopo(topoIds);
				} catch (Exception e) {
					log.error("Operation batch delete topo failed!, topo_ids={}, catch error:",
							JsonUtil.toJsonString(topoIds), e);
				}
				if (!practiceIds.isEmpty()) {
					LambdaQueryWrapper<PracticeOperationTarget> wrapper = new LambdaQueryWrapper<>();
					wrapper.in(PracticeOperationTarget::getOperationId, practiceIds);
					practiceOperationTargetMapper.delete(wrapper);
				}
				return practiceOperationMapper.deleteBatchIds(practiceIds);
			}
		});
	}

	/**
	 * 处理器转 储实操
	 *
	 * @param operation     实操
	 * @param fileStorePath 文件存储路径
	 * @param filePathMap   文件路径图
	 * @return {@code PracticeOperationDumpLoadBo }
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see Map<String, String>
	 * @see PracticeOperationDumpLoadBo
	 */
	@Override
	public PracticeOperationDumpLoadBo handlerDumpOperation(PracticeOperation operation,
			String fileStorePath, Map<String, String> filePathMap) {
		PracticeOperationDumpLoadBo dumpLoadBo = BeanUtils.copy(operation,
				PracticeOperationDumpLoadBo.class);
		// 分类
		dumpLoadBo.setCategoryName("");

		// 附件
		List<String> filePathList = dumpOperationFilePathList(operation, fileStorePath,
				filePathMap);
		dumpLoadBo.setAttachmentPath(filePathList);

		List<OperationTargetAddBo> targets = this.dumpTargets(operation.getResourceId(),
				fileStorePath);
		dumpLoadBo.setTarget(targets);

		// 场景
		// if (operation.getDynamicEnv() &&
		// StringUtils.isNotBlank(operation.getTopoId())) {
		// JSONObject topoData = remoteTopoService.exportTopo(operation.getTopoId());
		// dumpLoadBo.setTopoConfig(topoData);
		// }
		List<String> contents = Lists.newArrayList(operation.getWriteup(), operation.getContent());
		for (String text : contents) {
			if (StringUtils.isNotBlank(text)) {
				String content = MdUtil.filterCodeContent(text);
				List<String> matchList = LocalStringUtil.getMatchStr(content,
						PatternConstant.MARKDOWN_IMAGE);
				List<String> matchPathList = StreamApiUtils.map(matchList,
						matchStr -> LocalStringUtil.getFirstMatchStr(matchStr,
								PatternConstant.MARKDOWN_IMAGE_URL));

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
		}

		// 题解 题解文件拷贝
		

		return dumpLoadBo;
	}

	private List<OperationTargetAddBo> dumpTargets(String resourceId, String fileStorePath) {
		// target
		List<PracticeOperationTarget> operationTargetList = practiceOperationTargetMapper
				.getAllByOperationIds(Collections.singletonList(resourceId));
		List<OperationTargetAddBo> res = Lists.newArrayList();
		for (PracticeOperationTarget target : operationTargetList) {
			OperationTargetAddBo bo = BeanUtils.copy(target, OperationTargetAddBo.class);
			if (StringUtils.isNotBlank(target.getAttachmentId())) {
				Attachment attachment = attachmentMapper.selectById(target.getAttachmentId());
				if (null == attachment) {
					log.error("operation practice file not found！target_id={}, attachment_id={}",
							target.getResourceId(), target.getAttachmentId());
					throw new BusinessException(
							OjBusinessErrorCode.PRACTICE_OPERATION_FILE_NOT_FOUNT);
				}

				// 重命名文件防止重复
				String realPath = utilService.getRealPath(attachment.getFilePath());
				String fileName = FileUtil.getFileName(realPath, true);
				String destFilePath = fileStorePath + File.separatorChar
						+ FileUtil.getNewName(fileName);
				FileUtil.copyFile(realPath, destFilePath, false);

				String relativePath = destFilePath.replace(fileStorePath,
						CommonConstant.EMPTY_STRING);
				bo.setAttachmentId(relativePath);
			}
			res.add(bo);
		}
		return res;
	}

	/**
	 * 获取进展统计学
	 *
	 * @param practiceSetId 实践设置id
	 * @return {@code ProgressStatisticsRes }
	 * @throws
	 * @see String
	 * @see ProgressStatisticsRes
	 */
	@Override
	public ProgressStatisticsRes getProgressStatistics(String practiceSetId) {
		ProgressStatisticsRes progressStatisticsRes = new ProgressStatisticsRes();
		List<AdminOperationListBo> practiceOptionListBoList = getAllPracticeTheoryByAuthAndSetId(practiceSetId);
		ProgressProportion simple = new ProgressProportion();
		simple.setCount((int) practiceOptionListBoList.stream()
													  .filter(practiceOptionListBo -> practiceOptionListBo.getDifficulty().equals(Difficulty.PRIMARY.getCode())
															  && practiceOptionListBo.getSolved()).count());
		simple.setSum((int) practiceOptionListBoList.stream()
													.filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.PRIMARY.getCode())).count());
		progressStatisticsRes.setSimple(simple);

		ProgressProportion medium = new ProgressProportion();
		medium.setCount((int) practiceOptionListBoList.stream()
													  .filter(practiceOptionListBo -> practiceOptionListBo.getDifficulty().equals(Difficulty.MIDDLE.getCode())
															  && practiceOptionListBo.getSolved()).count());
		medium.setSum((int) practiceOptionListBoList.stream()
													.filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.MIDDLE.getCode())).count());
		progressStatisticsRes.setMedium(medium);

		ProgressProportion difficulty = new ProgressProportion();
		difficulty.setCount((int) practiceOptionListBoList.stream()
													  .filter(practiceOptionListBo -> practiceOptionListBo.getDifficulty().equals(Difficulty.HIGH.getCode())
															  && practiceOptionListBo.getSolved()).count());
		difficulty.setSum((int) practiceOptionListBoList.stream()
													.filter(practiceTheoryListBo -> practiceTheoryListBo.getDifficulty().equals(Difficulty.HIGH.getCode())).count());
		progressStatisticsRes.setDifficulty(difficulty);

		ProgressProportion sum = new ProgressProportion();
		sum.setSum(simple.getSum() + medium.getSum() + difficulty.getSum());
		sum.setCount(simple.getCount() + medium.getCount() + difficulty.getCount());
		progressStatisticsRes.setSum(sum);
		return progressStatisticsRes;
	}

	/**
	 * 导出附件列表 按顺序排序
	 *
	 * @param operation
	 * @param fileStorePath 文件存储路径
	 * @param filePathMap
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see Map<String, String>
	 * @see List<String>
	 */
	private List<String> dumpOperationFilePathList(PracticeOperation operation,
			String fileStorePath, Map<String, String> filePathMap) {
		if (StringUtils.isBlank(operation.getAttachmentId())) {
			return Lists.newArrayList();
		}

		List<String> attachmentIdList = Arrays
				.asList(operation.getAttachmentId().split(CommonConstant.CODE_COMMA));
		return StreamApiUtils.map(attachmentIdList, attachmentId -> {
			Attachment attachment = attachmentMapper.selectById(attachmentId);
			if (null == attachment) {
				log.error(
						"operation practice file not found！operation_practice_id={} title=[{}], attachment_id={}",
						operation.getResourceId(), operation.getTitle(), attachmentId);
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_OPERATION_FILE_NOT_FOUNT);
			}

			// 重命名文件防止重复
			String realPath = utilService.getRealPath(attachment.getFilePath());
			String fileName = FileUtil.getFileName(realPath, true);
			String destFilePath = fileStorePath + File.separatorChar
					+ FileUtil.getNewName(fileName);
			FileUtil.copyFile(realPath, destFilePath, false);

			String relativePath = destFilePath.replace(fileStorePath, CommonConstant.EMPTY_STRING);
			filePathMap.put(attachment.getFilePath(), relativePath);
			return attachment.getFilePath();
		});
	}

	/**
	 * 加载附件列表
	 *
	 * @param filePathList
	 * @param filePathMap
	 * @param filePath
	 * @return
	 * @throws
	 * @see List<String>
	 * @see Map<String, String>
	 * @see String
	 * @see String
	 */
	public String loadOperationPathList(List<String> filePathList, Map<String, String> filePathMap,
			String filePath) {

		if (CollectionUtils.isEmpty(filePathList)) {
			return CommonConstant.EMPTY_STRING;
		}

		List<String> fileIds = StreamApiUtils.map(filePathList, file -> {
			String relativePath = filePathMap.get(file);
			String attachmentPath = filePath + File.separatorChar + relativePath;

			String fileSuffix = FileUtil.getFileSuffix(attachmentPath);
			String fileName = IDGeneratorUtil.getID() + CommonConstant.PERIOD + fileSuffix;
			String destFilePath = AttachmentPath.PRACTICE_ATTACHMENT.getDesc() + fileName;
			FileUtil.copyFile(attachmentPath, destFilePath, false);
			String fileUrl = utilService.replaceFileUrl(destFilePath);

			AttachmentBo attachmentBo = attachmentService.create(fileName, fileUrl);
			return attachmentBo.getResourceId();
		});

		return String.join(CommonConstant.CODE_COMMA, fileIds);

	}

	/**
	 * 加载实践实操
	 *
	 * @param practiceSetId 实践设置id
	 * @param dumpBo        dump bo
	 * @param filePathMap   文件路径图
	 * @param filePath      文件路径
	 * @param userId        用户id
	 * @return {@code PracticeOperation }
	 * @throws
	 * @see String
	 * @see PracticeOperationDumpLoadBo
	 * @see Map<String, String>
	 * @see String
	 * @see String
	 * @see PracticeOperation
	 */
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
		String attachmentIds = loadOperationPathList(dumpLoadBo.getAttachmentPath(), filePathMap,
				filePath);
		operation.setAttachmentId(attachmentIds);
		// targets
		List<PracticeOperationTarget> targetList = this.loadTargets(dumpBo, filePath,
				operation.getResourceId());
		practiceOperationTargetService.saveBatch(targetList);

		// 场景
//		if (dumpLoadBo.getDynamicEnv()) {
//			String topoId = localSceneService.saveTopo(dumpLoadBo.getTopoConfig(),
//					LocalScene.BusinessCode.PRACTICE.getCode());
//			operation.setTopoId(topoId);
//		}

		// 题解
		if (StringUtils.isNotBlank(dumpLoadBo.getWriteup())) {
			operation.setWriteup(
					handleTextWithImage(dumpLoadBo.getWriteup(), filePathMap, filePath));
		}

		if (StringUtils.isNotBlank(dumpLoadBo.getContent())) {
			operation.setContent(
					handleTextWithImage(dumpLoadBo.getContent(), filePathMap, filePath));
		}
		return operation;
	}
	
	private String handleTextWithImage(String writeup, Map<String, String> filePathMap,
			String filePath) {
		String filterCodeContent = MdUtil.filterCodeContent(writeup);
		List<String> matchList = LocalStringUtil.getMatchStr(filterCodeContent,
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
			return utilService.handlerFileUrl(storePath);
		});
		String content = LocalStringUtil.replaceAll(writeup, matchPathList, urlList);
		return content;
	}

	/**
	 * 节点操作前校验
	 *
	 * @param practiceId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see LocalScene
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
	 * @throws
	 * @see PracticeOperation
	 * @see AuthContext
	 * @see OjLocalScene
	 */
	private OjLocalScene initOjLocalScene(PracticeOperation operation, AuthContext authContext) {
		// 初始化场景数据
		InitOjSceneContext initOjSceneContext = InitOjSceneContext.builder()
				.businessId(operation.getResourceId())
				.businessType(LocalScene.BusinessCode.PRACTICE.getCode())
				.dynamicFlag(operation.getDynamicFlag()).flags(operation.getAnswer())
				.sharedType(operation.getSceneShareMode()).fromWeb(authContext.getIsWeb())
				.userId(authContext.getUserId()).build();

		return ojLocalSceneService.initOjLocalScene(initOjSceneContext);
	}

	/**
	 * 组装localScene数据
	 *
	 * @param operation
	 * @param ojLocalScene
	 * @param authContext
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see OjLocalScene
	 * @see AuthContext
	 * @see LocalScene
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

		LocalSceneMeta sceneMeta = LocalSceneMeta.builder().appResourceId(operation.getResourceId())
				.isWeb(ojLocalScene.getFromWeb()).build();

		// 设置场景类型
		JSONObject topoData = remoteTopoService.getTopoById(operation.getTopoId());
		topoData.put(JsonKeyConstant.REFERENCE_TYPE, LocalScene.BusinessCode.PRACTICE.getCode());
		sceneMeta.setTopoData(topoData);

		// 动态环境flag设置
		if (null != operation.getDynamicFlag() && operation.getDynamicFlag()
				.intValue() == PracticeOperation.DynamicFlag.DYNAMIC.getCode()) {
			JSONObject dynamicFlag = ojLocalSceneService.setDynamicFlag(ojLocalScene, topoData);
			sceneMeta.setTopoData(dynamicFlag);
		}
		localScene.setMeta(JsonUtil.toJsonString(sceneMeta));
		return localScene;
	}

	/**
	 * 创造中台日志
	 *
	 * @param practiceId 实践id
	 * @param logContent 日志内容
	 * @throws
	 * @see String
	 * @see LogContent
	 */
	@Override
	public void createMpLog(String practiceId, LogContent logContent) {
		PracticeOperation operation = practiceOperationMapper.selectById(practiceId);
		String content = LogContent.PRACTICE_FORMAT.format(operation.getContent(), logContent);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.CAPTURE_FLAG,
				OperationType.EXERCISE, LogContent.EXERCISE.getMsg(), content);
	}

	/**
	 * 查询导出实践
	 *
	 * @param setIds     设置id列表
	 * @param practiceId 实践id
	 * @return {@code List<PracticeOperation> }
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see List<PracticeOperation>
	 */
	@Override
	public List<PracticeOperation> selectExportPractice(List<String> setIds, String practiceId) {

		// 查询所有的习题
		return practiceListByIds(setIds, practiceId);
	}

	/**
	 * 导出实务
	 *
	 * @param setPath            设置路径
	 * @param operations         操作
	 * @param disableImageExport 禁用图像导出
	 * @throws
	 * @see String
	 * @see List<PracticeOperation>
	 * @see Boolean
	 */
	@Override
	public void exportPractice(String setPath, List<PracticeOperation> operations, Boolean disableImageExport) {

		for (PracticeOperation operation : operations) {
			try {
				doExport(setPath, operation, disableImageExport);
			} catch (Exception e) {
				EXPORT_LOG.error(
						"Practice operation export failed!, set_path=[{}], practice_name=[{}], catch error:",
						setPath, operation.getTitle(), e);
				throw new BusinessException(OjBusinessErrorCode.OPERATION_EXPORT_FAILED,
						operation.getTitle());
			}
		}
	}

	/**
	 * 获取选择id列表由认证
	 *
	 * @param authContext   auth 上下文
	 * @param practiceSetId 实践设置id
	 * @return {@code List<String> }
	 * @throws
	 * @see AuthContext
	 * @see String
	 * @see List<String>
	 */
	@Override
	public List<String> getOptionIdsByAuth(AuthContext authContext, String practiceSetId) {
		List<String> operationIds = new ArrayList<>();
		// 过滤无权限习题集
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authIds)) {
			return operationIds;
		}

		// 过滤非公开习题集
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.OPERATION.getCode(), authContext.getIsWeb());
		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
		authIds.retainAll(setIds);
		if (CollectionUtils.isEmpty(authIds)) {
			return operationIds;
		}

		if (StringUtils.isNotBlank(practiceSetId)
				&& !authIds.contains(practiceSetId)) {
			return operationIds;
		}

		if (practiceSetId != null) {
			authIds.clear();
			authIds.add(practiceSetId);
		}

		operationIds = practiceOperationMapper.selectBySetIds(authIds,
				authContext.getIsWeb());
		if (CollectionUtils.isEmpty(operationIds)) {
			return null;
		}
		return operationIds;
	}

	/**
	 * 获取实操记录
	 *
	 * @param userInfo  用户信息
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return {@code List<OperationRecordRes> }
	 * @throws
	 * @see UserInfo
	 * @see Date
	 * @see Date
	 * @see List<OperationRecordRes>
	 */
	@Override
	public List<OperationRecordRes> getOperationRecord(UserInfo userInfo, Date startTime, Date endTime) {
		AuthContext authContext = AuthContextUtils.constructAuth(userInfo, true);
		List<String> operationIds = getOptionIdsByAuth(authContext, null);
		return practiceSolvedMapper.getOperationRecord(userInfo.getUserId(), operationIds, true, startTime, endTime);
	}

	/**
	 * 获取实操类别进展
	 *
	 * @return {@code List<CategoryProgressRes> }
	 * @throws
	 * @see List<CategoryProgressRes>
	 */
	@Override
	public List<CategoryProgressRes> getOperationCategoryProgress() {
		List<AdminOperationListBo> operationList = getAllPracticeTheoryByAuthAndSetId(null);
		List<PracticeCategory> practiceCategoryList = practiceCategoryMapper.listByTypeAndName(PracticeCategory.PracticeType.OPERATION.getCode(), null);
		List<CategoryProgressRes> categoryProgressResList = new ArrayList<>();
		for (PracticeCategory practiceCategory : practiceCategoryList) {
			categoryProgressResList.add(
					new CategoryProgressRes(practiceCategory.getName(), practiceCategory.getResourceId(),
							//该方向的题目数
							(int) operationList.stream().filter(adminOperationListBo -> adminOperationListBo.getCategoryId().equals(practiceCategory.getResourceId())
									&& adminOperationListBo.getSolved()).count(),
							//该方向所有的题目数
							(int) operationList.stream().filter(adminOperationListBo -> adminOperationListBo.getCategoryId().equals(practiceCategory.getResourceId())).count()));
		}
		return categoryProgressResList;
	}

	/**
	 * 提交答案再
	 *
	 * @param userId       用户id
	 * @param resourceId   资源id
	 * @param submitAnswer 提交答案
	 * @return {@code EvaluateResult }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see EvaluateResult
	 */
	@Override
	public EvaluateResult submitAnswerAgain(String userId, String resourceId, String submitAnswer) {
		PracticeOperation operation = practiceOperationMapper.selectById(resourceId);
		if (null == operation) {
			log.error("Operation practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return practiceEvaluateHandler.evaluateByBusiness(userId,
				operation, submitAnswer);
	}

	/**
	 * 获取类别id和设置id列表
	 *
	 * @return {@code List<PracticeOperation> }
	 * @throws
	 * @see List<PracticeOperation>
	 */
	@Override
	public List<PracticeOperation> getCategoryIdAndSetIds() {
		return practiceOperationMapper.selectAllCategoryIds();
	}

	/**
	 * 获取实操由博 德id列表
	 *
	 * @param nodeIds 节点id列表
	 * @return {@code List<AdminOperationListBo> }
	 * @throws
	 * @see List<String>
	 * @see List<AdminOperationListBo>
	 */
	@Override
	public List<AdminOperationListBo> getOperationByBodeIds(List<String> nodeIds) {
		if (CollectionUtils.isEmpty(nodeIds)) {
			return new ArrayList<>();
		}
		List<PracticeOperationTarget> targetList = practiceOperationTargetMapper.getAllByTargetIds(nodeIds);
		if (CollectionUtils.isEmpty(targetList)) {
			return new ArrayList<>();
		}
		List<String> operationIds = StreamApiUtils.map(targetList, PracticeOperationTarget::getOperationId);
		List<PracticeOperation> operationList = practiceOperationMapper.selectBatchIds(operationIds);
		List<String> attachmentIds = new ArrayList<>();
		for (PracticeOperation operation : operationList) {
			String attachmentId = operation.getAttachmentId();
			if (StringUtils.isNotBlank(attachmentId)) {
				attachmentIds.addAll(Arrays.asList(attachmentId.split(CommonConstant.CODE_COMMA)));
			}
		}

		List<PracticeOperationTarget> operationTargets = practiceOperationTargetMapper.getAllByOperationIds(operationIds);
		Map<String, List<PracticeOperationTarget>> operationTargetMap = new HashMap<>();
		for (PracticeOperationTarget operationTarget : operationTargets) {
			List<PracticeOperationTarget> operations = operationTargetMap.get(operationTarget.getOperationId());
			if (operations == null) {
				operationTargetMap.put(operationTarget.getOperationId(), Collections.singletonList(operationTarget));
			} else {
				operations.add(operationTarget);
			}
		}


		attachmentIds.addAll(operationTargets.stream().map(PracticeOperationTarget::getAttachmentId).collect(Collectors.toList()));
		List<Attachment> attachments = attachmentMapper.selectBatchIds(attachmentIds, false);


		// 数据类型转换
		Map<String, Attachment> attachmentMap = StreamApiUtils.toMap(attachments,
				Attachment::getResourceId, attachment -> attachment);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false);

		return operationList.stream().map(practiceOperation -> entityToListBo(practiceOperation, authContext, null, operationTargetMap, attachmentMap)).collect(Collectors.toList());
	}

	@Override
	public File dumpBatch(List<String> ids) {
		// 设置数据存储
		String destDir = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID();
		String destFilePath = destDir + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;
		FileUtil.mkdirs(destFilePath);

		Map<String, String> filePathMap = Maps.newHashMap();
		List<PracticeOperationDumpLoadBo> boList = this.dumpOperation(ids, destFilePath,
				filePathMap);

		PracticeSetDumpLoadBo setDumpLoadBo = new PracticeSetDumpLoadBo();

		setDumpLoadBo.setPractice(boList);
		setDumpLoadBo.setFilePathMap(filePathMap);

		String dataPath = destDir + File.separatorChar + ResourceImportConstant.DATA_JSON;
		FileUtil.writeToDestFile(JsonUtil.toJsonString(setDumpLoadBo), dataPath);

		// 压缩当前文件夹
		String zipDest = ZipUtil.toZipDest(destDir, AttachmentPath.OTHER.getDesc());
		FileUtil.deleteDir(destDir, true);
		return new File(zipDest);
	}

	@Override
	public Boolean loadBatch(String realPath, String userId) {
		String destPath = realPath.substring(0, realPath.lastIndexOf(CommonConstant.PERIOD));
		ZipUtil.unzipToDest(realPath, destPath);

		// 解析数据
		String filePath = destPath + File.separatorChar
				+ ResourceImportConstant.DUMP_LOAD_FILE_STORE_PATH;
		String dataPath = destPath + File.separatorChar + ResourceImportConstant.DATA_JSON;
		String data = FileUtil.getContent(new File(dataPath));
		JSONObject dumpLoadBo = JsonUtil.toJsonObject(data, JSONObject.class);

		JSONArray practice = (JSONArray) dumpLoadBo.remove(JsonKeyConstant.PRACTICE_KEY);
		// 习题集
		PracticeSetDumpLoadBo setDumpLoadBo = dumpLoadBo.toJavaObject(PracticeSetDumpLoadBo.class);
		List<PracticeOperationDumpLoadBo> operationDumpLoadBos = practice
				.toJavaList(PracticeOperationDumpLoadBo.class);
		String setId = IDGeneratorUtil.getID();
		List<PracticeOperation> operations = StreamApiUtils.map(operationDumpLoadBos,
				bo -> loadPracticeOperation(setId, bo, setDumpLoadBo.getFilePathMap(), filePath,
						userId));

		this.saveBatch(operations);

		return true;
	}

	private List<PracticeOperationTarget> loadTargets(
			PracticeOperationDumpLoadBo operationDumpLoadBos, String filePath, String operationId) {
		List<OperationTargetAddBo> targetAddBoList = operationDumpLoadBos.getTarget();
		List<PracticeOperationTarget> targetList = Lists.newArrayList();
		for (OperationTargetAddBo addBo : targetAddBoList) {
			String relativePath = addBo.getAttachmentId();
			if (StringUtils.isNotBlank(relativePath)) {
				String path = filePath + relativePath;
				AttachmentBo attachmentBo = attachmentService.copyFile(path,
						AttachmentPath.PRACTICE_ATTACHMENT.getCode(), true, true);
				addBo.setAttachmentId(attachmentBo.getResourceId());
			}
			PracticeOperationTarget target = BeanUtils.copy(addBo, PracticeOperationTarget.class);
			target.setResourceId(IDGeneratorUtil.getID());
			target.setOperationId(operationId);
			targetList.add(target);
		}
		return targetList;
	}

	/**
	 * do export （执行导出）
	 *
	 * @param setPath            设置路径
	 * @param operation          实操
	 * @param disableImageExport 禁用图像导出
	 * @throws
	 * @see String
	 * @see PracticeOperation
	 * @see Boolean
	 */
	private void doExport(String setPath, PracticeOperation operation, Boolean disableImageExport) {
		EXPORT_LOG.info("Start export practice {}....", operation.getTitle());

		String path = createOperationPath(setPath, operation);

		// README文件导出
		File readmeFile = new File(path, ResourceImportConstant.README_NAME);
		if (!readmeFile.exists()) {
			MdCtfBase mdCtfBase = entityToMd(operation);
			MdParser mdParser = new MdParser();
			mdParser.write(mdCtfBase, readmeFile);
		}
		EXPORT_LOG.info("{} README.md export successfully", operation.getTitle());

		// 附件导出
		File attachmentDir = new File(path, ResourceImportConstant.PRACTICE_ATTACHMENT);
		exportAttachment(attachmentDir.getAbsolutePath(), operation.getAttachmentId());
		EXPORT_LOG.info("{} attachment export successfully", operation.getTitle());

		// 题解文件导出
		if (StringUtils.isNotBlank(operation.getWriteup())) {
			File file = new File(path, ResourceImportConstant.WRITEUP);
			exportWriteup(file.getAbsolutePath(), operation.getWriteup());
		}
		EXPORT_LOG.info("{} writeup.md export successfully", operation.getTitle());

		// topo和镜像导出
		if (StringUtils.isNotBlank(operation.getTopoId())) {
			SceneDumpContext dumpContext = exportSceneReq(operation, path, disableImageExport);
			remoteSceneService.sceneDump(dumpContext);
			EXPORT_LOG.info("{} topo and images export successfully", operation.getTitle());
		}

		EXPORT_LOG.info("Export practice [{}] successfully", operation.getTitle());
	}

	/**
	 * 获取场景导出请求体
	 *
	 * @param operation
	 * @param practicePath       实践路径
	 * @param disableImageExport 禁用图像导出
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see String
	 * @see Boolean
	 * @see SceneDumpContext
	 */
	private SceneDumpContext exportSceneReq(PracticeOperation operation, String practicePath, Boolean disableImageExport) {
		SceneDumpContext dumpContext = new SceneDumpContext();
		dumpContext.setId(operation.getTopoId());

		File envPath = new File(practicePath, ResourceImportConstant.ENV);
		dumpContext.setSingle_image_path(envPath.getAbsolutePath());
		dumpContext.setEnv_path(envPath.getAbsolutePath());
		dumpContext.setDisable_image_export(disableImageExport);

		return dumpContext;
	}

	/**
	 * 导出题解文件
	 *
	 * @param writeupDir
	 * @param content    内容
	 * @throws
	 * @see String
	 * @see String
	 */
	private void exportWriteup(String writeupDir, String content) {
		File writeupFile = new File(writeupDir, ResourceImportConstant.WRITEUP_MD);
		MdUtil.writeMdToFile(writeupFile, content);
	}

	/**
	 * 附件导出 存在不进行再次导出
	 *
	 * @param dir
	 * @param attachmentId 附件id
	 * @throws
	 * @see String
	 * @see String
	 */
	private void exportAttachment(String dir, String attachmentId) {
		if (StringUtils.isBlank(attachmentId)) {
			return;
		}

		String[] fileIds = StringUtils.split(attachmentId, CommonConstant.CODE_COMMA);
		for (String fileId : fileIds) {
			Attachment attachment = attachmentMapper.selectById(fileId);
			String realPath = utilService.getRealPath(attachment.getFilePath());

			File source = new File(realPath);
			File distFile = new File(dir, source.getName());
			if (!distFile.exists()) {
				FileUtil.copyFile(source, distFile);
			}
		}
	}

	/**
	 * 数据类型转换
	 *
	 * @param operation
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see MdCtfBase
	 */
	private MdCtfBase entityToMd(PracticeOperation operation) {

		MdCtfBase ctfBase = BeanUtils.copy(operation, MdCtfBase.class);
		UserInfo byId = remoteUserService.getById(operation.getCreateUserId());
		if (null != byId) {
			ctfBase.setAuthor(byId.getName());
		}

		PracticeCategory category = practiceCategoryMapper.selectById(operation.getCategoryId());
		if (null != category) {
			ctfBase.setCategory(category.getName());
		}

		if (StringUtils.isNotBlank(operation.getKnowledge())) {
			ctfBase.setKnowledge(
					JsonUtil.toJsonObjectArray(operation.getKnowledge(), String.class));
		}

		List<String> answers = JsonUtil.toJsonObjectArray(operation.getAnswer(), String.class);

		if (operation.getDynamicFlag().intValue() == PracticeOperation.DynamicFlag.RANDOM
				.getCode()) {
			MdCtfRandom copy = BeanUtils.copy(ctfBase, MdCtfRandom.class);
			Map<String, String> fileFlag = formatterRandom(operation.getAttachmentId(), answers);
			copy.setFlag(fileFlag);
			return copy;

		} else {
			MdCtf copy = BeanUtils.copy(ctfBase, MdCtf.class);
			copy.setFlag(answers);
			return copy;
		}
	}

	/**
	 * 随机flag数据转换
	 *
	 * @param attachmentId
	 * @param answers
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Map<String, String>
	 */
	private Map<String, String> formatterRandom(String attachmentId, List<String> answers) {
		Map<String, String> fileFlag = Maps.newTreeMap();
		String[] files = attachmentId.split(CommonConstant.CODE_COMMA);

		if (files.length != answers.size()) {
			log.error("files.length != answers.size()! attachmentId=[{}]", attachmentId);
			throw new BusinessException(
					OjBusinessErrorCode.PRACTICE_ATTACHMENT_ANSWER_INCONSISTENT);
		}

		for (int i = 0; i < files.length; i++) {
			Attachment attachment = attachmentMapper.selectById(files[i]);
			String realPath = utilService.getRealPath(attachment.getFilePath());

			File file = new File(realPath);
			if (!file.exists()) {
				log.error("file is not exists! realPath=[{}]", realPath);
				throw new BusinessException(ErrorCode.FILE_ERROR_FAIL, realPath);
			}

			fileFlag.put(file.getName(), answers.get(i));
		}

		return fileFlag;

	}

	/**
	 * 创建操作习题文件夹
	 *
	 * @param setPath
	 * @param operation
	 * @return
	 * @throws
	 * @see String
	 * @see PracticeOperation
	 * @see String
	 */
	private String createOperationPath(String setPath, PracticeOperation operation) {
		File practicePath = new File(setPath, operation.getTitle());
		FileUtil.createPath(practicePath);

		FileUtil.createManyPath(practicePath.getAbsolutePath(),
				ResourceImportConstant.PRACTICE_ATTACHMENT, ResourceImportConstant.ENV,
				ResourceImportConstant.SCRIPT, ResourceImportConstant.WRITEUP);

		EXPORT_LOG.info("{} file directory create finish....", operation.getTitle());
		return practicePath.getAbsolutePath();
	}

	/**
	 * 查询习题列表
	 *
	 * @param setIds     习题集编号
	 * @param practiceId 习题编号 ,分割
	 * @return
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see List<PracticeOperation>
	 */
	private List<PracticeOperation> practiceListByIds(List<String> setIds, String practiceId) {

		List<String> practiceIds = Lists.newArrayList();

		if (StringUtils.isNotBlank(practiceId)) {
			String[] tmpIds = practiceId.split(CommonConstant.CODE_COMMA);
			practiceIds.addAll(Arrays.asList(tmpIds));
		} else {
			List<String> ids = practiceOperationMapper.selectBySetIds(setIds, false);
			practiceIds.addAll(ids);
		}

		return practiceOperationMapper.selectBatchIds(practiceIds);
	}

	/**
	 * 获取都实践理论由认证和设置id
	 *
	 * @param practiceSetId 实践设置id
	 * @return {@code List<AdminOperationListBo> }
	 * @throws
	 * @see String
	 * @see List<AdminOperationListBo>
	 */
	private List<AdminOperationListBo> getAllPracticeTheoryByAuthAndSetId (String practiceSetId){
		PageReqBase pageReqBase = new PageReqBase();
		pageReqBase.setPer_page(2147483647);
		OperationListQueryBo listQueryBo = new OperationListQueryBo();
		listQueryBo.setPracticeSetId(practiceSetId);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		IPage<AdminOperationListBo> practiceTheoryListBoIPage = pageByQuery(pageReqBase.toPageParam(), listQueryBo, authContext);
		if (practiceTheoryListBoIPage == null) {
			return new ArrayList<>();
		}
		return practiceTheoryListBoIPage.getRecords();
	}

	/**
	 * 目标 BO2 实体
	 *
	 * @param boList      bo 列表
	 * @param operationId 实操id
	 * @return {@code List<PracticeOperationTarget> }
	 * @throws
	 * @see List<OperationTargetAddBo>
	 * @see String
	 * @see List<PracticeOperationTarget>
	 */
	private List<PracticeOperationTarget> targetBo2Entity(List<OperationTargetAddBo> boList,
			String operationId) {
		List<PracticeOperationTarget> res = Lists.newArrayList();
		for (OperationTargetAddBo bo : boList) {
			PracticeOperationTarget e = new PracticeOperationTarget();
			e.setOperationId(operationId);
			e.setAttachmentId(bo.getAttachmentId());
			e.setNodeId(bo.getNodeId());
			e.setNodeName(bo.getNodeName());
			e.setNodeCommand(bo.getNodeCommand());

			res.add(e);
		}
		return res;
	}

	private List<PracticeOperationDumpLoadBo> dumpOperation(List<String> operationIds,
			String fileStorePath, Map<String, String> filePathMap) {
		// 数据查询
		List<PracticeOperation> operationList = Lists.newArrayList();
		if (!operationIds.isEmpty()) {
			operationList = practiceOperationMapper.selectBatchIds(operationIds);
		}

		// 数据转换
		return StreamApiUtils.map(operationList,
				operation -> this.handlerDumpOperation(operation, fileStorePath, filePathMap));
	}

}
