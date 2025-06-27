package com.cyberpeace.oj.practice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.CollectionReq;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import com.cyberpeace.oj.practice.dao.entity.PaperPracticeTheory;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PaperPracticeTheoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PaperPracticeTheoryService;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import com.cyberpeace.oj.practice.service.bo.BlankAnswer;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.EvaluateResultTheory;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;

import lombok.extern.slf4j.Slf4j;

/**
 * 试卷练习理论Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class PaperPracticeTheoryServiceImpl extends ServiceImpl<PaperPracticeTheoryMapper, PaperPracticeTheory> implements PaperPracticeTheoryService {

	/**
	 * 试卷练习理论Mapper
	 */
	@Autowired
	private PaperPracticeTheoryMapper paperPracticeTheoryMapper;

	/**
	 * 理论评估处理器
	 */
	@Resource(name = "theory")
	private TheoryEvaluateHandler theoryEvaluateHandler;

	/**
	 * 练习错误Service
	 */
	@Autowired
	private PracticeMistakeService practiceMistakeService;

	/**
	 * 练习解决Mapper
	 */
	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 资源收集Service
	 */
	@Autowired
	private ResourceCollectionService resourceCollectionService;

	/**
	 * 练习理论Mapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 获取练习详情
	 *
	 * @param practiceRefId 练习裁判id
	 * @return {@code WebTheoryBo }
	 */
	@Override
	public WebTheoryBo getPracticeDetail(String practiceRefId) {
		PaperPracticeTheory paperPracticeTheory = paperPracticeTheoryMapper.selectById(practiceRefId);
		PracticeTheory practiceTheory = JsonUtil.toJsonObject(paperPracticeTheory.getSnapshot(), PracticeTheory.class);
		WebTheoryBo webTheoryBo = BeanUtils.copy(practiceTheory, WebTheoryBo.class);
		String practiceId = paperPracticeTheory.getOriginId();
		Boolean isCollection = resourceCollectionService.selectByUnique(UserInfoContext.getUserContext().getUserId(),
				ResourceCollection.BusinessType.THEORY.getCode(), practiceId);
		PracticeTheory originPracticeTheory = practiceTheoryMapper.selectById(paperPracticeTheory.getOriginId());
		webTheoryBo.setResourceId(practiceRefId);
		webTheoryBo.setIsCollection(originPracticeTheory != null && isCollection);
		webTheoryBo.setAnswer(paperPracticeTheory.getSubmitAnswer());
		webTheoryBo.setSolved(paperPracticeTheory.getSolved());
		if (webTheoryBo.getTheoryType().equals(PracticeTheory.TheoryType.FILL_BLANK.getCode())) {
			webTheoryBo.setBlankCount(JsonUtil
					.toJsonObjectArray(practiceTheory.getBlank(), BlankAnswer.class).size());
		}
		return webTheoryBo;
	}

	/**
	 * 提交答案
	 *
	 * @param practiceRefId 练习裁判id
	 * @param submitAnswer  提交答案
	 * @return {@code EvaluateResultTheory }
	 */
	@Override
	public EvaluateResultTheory submitAnswer(String practiceRefId, String submitAnswer) {
		PaperPracticeTheory paperPracticeTheory = paperPracticeTheoryMapper.selectById(practiceRefId);
		String userId = UserInfoContext.getUserContext().getUserId();
		EvaluateResultTheory evaluateResultTheory = new EvaluateResultTheory();
		evaluateResultTheory.setResourceId(practiceRefId);
		evaluateResultTheory.setScore(0d);
		evaluateResultTheory.setTotalScore(0d);
		if (null == paperPracticeTheory) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		paperPracticeTheory.setSubmitAnswer(submitAnswer);
		//题目已解出
		if (paperPracticeTheory.getSolved().equals(PracticeSolved.SolvedStatus.SOLVED.getCode())) {
			evaluateResultTheory.setStatus(paperPracticeTheory.getSolved());
			return evaluateResultTheory;
		}

		PracticeTheory practiceTheory = JsonUtil.toJsonObject(paperPracticeTheory.getSnapshot(), PracticeTheory.class);
		EvaluateResult evaluateResult = theoryEvaluateHandler.evaluateByBusiness(userId,
				practiceTheory, submitAnswer);

		PracticeSolved practiceSolved = BeanUtils.copy(evaluateResult, PracticeSolved.class);
		practiceSolved.setPracticeId(practiceTheory.getResourceId());
		practiceSolved.setSubmitUserId(userId);
		practiceSolved.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
		practiceSolved.setSubmitAnswer(submitAnswer);
		practiceSolved.setIsPaper(true);
		practiceSolved.setCollection(resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.THEORY.getCode(), practiceTheory.getResourceId()));

		if (evaluateResult.getSolved()) {
			evaluateResultTheory.setStatus(PracticeSolved.SolvedStatus.SOLVED.getCode());
			paperPracticeTheory.setSolved(PracticeSolved.SolvedStatus.SOLVED.getCode());
		} else {
			evaluateResultTheory.setStatus(PracticeSolved.SolvedStatus.TRIED.getCode());
			paperPracticeTheory.setSolved(PracticeSolved.SolvedStatus.TRIED.getCode());
		}

		//修改原题目状态
		LambdaQueryWrapper<PracticeSolved> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(PracticeSolved::getPracticeId, paperPracticeTheory.getOriginId());
		lambdaQueryWrapper.eq(PracticeSolved::getSubmitUserId, userId);
		PracticeSolved originPracticeSolved = practiceSolvedMapper.selectOne(lambdaQueryWrapper);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				//更新错题集状态
				if (evaluateResult.getSolved()) {
					practiceMistakeService.addCorrect(userId, paperPracticeTheory.getOriginId(), PracticeCategory.PracticeType.THEORY.getCode());
				} else {
					practiceMistakeService.addIncorrect(userId, paperPracticeTheory.getOriginId(), PracticeCategory.PracticeType.THEORY.getCode());
				}
				//当原题目为未开始或尝试过的状态时更新题目状态
				if (originPracticeSolved == null || !originPracticeSolved.getSolved()) {

					if (originPracticeSolved == null) {
						practiceSolvedMapper.insert(practiceSolved);
					} else {
						practiceSolved.setIsPaper(false);
						practiceSolvedMapper.updateSolved(practiceSolved);
					}
				}
				paperPracticeTheoryMapper.updateById(paperPracticeTheory);
			}
		});
		return evaluateResultTheory;
	}

	/**
	 * 收集
	 *
	 * @param collectionReq 收集请求体
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean collection(CollectionReq collectionReq) {
		PaperPracticeTheory paperPracticeTheory = paperPracticeTheoryMapper.selectById(collectionReq.getBusinessId());
		CollectionContext collectionContext = BeanUtils.copy(collectionReq,
				CollectionContext.class);
		collectionContext.setUserId(UserInfoContext.getUserContext().getUserId());

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(paperPracticeTheory.getOriginId());
		if (practiceTheory == null) {
			log.error("practiceTheory is null ,BusinessId =[{}]",collectionReq.getBusinessId());
			throw new BusinessException(OjBusinessErrorCode.CANT_COLLECT_DELETE_PRACTICE);
		}

		collectionContext.setBusinessId(paperPracticeTheory.getOriginId());

		return resourceCollectionService.collectionResource(collectionContext,
				collectionReq.getIsCollection());
	}

	/**
	 * 获取练习详情
	 *
	 * @param practiceRefIds 练习裁判id列表
	 * @return {@code List<WebTheoryBo> }
	 */
	@Override
	public List<WebTheoryBo> getPracticeDetail(List<String> practiceRefIds) {
		List<WebTheoryBo> webTheoryBoList = new ArrayList<>();
		List<PaperPracticeTheory> paperPracticeTheories = paperPracticeTheoryMapper.selectBatchIds(practiceRefIds);
		if (paperPracticeTheories == null) {
			return new ArrayList<>();
		}
		Map<String, PaperPracticeTheory> refIdPaperPracticeMap = StreamApiUtils.toMap(paperPracticeTheories, PaperPracticeTheory::getResourceId, practice -> practice);
		List<String> practiceIds = StreamApiUtils.map(paperPracticeTheories, PaperPracticeTheory::getOriginId);
		Map<String, Boolean> collectMap = resourceCollectionService.selectCollectionBatchIds(UserInfoContext.getUserContext().getUserId(),
				ResourceCollection.BusinessType.THEORY.getCode(), practiceIds);
		List<PracticeTheory> practiceTheoryList = practiceTheoryMapper.selectBatchIds(practiceIds);
		List<String> practiceTheoryIds = StreamApiUtils.map(practiceTheoryList, PracticeTheory::getResourceId);

		for (String practiceRefId : practiceRefIds) {
			PaperPracticeTheory paperPracticeTheory	= refIdPaperPracticeMap.get(practiceRefId);
			PracticeTheory practiceTheory = JsonUtil.toJsonObject(paperPracticeTheory.getSnapshot(), PracticeTheory.class);
			WebTheoryBo webTheoryBo = BeanUtils.copy(practiceTheory, WebTheoryBo.class);
			webTheoryBo.setIsCollection(practiceTheoryIds.contains(practiceTheory.getResourceId()) && collectMap.get(practiceTheory.getResourceId()));
			webTheoryBo.setResourceId(practiceRefId);
			webTheoryBo.setAnswer(paperPracticeTheory.getSubmitAnswer());
			webTheoryBo.setSolved(paperPracticeTheory.getSolved());
			if (webTheoryBo.getTheoryType().equals(PracticeTheory.TheoryType.FILL_BLANK.getCode())) {
				webTheoryBo.setBlankCount(JsonUtil.toJsonObjectArray(practiceTheory.getBlank(), BlankAnswer.class).size());
			}
			webTheoryBoList.add(webTheoryBo);
		}
		return webTheoryBoList;
	}

}
