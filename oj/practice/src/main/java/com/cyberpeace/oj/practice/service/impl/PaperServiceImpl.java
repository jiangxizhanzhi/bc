package com.cyberpeace.oj.practice.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PaperService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	@Override
	public Paper create(PaperContext paperContext) {

		// 解析试卷信息
		Paper paper = BeanUtils.copy(paperContext, Paper.class);
		if (StringUtils.isBlank(paper.getResourceId())) {
			paper.setResourceId(IDGeneratorUtil.getID());
		}
		paper.setIsPublic(CommonConstant.TRUE);

		// 数据设置
		List<PracticeTheoryRef> theoryRefList = Lists.newArrayList();
		List<PracticeOperationRef> operationRefs = Lists.newArrayList();
		setPaperRelateData(paper, theoryRefList, operationRefs, paperContext.getPracticeList());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				paperMapper.insert(paper);

				if (CollectionUtils.isNotEmpty(theoryRefList)) {
					practiceTheoryRefService.saveBatch(theoryRefList);
				}
				if (CollectionUtils.isNotEmpty(operationRefs)) {
					practiceOperationRefService.saveBatch(operationRefs);
				}
			}
		});

		return paper;
	}

	@Override
	public Paper update(PaperContext paperContext) {

		// 数据拷贝
		Paper paper = BeanUtils.copy(paperContext, Paper.class);

		// 参数设置
		List<PracticeTheoryRef> theoryRefList = Lists.newArrayList();
		List<PracticeOperationRef> operationRefs = Lists.newArrayList();
		setPaperRelateData(paper, theoryRefList, operationRefs, paperContext.getPracticeList());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				paperMapper.updateById(paper);
				practiceTheoryRefMapper.deleteByPaper(paper.getResourceId());
				practiceOperationRefMapper.deleteByPaper(paper.getResourceId());

				if (CollectionUtils.isNotEmpty(theoryRefList)) {
					practiceTheoryRefService.saveBatch(theoryRefList);
				}
				if (CollectionUtils.isNotEmpty(operationRefs)) {
					practiceOperationRefService.saveBatch(operationRefs);
				}
			}
		});

		return paper;
	}

	@Override
	public IPage<LibPracticeListBo> selectLibPractice(Page page, LibPracticeQueryBo queryBo,
			AuthContext authContext) {

		List<String> permissionIds = practiceSetMapper.selectByAuthAndPublic(authContext, true);
		if (CollectionUtils.isEmpty(permissionIds)
				|| (StringUtils.isNotBlank(queryBo.getPracticeSetId())
						&& !permissionIds.contains(queryBo.getPracticeSetId()))) {
			return null;
		}

		if (StringUtils.isBlank(queryBo.getPracticeSetId())) {
			// 仅查询公开习题
			List<String> theoryIds = practiceTheoryMapper.selectBySetIds(permissionIds, true);
			List<String> operationIds = practiceOperationMapper.selectBySetIds(permissionIds, true);

			return selectAllLibPractice(theoryIds, operationIds, page);
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(queryBo.getPracticeSetId());
		if (practiceSet.getPracticeType().intValue() == PracticeCategory.PracticeType.THEORY
				.getCode()) {
			return theoryConvert(queryBo, page);
		} else {
			// 仅查询公开习题
			OperationListQuery listQuery = BeanUtils.copy(queryBo, OperationListQuery.class);
			if (StringUtils.isNotBlank(queryBo.getPracticeSetId())) {
				listQuery.setPracticeSetIds(Lists.newArrayList(queryBo.getPracticeSetId()));
			}
			IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page, listQuery,
					true);

			return iPage.convert(practiceOperation -> operationToListBo(practiceOperation));
		}
	}

	private IPage<LibPracticeListBo> selectAllLibPractice(List<String> theoryIds,
			List<String> operationIds, Page page) {

		theoryIds.addAll(operationIds);
		List<String> pageByIds = StreamApiUtils.pageByList(theoryIds,
				page.getCurrent(), page.getSize());

		if (CollectionUtils.isEmpty(pageByIds)) {
			return page;
		}
		// 查询理论习题
		List<PracticeTheory> theoryList = practiceTheoryMapper.selectBatchIds(pageByIds);
		List<LibPracticeListBo> theoryBoList = StreamApiUtils.map(theoryList,
				theory -> theoryToListBo(theory));

		// 查询实操习题
		List<PracticeOperation> operationList = practiceOperationMapper
				.selectBatchIds(pageByIds);
		List<LibPracticeListBo> operationBoList = StreamApiUtils.map(operationList,
				operation -> operationToListBo(operation));

		// 数据分页
		theoryBoList.addAll(operationBoList);

		page.setRecords(theoryBoList);
		page.setTotal(theoryIds.size());

		return page;
	}

	@Override
	public List<LibPracticeListBo> selectRandomPractice(RandomPracticeQuery practiceQuery,
			Double score, AuthContext authContext) {

		// 数据校验
		practiceValidateHandler.validatePracticeSetPermission(practiceQuery.getPracticeSetId(),
				authContext);

		PracticeSet practiceSet = practiceSetMapper.selectById(practiceQuery.getPracticeSetId());

		// 根据习题集类型不同查询不同的习题
		if (practiceSet.getPracticeType().intValue() == PracticeCategory.PracticeType.THEORY
				.getCode()) {

			List<String> practiceIds = practiceTheoryMapper
					.selectBySetIds(Lists.newArrayList(practiceQuery.getPracticeSetId()), true);
			practiceIds.removeAll(practiceQuery.getSelectIds());

			// 习题集下数据量判断
			if (practiceIds.size() < practiceQuery.getCount()) {
				log.error("practiceIds.size() < practiceQuery.getCount() ,PracticeSetId =[{}]",practiceQuery.getPracticeSetId());
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_COUNT_NOT_ENOUGH);
			}

			// 执行数据查询
			TheoryListQuery theoryListQuery = BeanUtils.copy(practiceQuery, TheoryListQuery.class);
			theoryListQuery.setResourceIds(practiceIds);
			theoryListQuery.setPracticeSetIds(Lists.newArrayList(practiceQuery.getPracticeSetId()));
			List<PracticeTheory> theories = practiceTheoryMapper.randomChoice(theoryListQuery,
					practiceQuery.getCount());

			// 其他条件下数据量判断
			if (theories.size() < practiceQuery.getCount()) {
				log.error("theories.size() < practiceQuery.getCount(),PracticeSetId =[{}]",practiceQuery.getPracticeSetId());
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_COUNT_NOT_ENOUGH);
			}

			theories.forEach(practiceTheory -> practiceTheory.setScore(score));
			return StreamApiUtils.map(theories, practiceTheory -> theoryToListBo(practiceTheory));
		} else {

			// 习题集数量校验
			List<String> practiceIds = practiceOperationMapper
					.selectBySetIds(Lists.newArrayList(practiceQuery.getPracticeSetId()), true);
			practiceIds.removeAll(practiceQuery.getSelectIds());

			// 习题集下数据量判断
			if (practiceIds.size() < practiceQuery.getCount()) {
				log.error("theories.size() < practiceQuery.getCount(),PracticeSetId =[{}]",practiceQuery.getPracticeSetId());
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_COUNT_NOT_ENOUGH);
			}

			// 实操习题数据查询
			OperationListQuery listQuery = BeanUtils.copy(practiceQuery, OperationListQuery.class);
			listQuery.setResourceIds(practiceIds);
			List<PracticeOperation> operations = practiceOperationMapper.randomChoice(listQuery,
					practiceQuery.getCount());

			// 其他条件下数据量判断
			if (operations.size() < practiceQuery.getCount()) {
				log.error("operations.size() < practiceQuery.getCount(),PracticeSetId =[{}]",practiceQuery.getPracticeSetId());
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_COUNT_NOT_ENOUGH);
			}

			operations.forEach(operation -> operation.setScore(score));
			return StreamApiUtils.map(operations, operation -> operationToListBo(operation));
		}
	}

	/**
	 * 理论习题数据转换
	 * @param queryBo
	 * @param page
	 * @return
	 */
	private IPage<LibPracticeListBo> theoryConvert(LibPracticeQueryBo queryBo, Page page) {

		TheoryListQuery theoryListQuery = BeanUtils.copy(queryBo, TheoryListQuery.class);
		if (StringUtils.isNotBlank(queryBo.getPracticeSetId())) {
			theoryListQuery.setPracticeSetIds(Lists.newArrayList(queryBo.getPracticeSetId()));
		}
		IPage<PracticeTheory> iPage = practiceTheoryMapper.pageByQuery(page, theoryListQuery, true);

		return iPage.convert(practiceTheory -> theoryToListBo(practiceTheory));
	}

	/**
	 * 实操习题数据转换为列表数据
	 * @param operation
	 * @return
	 */
	private LibPracticeListBo operationToListBo(PracticeOperation operation) {

		LibPracticeListBo listBo = BeanUtils.copy(operation, LibPracticeListBo.class);

		// 分类数据设置
		PracticeCategory practiceCategory = practiceCategoryMapper
				.selectById(operation.getCategoryId());
		if (null != practiceCategory) {
			listBo.setCategoryName(practiceCategory.getName());
		}

		// 设置习题类型
		listBo.setPracticeType(PracticeCategory.PracticeType.OPERATION.getCode());
		listBo.setQuestionType(PracticeTheory.TheoryType.OPERATION.getCode());
		return listBo;
	}

	@Override
	public Integer practiceCountByPaper(String paperId) {
		List<String> operationRefIds = practiceOperationRefMapper.selectByPaper(paperId);
		List<String> theoryRefIds = practiceTheoryRefMapper.selectByPaper(paperId);
		return operationRefIds.size() + theoryRefIds.size();
	}

	/**
	 * 基础习题数据转换为列表数据
	 * @param practiceTheory
	 * @return
	 */
	private LibPracticeListBo theoryToListBo(PracticeTheory practiceTheory) {

		LibPracticeListBo bo = BeanUtils.copy(practiceTheory, LibPracticeListBo.class);
		bo.setTitle(practiceTheory.getContent());
		bo.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
		bo.setQuestionType(practiceTheory.getTheoryType());

		// 分类数据
		PracticeCategory practiceCategory = practiceCategoryMapper
				.selectById(practiceTheory.getCategoryId());
		if (null != practiceCategory) {
			bo.setCategoryName(practiceCategory.getName());
		}

		// 填空相关数据
		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
				.getCode().intValue()) {
			List<JSONObject> objects = JsonUtil.toJsonObjectArray(practiceTheory.getBlank(),
					JSONObject.class);
			bo.setBlankCount(objects.size());
		}

		return bo;
	}

	/**
	 * 设置相关参数
	 * @param paper
	 * @param theoryRefs
	 * @param operationRefs
	 */
	private void setPaperRelateData(Paper paper, List<PracticeTheoryRef> theoryRefs,
			List<PracticeOperationRef> operationRefs,
			List<PaperPracticeContext> practiceContextList) {

		// 计算试卷基本信息
		Double totalScore = StreamApiUtils.mapToDouble(practiceContextList,
				PaperPracticeContext::getScore);
		paper.setTotalScore(totalScore);
		paper.setPracticeCount(CollectionUtils.isEmpty(practiceContextList) ? CommonConstant.ZERO
				: practiceContextList.size());

		// 解析practiceList, 设置试卷基础信息
		Map<Integer, List<PaperPracticeContext>> columnMap = StreamApiUtils
				.groupBy(practiceContextList, PaperPracticeContext::getPracticeType);

		// 生成理论习题练习
		List<PaperPracticeContext> theoryContextList = columnMap.getOrDefault(
				PracticeCategory.PracticeType.THEORY.getCode().intValue(), Lists.newArrayList());
		List<PracticeTheoryRef> theoryRefList = theoryContextToRef(theoryContextList, paper);
		theoryRefs.addAll(theoryRefList);

		// 生成实践操作练习
		List<PaperPracticeContext> operationContextList = columnMap.getOrDefault(
				PracticeCategory.PracticeType.OPERATION.getCode(), Lists.newArrayList());
		List<PracticeOperationRef> operationRefList = operationContextToRef(operationContextList,
				paper);
		operationRefs.addAll(operationRefList);

	}

	/**
	 * 理论题数据转换
	 * @param theoryContexts
	 * @param paper
	 * @return
	 */
	private List<PracticeTheoryRef> theoryContextToRef(List<PaperPracticeContext> theoryContexts,
			Paper paper) {

		// 为空则返回空集合
		if (CollectionUtils.isEmpty(theoryContexts)) {
			return Lists.newArrayList();
		}

		// 查询习题数据 查询新的以及旧的
		List<String> practiceIds = StreamApiUtils.map(theoryContexts,
				PaperPracticeContext::getResourceId);
		List<PracticeTheory> practiceList = practiceTheoryMapper.selectBatchIds(practiceIds);
		List<PracticeTheoryRef> theoryRefList = practiceTheoryRefMapper
				.selectByPracticeIds(paper.getResourceId(), practiceIds);

		// 组装引用数据
		Map<String, PaperPracticeContext> practiceMap = StreamApiUtils.toMap(theoryContexts,
				PaperPracticeContext::getResourceId, pc -> pc);
		practiceList.forEach(practiceTheory -> {

			PracticeTheoryRef theoryRef = new PracticeTheoryRef();
			practiceTheory.setScore(practiceMap.get(practiceTheory.getResourceId()).getScore());
			theoryRef.setSnapshot(JsonUtil.toJsonString(practiceTheory));
			theoryRef.setPaperId(paper.getResourceId());
			theoryRef.setOriginId(practiceTheory.getResourceId());

			theoryRef.setOptionRandom(
					practiceMap.get(practiceTheory.getResourceId()).getOptionRandom());
			//如果是传入的是先取消后增加的新习题，需要覆盖原有旧的习题
			if(StringUtils.isEmpty((practiceMap.get(practiceTheory.getResourceId()).getRefId()))){
				//遍历原有题目，如果包含此题目，将题目快照覆盖
				for (PracticeTheoryRef practiceTheoryRef : theoryRefList) {
					if(practiceTheoryRef.getOriginId().equals(theoryRef.getOriginId())){
						practiceTheoryRef.setSnapshot(theoryRef.getSnapshot());
					}
				}
			}
			theoryRef.setResourceId(IDGeneratorUtil.getID());
			theoryRefList.add(theoryRef);
		});

		return StreamApiUtils.distinctByColumn(theoryRefList, PracticeTheoryRef::getOriginId);
	}

	/**
	 * 实操习题数据
	 * @param operationContexts
	 * @param paper
	 * @return
	 */
	private List<PracticeOperationRef>
			operationContextToRef(List<PaperPracticeContext> operationContexts, Paper paper) {

		// 为空则返回空集合
		if (CollectionUtils.isEmpty(operationContexts)) {
			return Lists.newArrayList();
		}

		// 查询习题数据 查询新的以及旧的
		List<String> practiceIds = StreamApiUtils.map(operationContexts,
				PaperPracticeContext::getResourceId);
		List<PracticeOperation> practiceList = practiceOperationMapper.selectBatchIds(practiceIds);
		List<PracticeOperationRef> operationRefList = practiceOperationRefMapper
				.selectByPracticeIds(paper.getResourceId(), practiceIds);

		// 组装引用数据
		Map<String, PaperPracticeContext> practiceMap = StreamApiUtils.toMap(operationContexts,
				PaperPracticeContext::getResourceId, pc -> pc);
		practiceList.forEach(practiceOperation -> {
			PracticeOperationRef operationRef = new PracticeOperationRef();
			operationRef.setPaperId(paper.getResourceId());
			operationRef.setOriginId(practiceOperation.getResourceId());

			practiceOperation
					.setScore(practiceMap.get(practiceOperation.getResourceId()).getScore());
			operationRef.setSnapshot(JsonUtil.toJsonString(practiceOperation));

			//如果是传入的是先取消后增加的新习题，需要覆盖原有旧的习题
			if(StringUtils.isEmpty((practiceMap.get(practiceOperation.getResourceId()).getRefId()))){
				//遍历原有题目，如果包含此题目，将题目快照覆盖
				for (PracticeOperationRef practiceOperationRef : operationRefList) {
					if(practiceOperationRef.getOriginId().equals(operationRef.getOriginId())){
						practiceOperationRef.setSnapshot(operationRef.getSnapshot());
					}
				}
			}
			operationRef.setResourceId(IDGeneratorUtil.getID());
			operationRefList.add(operationRef);
		});

		return StreamApiUtils.distinctByColumn(operationRefList, PracticeOperationRef::getOriginId);
	}

}
