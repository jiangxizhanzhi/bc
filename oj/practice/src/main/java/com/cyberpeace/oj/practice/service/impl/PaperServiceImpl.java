package com.cyberpeace.oj.practice.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.service.bo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
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

	@Override
	public Paper create(PaperContext paperContext) {

		// 解析试卷信息
		Paper paper = BeanUtils.copy(paperContext, Paper.class);
		paper.setResourceId(IDGeneratorUtil.getID());
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
					practiceTheoryRefMapper.saveBatch(theoryRefList);
				}
				if (CollectionUtils.isNotEmpty(operationRefs)) {
					practiceOperationRefMapper.saveBatch(operationRefs);
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
					practiceTheoryRefMapper.saveBatch(theoryRefList);
				}
				if (CollectionUtils.isNotEmpty(operationRefs)) {
					practiceOperationRefMapper.saveBatch(operationRefs);
				}
			}
		});

		return paper;
	}

	@Override
	public IPage<LibPracticeListBo> selectLibPractice(Page page, LibPracticeQueryBo queryBo,
			AuthContext authContext) {

		List<String> permissionIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(permissionIds)
				|| (StringUtils.isNotBlank(queryBo.getPracticeSetId())
						&& !permissionIds.contains(queryBo.getPracticeSetId()))) {
			return null;
		}

		if (StringUtils.isBlank(queryBo.getPracticeSetId())) {
			List<String> theoryIds = practiceTheoryMapper.selectBySetIds(permissionIds,
					authContext.getIsWeb());
			queryBo.setResourceIds(theoryIds);
			return theoryConvert(queryBo, page, authContext);
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(queryBo.getPracticeSetId());
		if (practiceSet.getPracticeType().intValue() == PracticeCategory.PracticeType.THEORY
				.getCode()) {
			return theoryConvert(queryBo, page, authContext);
		} else {
			OperationListQuery listQuery = BeanUtils.copy(queryBo, OperationListQuery.class);
			IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page, listQuery,
					authContext.getIsWeb());

			IPage<LibPracticeListBo> convert = iPage
					.convert(practiceOperation -> operationToListBo(practiceOperation));

			return convert;
		}
	}

	@Override
	public List<LibPracticeListBo> selectRandomPractice(RandomPracticeQuery practiceQuery,
			Double score, AuthContext authContext) {

		List<String> permissionIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(permissionIds)
				|| (StringUtils.isNotBlank(practiceQuery.getPracticeSetId())
						&& !permissionIds.contains(practiceQuery.getPracticeSetId()))) {
			return null;
		}

		PracticeSet practiceSet = practiceSetMapper.selectById(practiceQuery.getPracticeSetId());
		if (practiceSet.getPracticeType().intValue() == PracticeCategory.PracticeType.THEORY
				.getCode()) {
			TheoryListQuery theoryListQuery = BeanUtils.copy(practiceQuery, TheoryListQuery.class);

			List<PracticeTheory> theories = practiceTheoryMapper.randomChoice(theoryListQuery,
					practiceQuery.getCount());
			theories.forEach(practiceTheory -> practiceTheory.setScore(score));
			return StreamApiUtils.simpleMap(theories,
					practiceTheory -> theoryToListBo(practiceTheory));
		} else {
			OperationListQuery listQuery = BeanUtils.copy(practiceQuery, OperationListQuery.class);
			List<PracticeOperation> operations = practiceOperationMapper.randomChoice(listQuery,
					practiceQuery.getCount());
			operations.forEach(operation -> operation.setScore(score));
			return StreamApiUtils.simpleMap(operations, operation -> operationToListBo(operation));
		}
	}

	private IPage<LibPracticeListBo> theoryConvert(LibPracticeQueryBo queryBo, Page page,
			AuthContext authContext) {

		TheoryListQuery theoryListQuery = BeanUtils.copy(queryBo, TheoryListQuery.class);
		IPage<PracticeTheory> iPage = practiceTheoryMapper.pageByQuery(page, theoryListQuery,
				authContext.getIsWeb());

		IPage<LibPracticeListBo> convert = iPage
				.convert(practiceTheory -> theoryToListBo(practiceTheory));

		return convert;
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

		if (CollectionUtils.isNotEmpty(practiceContextList)) {
			Double totalScore = StreamApiUtils.mapToDouble(practiceContextList,
					PaperPracticeContext::getScore);
			paper.setTotalScore(totalScore);
			paper.setPracticeCount(practiceContextList.size());

		} else {
			paper.setTotalScore(new Double(CommonConstant.ZERO));
			paper.setPracticeCount(CommonConstant.ZERO);
		}

		// 解析practiceList, 设置试卷基础信息
		Map<Integer, List<PaperPracticeContext>> columnMap = StreamApiUtils
				.groupByColumn(practiceContextList, PaperPracticeContext::getPracticeType);

		// 生成理论习题练习
		List<PaperPracticeContext> theoryContextList = columnMap.getOrDefault(
				PracticeCategory.PracticeType.THEORY.getCode().intValue(), Lists.newArrayList());
		List<PracticeTheoryRef> theoryRefList = contextToEntity(practiceTheoryMapper,
				theoryContextList, paper, PracticeTheoryRef.class);
		theoryRefs.addAll(theoryRefList);

		// 生成实践操作练习
		List<PaperPracticeContext> operationContextList = columnMap.getOrDefault(
				PracticeCategory.PracticeType.OPERATION.getCode(), Lists.newArrayList());
		List<PracticeOperationRef> operationRefList = contextToEntity(practiceOperationMapper,
				operationContextList, paper, PracticeOperationRef.class);
		operationRefs.addAll(operationRefList);

	}

	/**
	 * 参数转实体类
	 * @param baseMapper
	 * @param paperPracticeContexts
	 * @param paper
	 * @return
	 */
	private <T> List<T> contextToEntity(BaseMapper baseMapper,
			List<PaperPracticeContext> paperPracticeContexts, Paper paper, Class<T> cls) {

		// 为空则返回空集合
		if (CollectionUtils.isEmpty(paperPracticeContexts)) {
			return Lists.newArrayList();
		}

		List<String> practiceIds = StreamApiUtils.simpleMap(paperPracticeContexts,
				PaperPracticeContext::getResourceId);

		List<PracticeBase> practiceList = baseMapper.selectBatchIds(practiceIds);
		if (practiceList.size() != practiceIds.size()) {
			log.error("practice data was inconsistent with database data when creating paper!!!");
			throw new BusinessException(OjBusinessErrorCode.PARAM_DATABASE_ERROR);
		}

		Map<String, PaperPracticeContext> practiceMap = StreamApiUtils
				.columnMap(paperPracticeContexts, PaperPracticeContext::getResourceId, pc -> pc);

		List<T> refList = StreamApiUtils.simpleMap(practiceList, practiceBase -> {

			PracticeTheoryRef theoryRef = new PracticeTheoryRef();
			practiceBase.setScore(practiceMap.get(practiceBase.getResourceId()).getScore());
			theoryRef.setPaperId(paper.getResourceId());
			theoryRef.setSnapshot(JsonUtil.toJsonString(practiceBase));
			theoryRef.setOriginId(practiceBase.getResourceId());
			theoryRef.setOptionRandom(
					practiceMap.get(practiceBase.getResourceId()).getOptionRandom());
			theoryRef.setResourceId(IDGeneratorUtil.getID());

			T instance = BeanUtils.copy(theoryRef, cls);
			return instance;
		});

		return refList;
	}

}
