package com.cyberpeace.oj.practice.service.impl;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import com.cyberpeace.oj.practice.service.PracticeEvaluateHandler;
import com.cyberpeace.oj.practice.service.bo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.dao.mapper.AttachmentMapper;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationService;

import javax.annotation.Resource;
import java.util.List;

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

	@Override
	public Integer addPracticeOperation(PracticeOperation practiceOperation, String ojSceneConfig) {

		// TODO 场景处理 获取topoId

		return practiceOperationMapper.insert(practiceOperation);
	}

	@Override
	public Integer updateOperation(PracticeOperation updateOperation, String ojSceneConfig) {

		// TODO 场景修改 修改失败 ？继续修改习题信息 ： 修改失败
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

		// TODO 获取场景信息配置

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

		// todo 解题状态查询
		return webOperationBo;
	}

	@Override
	public IPage<AdminOperationListBo> pageByQuery(Page page, OperationListQueryBo listQuery,
			AuthContext authContext) {

		// 过滤习题集
		List<String> authIds = practiceSetMapper.selectByAuthContext(authContext);
		if (!authIds.contains(listQuery.getPracticeSetId())) {
			log.error("The current resource has no permission！practice_set_id=[{}]",
					listQuery.getPracticeSetId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		// TODO 根据个人解题状态 筛选习题
		List<String> operationIds = null;
		if (null != listQuery.getSolved() && listQuery.getSolved()) {

		} else {

		}

		// 查询习题信息
		OperationListQuery operationListQuery = BeanUtils.copy(listQuery, OperationListQuery.class);
		operationListQuery.setResourceIds(operationIds);
		IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page,
				operationListQuery, authContext.getIsWeb());

		IPage<AdminOperationListBo> listBoIPage = iPage.convert(practiceOperation -> {
			AdminOperationListBo operationListBo = BeanUtils.copy(practiceOperation,
					AdminOperationListBo.class);
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
		if (solved.getSolved()) {
			EvaluateResult evaluateResult = BeanUtils.copy(solved, EvaluateResult.class);
			return evaluateResult;
		}

		EvaluateResult evaluateResult = practiceEvaluateHandler.evaluateByBusiness(userId,
				operation, submitAnswer);

		// 将评估记录存储
		PracticeSolved practiceSolved = BeanUtils.copy(evaluateResult, PracticeSolved.class);
		practiceSolved.setPracticeId(operation.getResourceId());
		practiceSolved.setSubmitUserId(userId);
		practiceSolved.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());

		practiceSolved.setCollection(resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.OPERATION.getCode(), operation.getResourceId()));
		practiceSolvedMapper.insert(practiceSolved);

		return evaluateResult;
	}
}
