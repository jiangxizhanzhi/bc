package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.PracticeTheoryListBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class PracticeTheoryServiceImpl extends ServiceImpl<PracticeTheoryMapper, PracticeTheory> implements PracticeTheoryService {

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Resource(name = "theory")
	private TheoryEvaluateHandler theoryEvaluateHandler;

	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Override
	public IPage<PracticeTheoryListBo> pageByQuery(Page page, TheoryListQueryBo listQuery,
			AuthContext authContext) {

		// 过滤习题集
		List<String> authSetIds = practiceSetMapper.selectByAuthContext(authContext);
		if (!authSetIds.contains(listQuery.getPracticeSetId())) {
			log.error("The current resource has no permission！practice_set_id=[{}]",
					listQuery.getPracticeSetId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		// TODO 根据习题状态过滤习题

		TheoryListQuery theoryListQuery = BeanUtils.copy(listQuery, TheoryListQuery.class);
		IPage<PracticeTheory> iPage = practiceTheoryMapper.pageByQuery(page, theoryListQuery,
				authContext.getIsWeb());

		IPage<PracticeTheoryListBo> listBoIPage = iPage.convert(practiceTheory -> {
			PracticeTheoryListBo theoryListBo = BeanUtils.copy(practiceTheory,
					PracticeTheoryListBo.class);
			UserInfo userInfo = remoteUserService.getById(practiceTheory.getCreateUserId());
			theoryListBo.setCreateUserName(userInfo.getUserName());
			return theoryListBo;
		});

		return listBoIPage;
	}

	@Override
	public WebTheoryBo selectById(String resourceId, String userId) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(resourceId);
		if (null == practiceTheory) {
			log.error("Theory practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		WebTheoryBo webTheoryBo = BeanUtils.copy(practiceTheory, WebTheoryBo.class);

		Boolean result = resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.OPERATION.getCode(), resourceId);
		webTheoryBo.setIsCollection(result);

		// todo 习题解决详情

		return webTheoryBo;
	}

	@Override
	public EvaluateResult submitAnswer(String userId, String theoryId, String userAnswer) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(theoryId);
		if (null == practiceTheory) {
			log.info("Theory practice not found! practice_id=[{}]", theoryId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		// 判断是否已完成答题
		PracticeSolved solvedPractice = practiceSolvedMapper.selectUserAndId(userId, theoryId);
		if (solvedPractice.getSolved()) {
			return BeanUtils.copy(solvedPractice, EvaluateResult.class);
		}

		EvaluateResult evaluateResult = theoryEvaluateHandler.evaluateByBusiness(userId,
				practiceTheory, userAnswer);

		// 将评估记录存储
		PracticeSolved practiceSolved = BeanUtils.copy(evaluateResult, PracticeSolved.class);
		practiceSolved.setPracticeId(practiceTheory.getResourceId());
		practiceSolved.setSubmitUserId(userId);
		practiceSolved.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());

		practiceSolved.setCollection(resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.THEORY.getCode(), practiceTheory.getResourceId()));
		practiceSolvedMapper.insert(practiceSolved);

		return evaluateResult;
	}

	@Override
	public Boolean create(PracticeTheory practiceTheory, AuthContext authContext) {
		if (null == practiceTheory.getBuiltin()) {
			practiceTheory.setBuiltin(CommonConstant.FALSE);
		}
		if (null == practiceTheory.getScore()) {
			practiceTheory.setScore(CommonConstant.ZERO.doubleValue());
		}

		List<String> permissionSets = practiceSetMapper.selectByAuthContext(authContext);
		if (!permissionSets.contains(practiceTheory.getPracticeSetId())) {
			log.error("When add theory practice, the practice set not permission! practice_id=[{}]",
					practiceTheory.getPracticeSetId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(practiceTheory.getPracticeSetId());
		if (practiceSet.getPracticeType().intValue() != PracticeSet.PracticeType.THEORY.getCode()
				.intValue()) {
			log.error(
					"When add theory practice, the practice set type error! practice_set_id=[{}], practice_type=[{}], theory_type=[{}]",
					practiceTheory.getPracticeSetId(), practiceSet.getPracticeType(),
					PracticeSet.PracticeType.THEORY.getCode());
			throw new InvalidParamException("习题类型与习题集类型不匹配！");
		}

		PracticeCategory category = practiceCategoryMapper
				.selectById(practiceTheory.getCategoryId());
		if (null == category) {
			log.error("When add practice theory, the category not found! category_id=[{}]",
					practiceTheory.getCategoryId());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		} else {
			if (category.getPracticeType().intValue() != PracticeCategory.PracticeType.THEORY
					.getCode().intValue()) {
				throw new InvalidParamException("习题类型与分类类型不一致！");
			}
		}

		return practiceTheoryMapper.insert(practiceTheory) > 0;
	}
}
