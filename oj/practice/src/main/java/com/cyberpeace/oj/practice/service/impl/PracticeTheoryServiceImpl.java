package com.cyberpeace.oj.practice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
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
		if (CollectionUtils.isEmpty(authSetIds)
				|| (StringUtils.isNotBlank(listQuery.getPracticeSetId())
						&& !authSetIds.contains(listQuery.getPracticeSetId()))) {
			log.error("The current resource has no permission！practice_set_id=[{}]",
					listQuery.getPracticeSetId());
			return null;
		}

		// 习题集过滤习题
		TheoryListQuery theoryListQuery = BeanUtils.copy(listQuery, TheoryListQuery.class);
		if (StringUtils.isBlank(listQuery.getPracticeSetId())) {
			List<String> practiceIds = practiceTheoryMapper.selectBySetIds(authSetIds,
					authContext.getIsWeb());
			if (CollectionUtils.isEmpty(practiceIds)) {
				return null;
			}
			theoryListQuery.setResourceIds(practiceIds);
		}

		// 答题记录过滤习题
		if (authContext.getIsWeb() && null != listQuery.getSolved()) {
			List<String> practiceIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
					listQuery.getSolved());
			if (CollectionUtils.isEmpty(practiceIds)) {
				return null;
			}

			theoryListQuery.getResourceIds().addAll(practiceIds);
			List<String> distinctIds = theoryListQuery.getResourceIds().stream().distinct()
					.collect(Collectors.toList());
			theoryListQuery.setResourceIds(distinctIds);
		}

		IPage<PracticeTheory> iPage = practiceTheoryMapper.pageByQuery(page, theoryListQuery,
				authContext.getIsWeb());

		IPage<PracticeTheoryListBo> listBoIPage = iPage.convert(practiceTheory -> {
			PracticeTheoryListBo theoryListBo = BeanUtils.copy(practiceTheory,
					PracticeTheoryListBo.class);

			if (authContext.getIsWeb()) {
				// 解题状态设置
				if (null != listQuery.getSolved()) {
					theoryListBo.setSolved(listQuery.getSolved());
				} else {
					PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(
							authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
							practiceTheory.getResourceId());
					theoryListBo.setSolved(null != practiceSolved ? practiceSolved.getSolved()
							: CommonConstant.FALSE);
				}
			}
			PracticeSet practiceSet = practiceSetMapper
					.selectById(practiceTheory.getPracticeSetId());
			theoryListBo.setPracticeSetName(null != practiceSet ? practiceSet.getName() : "");
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

		PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.THEORY.getCode(), resourceId);

		answerToNull(webTheoryBo, practiceSolved);

		return webTheoryBo;
	}

	/**
	 * 答案置空
	 * @param webTheoryBo
	 * @param practiceSolved
	 */
	private void answerToNull(WebTheoryBo webTheoryBo, PracticeSolved practiceSolved) {

		if (null == practiceSolved || !practiceSolved.getSolved()) {
			webTheoryBo.setAnswer(null);
			webTheoryBo.setBlank(null);
			webTheoryBo.setKeyword(null);
		} else {
			webTheoryBo.setSubmitAnswer(practiceSolved.getSubmitAnswer());
			webTheoryBo.setIsSolved(practiceSolved.getSolved());
		}

	}

	@Override
	public EvaluateResult submitAnswer(String userId, String theoryId, String userAnswer) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(theoryId);
		if (null == practiceTheory) {
			log.info("Theory practice not found! practice_id=[{}]", theoryId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		// 判断是否已完成答题
		PracticeSolved solvedPractice = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.THEORY.getCode(), theoryId);
		if (null != solvedPractice && solvedPractice.getSolved()) {
			return BeanUtils.copy(solvedPractice, EvaluateResult.class);
		}

		EvaluateResult evaluateResult = theoryEvaluateHandler.evaluateByBusiness(userId,
				practiceTheory, userAnswer);

		// 将评估记录存储
		PracticeSolved practiceSolved = BeanUtils.copy(evaluateResult, PracticeSolved.class);
		practiceSolved.setPracticeId(practiceTheory.getResourceId());
		practiceSolved.setSubmitUserId(userId);
		practiceSolved.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
		practiceSolved.setSubmitAnswer(userAnswer);

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

		validatePracticeTheory(practiceTheory, authContext);
		return practiceTheoryMapper.insert(practiceTheory) > 0;
	}

	@Override
	public int updateTheory(PracticeTheory practiceTheory, AuthContext authContext) {

		validatePracticeTheory(practiceTheory, authContext);
		return practiceTheoryMapper.updateById(practiceTheory);
	}

	@Override
	public List<String> selectPracticeListIds(AuthContext authContext) {

		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return Lists.newArrayList();
		}

		return practiceTheoryMapper.selectBySetIds(setIds, authContext.getIsWeb());
	}

	/**
	 * 数据校验
	 * @param practiceTheory
	 * @param authContext
	 */
	private void validatePracticeTheory(PracticeTheory practiceTheory, AuthContext authContext) {

		if (StringUtils.isNotBlank(practiceTheory.getPracticeSetId())) {
			List<String> permissionSets = practiceSetMapper.selectByAuthContext(authContext);
			if (CollectionUtils.isEmpty(permissionSets)
					|| !permissionSets.contains(practiceTheory.getPracticeSetId())) {
				log.error(
						"When add theory practice, the practice set not permission! practice_id=[{}]",
						practiceTheory.getPracticeSetId());
				throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
			}

			PracticeSet practiceSet = practiceSetMapper
					.selectById(practiceTheory.getPracticeSetId());
			if (practiceSet.getPracticeType().intValue() != PracticeSet.PracticeType.THEORY
					.getCode().intValue()) {
				log.error(
						"When add theory practice, the practice set type error! practice_set_id=[{}], practice_type=[{}], theory_type=[{}]",
						practiceTheory.getPracticeSetId(), practiceSet.getPracticeType(),
						PracticeSet.PracticeType.THEORY.getCode());
				throw new InvalidParamException("习题类型与习题集类型不匹配！");
			}
		}

		if (StringUtils.isNotBlank(practiceTheory.getCategoryId())) {
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
		}

	}
}
