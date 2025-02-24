package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.*;
import com.cyberpeace.oj.practice.dao.mapper.*;
import com.cyberpeace.oj.practice.service.PaperService;
import com.cyberpeace.oj.practice.service.PracticeOperationRefService;
import com.cyberpeace.oj.practice.service.PracticeTheoryRefService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

/**
 * 纸Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see PaperService
 * @since 1.0.0
 */
@Service
@Slf4j
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {

	/**
	 * 实践理论Mapper
	 *
	 * @see PracticeTheoryMapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 实践理论裁判Mapper
	 *
	 * @see PracticeTheoryRefMapper
	 */
	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	/**
	 * 实践实操Mapper
	 *
	 * @see PracticeOperationMapper
	 */
	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	/**
	 * 实践实操裁判Mapper
	 *
	 * @see PracticeOperationRefMapper
	 */
	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	/**
	 * 纸Mapper
	 *
	 * @see PaperMapper
	 */
	@Autowired
	private PaperMapper paperMapper;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 实践类别Mapper
	 *
	 * @see PracticeCategoryMapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 实践设置Mapper
	 *
	 * @see PracticeSetMapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 实践理论裁判Service
	 *
	 * @see PracticeTheoryRefService
	 */
	@Autowired
	private PracticeTheoryRefService practiceTheoryRefService;

	/**
	 * 实践实操裁判Service
	 *
	 * @see PracticeOperationRefService
	 */
	@Autowired
	private PracticeOperationRefService practiceOperationRefService;

	/**
	 * 实践验证处理器
	 *
	 * @see PracticeValidateHandler
	 */
	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	/**
	 * 创造
	 *
	 * @param paperContext 纸张上下文
	 * @return {@code Paper }
	 * @throws
	 * @see PaperContext
	 * @see Paper
	 */
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

	/**
	 * 更新
	 *
	 * @param paperContext 纸张上下文
	 * @return {@code Paper }
	 * @throws
	 * @see PaperContext
	 * @see Paper
	 */
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

	/**
	 * 查询自由实践
	 *
	 * @param page        分页
	 * @param queryBo     查询 bo
	 * @param authContext auth 上下文
	 * @return {@code IPage<LibPracticeListBo> }
	 * @throws
	 * @see Page
	 * @see LibPracticeQueryBo
	 * @see AuthContext
	 * @see IPage<LibPracticeListBo>
	 */
	@Override
	public IPage<LibPracticeListBo> selectLibPractice(Page page, LibPracticeQueryBo queryBo,
			AuthContext authContext) {
		return theoryConvert(queryBo, page);

//		List<String> permissionIds = practiceSetMapper.selectByAuthAndPublic(authContext, true);
//		if (CollectionUtils.isEmpty(permissionIds)
//				|| (StringUtils.isNotBlank(queryBo.getPracticeSetId())
//						&& !permissionIds.contains(queryBo.getPracticeSetId()))) {
//			return null;
//		}
//
//		if (StringUtils.isBlank(queryBo.getPracticeSetId())) {
//			// 仅查询公开习题
//			List<String> theoryIds = practiceTheoryMapper.selectBySetIds(permissionIds, true);
//			List<String> operationIds = practiceOperationMapper.selectBySetIds(permissionIds, true);
//
//			return selectAllLibPractice(theoryIds, operationIds, page);
//		}
//
//		PracticeSet practiceSet = practiceSetMapper.selectById(queryBo.getPracticeSetId());
//		if (practiceSet.getPracticeType().intValue() == PracticeCategory.PracticeType.THEORY
//				.getCode()) {
//			return theoryConvert(queryBo, page);
//		} else {
//			// 仅查询公开习题
//			OperationListQuery listQuery = BeanUtils.copy(queryBo, OperationListQuery.class);
//			if (StringUtils.isNotBlank(queryBo.getPracticeSetId())) {
//				listQuery.setPracticeSetIds(Lists.newArrayList(queryBo.getPracticeSetId()));
//			}
//			IPage<PracticeOperation> iPage = practiceOperationMapper.pageByQuery(page, listQuery,
//					true);
//
//			return iPage.convert(practiceOperation -> operationToListBo(practiceOperation));
//		}
	}

	/**
	 * 查询都自由实践
	 *
	 * @param theoryIds    理论id列表
	 * @param operationIds 实操id列表
	 * @param page         分页
	 * @return {@code IPage<LibPracticeListBo> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 * @see Page
	 * @see IPage<LibPracticeListBo>
	 */
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

	/**
	 * 查询随机实践
	 *
	 * @param practiceQuery 练习查询
	 * @param score         得分
	 * @param authContext   auth 上下文
	 * @return {@code List<LibPracticeListBo> }
	 * @throws
	 * @see RandomPracticeQuery
	 * @see Double
	 * @see AuthContext
	 * @see List<LibPracticeListBo>
	 */
	@Override
	public List<LibPracticeListBo> selectRandomPractice(RandomPracticeQuery practiceQuery,
			Double score, AuthContext authContext) {

		// 数据校验

		List<String> practiceIds = practiceTheoryMapper
				.selectBySetIds(Lists.newArrayList(practiceQuery.getPracticeSetId()), true);
		practiceIds.removeAll(practiceQuery.getSelectIds());

		// 习题集下数据量判断
		if (practiceIds.size() < practiceQuery.getCount()) {
			log.error("practiceIds.size() < practiceQuery.getCount() ,PracticeSetId =[{}]",
					practiceQuery.getPracticeSetId());
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
			log.error("theories.size() < practiceQuery.getCount(),PracticeSetId =[{}]",
					practiceQuery.getPracticeSetId());
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_COUNT_NOT_ENOUGH);
		}

		theories.forEach(practiceTheory -> practiceTheory.setScore(score));
		return StreamApiUtils.map(theories, practiceTheory -> theoryToListBo(practiceTheory));
	}

	/**
	 * 理论习题数据转换
	 *
	 * @param queryBo
	 * @param page
	 * @return
	 * @throws
	 * @see LibPracticeQueryBo
	 * @see Page
	 * @see IPage<LibPracticeListBo>
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
	 *
	 * @param operation
	 * @return
	 * @throws
	 * @see PracticeOperation
	 * @see LibPracticeListBo
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
	 * 实践计数由纸
	 *
	 * @param paperId 纸id
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	@Override
	public Integer practiceCountByPaper(String paperId) {
		List<String> operationRefIds = practiceOperationRefMapper.selectByPaper(paperId);
		List<String> theoryRefIds = practiceTheoryRefMapper.selectByPaper(paperId);
		return operationRefIds.size() + theoryRefIds.size();
	}

	/**
	 * 基础习题数据转换为列表数据
	 *
	 * @param practiceTheory
	 * @return
	 * @throws
	 * @see PracticeTheory
	 * @see LibPracticeListBo
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
	 *
	 * @param paper
	 * @param theoryRefs
	 * @param operationRefs       实操裁判
	 * @param practiceContextList 练习上下文列表
	 * @throws
	 * @see Paper
	 * @see List<PracticeTheoryRef>
	 * @see List<PracticeOperationRef>
	 * @see List<PaperPracticeContext>
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
	 *
	 * @param theoryContexts
	 * @param paper
	 * @return
	 * @throws
	 * @see List<PaperPracticeContext>
	 * @see Paper
	 * @see List<PracticeTheoryRef>
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
	 *
	 * @param operationContexts
	 * @param paper
	 * @return
	 * @throws
	 * @see List<PaperPracticeContext>
	 * @see Paper
	 * @see List<PracticeOperationRef>
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
