package com.cyberpeace.oj.practice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalBo.ExternalTheoryCountBo;
import com.cyberpeace.oj.practice.service.externalBo.TheorySimpleBo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.service.RemoteUserService;
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

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import static com.cyberpeace.common.constant.CommonConstant.VERTICAL_LINE;

@Service
@Slf4j
public class PracticeTheoryServiceImpl extends ServiceImpl<PracticeTheoryMapper, PracticeTheory> implements PracticeTheoryService {

	private Integer MAX_CONTENT_LENGTH;

	private final Boolean IS_COLLECTED = Boolean.FALSE;

	@PostConstruct
	private void init() {
		MAX_CONTENT_LENGTH = Integer
				.parseInt(ConfigUtil.getContextProperty("oj.practice.content.max.length"));
	}

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

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Override
	public IPage<PracticeTheoryListBo> pageByQuery(Page page, TheoryListQueryBo listQuery,
			AuthContext authContext) {

		// 过滤无权限习题集
		List<String> authSetIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(authSetIds)) {
			return null;
		}

		// 过滤非公开习题集
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), authContext.getIsWeb());
		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
		authSetIds.retainAll(setIds);
		if (CollectionUtils.isEmpty(authSetIds)
				|| (StringUtils.isNotBlank(listQuery.getPracticeSetId())
						&& !authSetIds.contains(listQuery.getPracticeSetId()))) {
			return null;
		}

		TheoryListQuery theoryListQuery = BeanUtils.copy(listQuery, TheoryListQuery.class);
		theoryListQuery.setQuestionType(listQuery.getTheoryType());
		// 根据习题集过滤习题编号
		if (StringUtils.isNotBlank(listQuery.getPracticeSetId())) {
			authSetIds.clear();
			authSetIds.add(listQuery.getPracticeSetId());
		}
		List<String> practiceIds = practiceTheoryMapper.selectBySetIds(authSetIds,
				authContext.getIsWeb());

		if (CollectionUtils.isEmpty(practiceIds)) {
			return null;
		}
		theoryListQuery.setResourceIds(practiceIds);

		// 答题记录过滤习题
		if (authContext.getIsWeb() && null != listQuery.getSolved()) {
			List<String> solvedIds = practiceSolvedMapper.selectUserAndSolved(
					authContext.getUserId(), PracticeCategory.PracticeType.THEORY.getCode(),
					CommonConstant.TRUE, IS_COLLECTED);

			// 求交集 空则返回为空
			if (listQuery.getSolved()) {
				theoryListQuery.getResourceIds().retainAll(solvedIds);
			} else {
				theoryListQuery.getResourceIds().removeAll(solvedIds);
			}

			// 资源数据判空
			if (CollectionUtils.isEmpty(theoryListQuery.getResourceIds())) {
				return null;
			}
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
							practiceTheory.getResourceId(), IS_COLLECTED);
					theoryListBo.setSolved(null != practiceSolved ? practiceSolved.getSolved()
							: CommonConstant.FALSE);
				}
			}

			UserInfo userInfo = remoteUserService.getById(practiceTheory.getCreateUserId());
			theoryListBo.setCreateUserName(null != userInfo ? userInfo.getUserName() : "");
			PracticeSet practiceSet = practiceSetMapper
					.selectById(practiceTheory.getPracticeSetId());
			theoryListBo.setPracticeSetName(null != practiceSet ? practiceSet.getName() : "");
			return theoryListBo;
		});

		return listBoIPage;
	}

	@Override
	public WebTheoryBo selectById(String resourceId, String userId, Boolean isCollected) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(resourceId);
		if (null == practiceTheory) {
			log.error("Theory practice not found! practice_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		WebTheoryBo webTheoryBo = BeanUtils.copy(practiceTheory, WebTheoryBo.class);

		Boolean result = resourceCollectionService.selectByUnique(userId,
				ResourceCollection.BusinessType.THEORY.getCode(), resourceId);
		webTheoryBo.setIsCollection(null == result ? CommonConstant.FALSE : result);

		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
				.getCode().intValue()) {
			webTheoryBo.setBlankCount(JsonUtil
					.toJsonObjectArray(practiceTheory.getBlank(), BlankAnswer.class).size());
		}

		PracticeSolved practiceSolved = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.THEORY.getCode(), resourceId, isCollected);

		answerToNull(webTheoryBo, practiceSolved);

		return webTheoryBo;
	}

	/**
	 * 答案置空
	 *
	 * @param webTheoryBo
	 * @param practiceSolved
	 */
	private void answerToNull(WebTheoryBo webTheoryBo, PracticeSolved practiceSolved) {

		if (null == practiceSolved) {
			answerToNull(webTheoryBo);
		} else {
			PracticeSolvedDetailBo detailBo = new PracticeSolvedDetailBo();
			if (practiceSolved.getSolved()) {
				detailBo.setCorrectAnswer(webTheoryBo.getAnswer());
				detailBo.setIsSolved(practiceSolved.getSolved());
				detailBo.setSubmitAnswer(practiceSolved.getSubmitAnswer());
				if (webTheoryBo.getTheoryType().intValue() == PracticeTheory.TheoryType.SHORT_ANSWER
						.getCode().intValue()) {
					detailBo.setKeyword(webTheoryBo.getKeyword());
				}
				webTheoryBo.setResolvedDetail(detailBo);
			} else {
				PracticeNoSolvedBo practiceNoSolvedBo = new PracticeNoSolvedBo();
				practiceNoSolvedBo.setIsSolved(practiceSolved.getSolved());
				answerToNull(webTheoryBo);
				webTheoryBo.setResolvedDetail(practiceNoSolvedBo);
			}
		}

	}

	/**
	 * 答案置空
	 *
	 * @param webTheoryBo
	 */
	private void answerToNull(WebTheoryBo webTheoryBo) {
		webTheoryBo.setAnswer(null);
		webTheoryBo.setBlank(null);
		webTheoryBo.setKeyword(null);
	}

	@Override
	public EvaluateResult submitAnswer(String userId, String theoryId, String userAnswer,
			Boolean isCollected) {

		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(theoryId);
		if (null == practiceTheory) {
			log.info("Theory practice not found! practice_id=[{}]", theoryId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 判断是否已完成答题
		PracticeSolved solvedPractice = practiceSolvedMapper.selectUserAndId(userId,
				PracticeCategory.PracticeType.THEORY.getCode(), theoryId, isCollected);
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
		try {
			practiceSolvedMapper.insert(practiceSolved);
		} catch (DuplicateKeyException e) {
			practiceSolvedMapper.updateSolved(practiceSolved);
		}

		return evaluateResult;
	}

	@Override
	public Integer create(PracticeTheory practiceTheory, AuthContext authContext) {

		// 数据校验
		practiceValidateHandler.validateTheoryContentExist(practiceTheory.getPracticeSetId(),
				practiceTheory.getContent());
		practiceValidateHandler.validateCategoryType(practiceTheory.getCategoryId(),
				PracticeCategory.PracticeType.THEORY.getCode());
		PracticeSet practiceSet = practiceValidateHandler.validateSetType(
				practiceTheory.getPracticeSetId(), PracticeSet.PracticeType.THEORY.getCode());

		// 基本数据设置
		practiceTheory.setBuiltin(CommonConstant.FALSE);
		practiceTheory.setScore(CommonConstant.ZERO.doubleValue());
		practiceSet.setPracticeCount(practiceSet.getPracticeCount() + CommonConstant.ONE);

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				practiceSetMapper.updateById(practiceSet);
				return practiceTheoryMapper.insert(practiceTheory);
			}
		});
	}

	@Override
	public int updateTheory(PracticeTheory practiceTheory, AuthContext authContext) {

		// 数据校验
		practiceValidateHandler.validateTheoryContentExistExcludeItself(practiceTheory.getPracticeSetId(),
				practiceTheory.getContent(), practiceTheory);
		practiceValidateHandler.validateCategoryType(practiceTheory.getCategoryId(),
				PracticeCategory.PracticeType.THEORY.getCode());
		practiceValidateHandler.validateSetType(practiceTheory.getPracticeSetId(),
				PracticeSet.PracticeType.THEORY.getCode());

		// 数据更新
		return practiceTheoryMapper.updateById(practiceTheory);
	}

	@Override
	public List<String> selectPracticeListIds(AuthContext authContext) {

		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return Lists.newArrayList();
		}

		List<PracticeSet> practiceSets = practiceSetMapper.selectBatchIds(setIds);
		List<String> publicIds = practiceSets.stream()
				.filter(practiceSet -> practiceSet.getIsPublic())
				.map(practiceSet -> practiceSet.getResourceId()).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(publicIds)) {
			return Lists.newArrayList();
		}

		return practiceTheoryMapper.selectBySetIds(publicIds, authContext.getIsWeb());
	}

	@Override
	public List<PracticeTheory> parseRmTheory(String path, PracticeSet practiceSet) {
		// 获取excel数据
		String oneFile = FileUtil.getOneFile(path, FileSunffixConstant.XLS.getDesc());
		if (StringUtils.isBlank(oneFile)) {
			oneFile = FileUtil.getOneFile(path, FileSunffixConstant.XLSX.getDesc());
		}
		ExcelDataHandler dataHandler = new ExcelDataHandler(oneFile, CommonConstant.ZERO);
		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);

		dataHandler.closeIO();
		// 数据类型转换
		List<PracticeTheory> theoryList = StreamApiUtils.map(sheetData,
				rowData -> excelDataToEntity(rowData, practiceSet, null));

		// 数据去重
		List<PracticeTheory> distinctList = StreamApiUtils.distinctByColumn(theoryList,
				practiceTheory -> practiceTheory.getContent().toUpperCase(Locale.ROOT));
		return distinctList;
	}

	@Override
	public Integer batchDelete(List<String> resourceIds) {
		String practiceId = resourceIds.get(CommonConstant.ZERO);
		PracticeTheory theory = practiceTheoryMapper.selectById(practiceId);

		// 习题集数据更新
		PracticeSet practiceSet = practiceValidateHandler
				.validatePracticeSetExist(theory.getPracticeSetId());
		practiceSet.setPracticeCount(null == practiceSet.getPracticeCount()
				|| practiceSet.getPracticeCount() <= resourceIds.size() ? CommonConstant.ZERO
						: practiceSet.getPracticeCount() - resourceIds.size());

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				practiceSetMapper.updateById(practiceSet);
				return practiceTheoryMapper.deleteBatchIds(resourceIds);
			}
		});
	}

	@Override
	public Boolean importTheory(String practiceSetId, String filePath, String userId) {
		// 基本数据校验
		PracticeSet practiceSet = practiceValidateHandler.validateSetType(practiceSetId,
				PracticeSet.PracticeType.THEORY.getCode());

		// 数据读取
		ExcelDataHandler dataHandler = new ExcelDataHandler(filePath, CommonConstant.ZERO);
		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);
		List<PracticeTheory> theories = StreamApiUtils.map(sheetData,
				data -> excelDataToEntity(data, practiceSet, userId));

		if (CollectionUtils.isEmpty(theories)) {
			return CommonConstant.TRUE;
		}

		// 根据内容去重 防止数据多插入
		List<PracticeTheory> theoryList = StreamApiUtils.distinctByColumn(theories,
				PracticeTheory::getContent);

		practiceSet.setPracticeCount(practiceSet.getPracticeCount() + theoryList.size());

		return transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				practiceSetMapper.updateById(practiceSet);
				return saveBatch(theoryList);
			}
		});
	}

	@Override
	public PracticeTheory loadPracticeTheory(String practiceSetId, PracticeTheoryDumpLoadBo dumpBo,
			String userId) {
		// 重复则不导入
		String practiceId = practiceTheoryMapper.selectByContent(practiceSetId,
				dumpBo.getContent());
		if (StringUtils.isNotBlank(practiceId)) {
			log.error("Practice theory exist! practice_id=[{}], practice_name=[{}]", practiceId,
					dumpBo.getContent());
			return null;
		}

		PracticeTheory practiceTheory = BeanUtils.copy(dumpBo, PracticeTheory.class);
		practiceTheory.setPracticeSetId(practiceSetId);
		practiceTheory.setBuiltin(CommonConstant.TRUE);
		practiceTheory.setResourceId(IDGeneratorUtil.getID());
		practiceTheory.setCreateUserId(userId);
		// 增加判断 添加默认值 防止前端无法格式化数据
		if (StringUtils.isBlank(dumpBo.getKeyword())) {
			practiceTheory.setKeyword(CommonConstant.EMPTY_LIST_JSON);
		}
		if (StringUtils.isBlank(dumpBo.getKnowledge())) {
			practiceTheory.setKnowledge(CommonConstant.EMPTY_LIST_JSON);
		}
		if (StringUtils.isBlank(dumpBo.getOption())) {
			practiceTheory.setOption(CommonConstant.EMPTY_OBJECT);
		}

		// 分类数据
		String categoryId = practiceCategoryService.selectCategoryOrCreate(dumpBo.getCategoryName(),
				PracticeCategory.PracticeType.THEORY.getCode(), userId);
		practiceTheory.setCategoryId(categoryId);

		return practiceTheory;
	}

	@Override
	public PracticeTheoryDumpLoadBo dumpTheory(PracticeTheory practiceTheory) {
		PracticeTheoryDumpLoadBo dumpLoadBo = BeanUtils.copy(practiceTheory,
				PracticeTheoryDumpLoadBo.class);

		// 分类数据处理
		PracticeCategory category = practiceValidateHandler
				.validateCategoryExist(practiceTheory.getCategoryId());
		dumpLoadBo.setCategoryName(category.getName());
		return dumpLoadBo;
	}

	/**
	 * 实训理论习题资源导入excel数据转换
	 *
	 * @param excelRowData
	 * @param practiceSetId
	 * @param categoryId
	 * @return
	 */
	private PracticeTheory ojExcelDataToEntity(List<String> excelRowData, String practiceSetId,
			String categoryId, String userId) {

		if (StringUtils.isBlank(String.join("", excelRowData))) {
			return null;
		}

		// 字段数据长度限制
		String content = excelRowData.get(0);
		practiceValidateHandler.validateFieldLength(content, MAX_CONTENT_LENGTH);

		// 该条数据存在也不插入
		String practiceId = practiceTheoryMapper.selectByContent(practiceSetId, content);
		if (StringUtils.isNotBlank(practiceId)) {
			return null;
		}

		// 理论习题内容
		PracticeTheory practiceTheory = new PracticeTheory();
		practiceTheory.setContent(content);
		practiceTheory.setTitle(content);
		practiceTheory.setCategoryId(categoryId);
		practiceTheory.setPracticeSetId(practiceSetId);
		practiceTheory.setScore(CommonConstant.ZERO.doubleValue());
		practiceTheory.setBuiltin(CommonConstant.TRUE);
		practiceTheory.setCreateUserId(userId);
		practiceTheory.setIsPublic(CommonConstant.TRUE);
		practiceTheory.setResourceId(IDGeneratorUtil.getID());

		// excel数据中题型=当前代码枚举值+1
		Integer theoryType = handleTheoryType(excelRowData.get(1));
		practiceTheory.setTheoryType(theoryType);
		
		// excel数据中难度=比当前代码枚举值+1
		practiceTheory.setDifficulty(Difficulty.PRIMARY.getCode());

		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.SINGLE.getCode()
				|| practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.MULTIPLE
						.getCode().intValue()) {
			String answer = handleChoiceAnswer(excelRowData.get(2));
			practiceTheory.setAnswer(answer);
			String option = formatOption(excelRowData, 3);
			practiceTheory.setOption(option);
		}

		// 判断题
		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.JUDGMENT
				.getCode().intValue()) {
			practiceValidateHandler.validateJudgementAnswer(excelRowData.get(2));
			practiceTheory.setAnswer(excelRowData.get(2));
			practiceTheory.setOption(JsonUtil.toJsonString(null));
		}

		// 填空题
		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
				.getCode().intValue()) {
			String blankAnswers = handleFillBlank(excelRowData, 3);
			practiceTheory.setBlank(blankAnswers);
			practiceTheory.setAnswer(blankAnswers);
		}

		// 简答题
		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.SHORT_ANSWER
				.getCode().intValue()) {
			String shortAnswer = excelRowData.get(2);
			String keywordsString = "";
			if (excelRowData.size() > 3) {
				keywordsString = excelRowData.get(3);
			}
			if (StringUtils.isNotBlank(keywordsString)) {
				List<String> keywords = Lists
						.newArrayList(keywordsString.split(PatternConstant.VERTICAL_LINE));
				practiceTheory.setKeyword(JsonUtil.arrayToString(keywords));
			} else {
				practiceTheory.setKeyword(CommonConstant.EMPTY_LIST_JSON);
			}

			practiceTheory.setAnswer(shortAnswer);

		}

		return practiceTheory;
	}

	/**
	 * 处理填空题答案
	 * @param excelRowData
	 * @param index
	 * @return
	 */
	private String handleFillBlank(List<String> excelRowData, int index) {
		practiceValidateHandler.validateFillBlankAnswerLength(excelRowData, index);

		// 获取填空题答案
		List<String> blanks = Lists.newArrayList();
		String answers = excelRowData.get(index + 1);
		String[] split = answers.split(PatternConstant.VERTICAL_LINE);
		for (String blank : split) {
			if (StringUtils.isBlank(blank)) {
				break;
			}
			blanks.add(blank);
		}

		Integer weight = CommonConstant.HUNDRED / blanks.size();
		Integer remainder = CommonConstant.HUNDRED % blanks.size();

		// 填空题答案数据组装
		List<BlankAnswer> blankAnswers = Lists.newArrayList();
		for (int i = 0; i < blanks.size(); i++) {
			BlankAnswer blankAnswer = new BlankAnswer();
			blankAnswer.setValue(blanks.get(i));
			if (i == (blanks.size() - CommonConstant.ONE)
					&& remainder.intValue() != CommonConstant.ZERO) {
				blankAnswer.setWeight(weight + remainder);
			} else {
				blankAnswer.setWeight(weight);
			}
			blankAnswers.add(blankAnswer);
		}

		return JsonUtil.arrayToString(blankAnswers);
	}

	/**
	 * 处理选择题答案
	 * @param answer
	 * @return
	 */
	private String handleChoiceAnswer(String answer) {
		practiceValidateHandler.validateChoiceAnswer(answer);

		if (answer.length() > 1 && !answer.contains("|")) {
			String[] answerArray = answer.split("");
			answer = String.join("|", answerArray);
		}
		
		return answer;
	}
	
	/**
	 * 处理习题类型
	 * @param theoryType 习题类型
	 * @return
	 */
	private Integer handleTheoryType(String theoryType) {

		Integer realType;

		try {
			Double practiceType = new Double(theoryType);
			realType = practiceType.intValue() - 1;
		} catch (Exception e) {
			log.error("Parse theory type failed! value=[{}] catch error:", theoryType, e);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_TYPE_ERROR);
		}

		practiceValidateHandler.validateTheoryType(realType);
		return realType;
	}



	/**
	 * excel数据转化为实体类
	 *
	 * @param excelRowData
	 * @return
	 */
	private PracticeTheory excelDataToEntity(List<String> excelRowData, PracticeSet practiceSet, String userId) {

		// 数据长度为零 返回空过滤掉
		if (StringUtils.isBlank(String.join("", excelRowData))) {
			return null;
		}

		PracticeTheory practiceTheory = new PracticeTheory();
		practiceTheory.setContent(excelRowData.get(0));
		practiceTheory.setTitle(excelRowData.get(0));

		String practiceId = practiceTheoryMapper.selectByContent(practiceSet.getResourceId(),
				practiceTheory.getContent());
		if (StringUtils.isNotBlank(practiceId)) {
			return null;
		}

		// excel数据中难度=比当前代码枚举值+1
		practiceTheory.setDifficulty(new Double(excelRowData.get(2)).intValue() - 1);
		// excel数据中题型=当前代码枚举值+1
		Integer theoryType = handleTheoryType(excelRowData.get(3));
		practiceTheory.setTheoryType(theoryType);

		// 分类编号设置 不存在创建
		String categoryName = excelRowData.get(1);
		if (StringUtils.isBlank(categoryName = categoryName.trim())) {
			log.error("Practice category is empty! operation_data=[{}]",
					JsonUtil.toJsonString(excelRowData));
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_CATEGORY_TYPE_ERROR);
		}

		String categoryId = practiceCategoryMapper
				.selectByName(PracticeCategory.PracticeType.THEORY.getCode(), categoryName);
		if (StringUtils.isBlank(categoryId)) {
			PracticeCategory category = new PracticeCategory();
			category.setResourceId(IDGeneratorUtil.getID());
			category.setCode(category.getResourceId());
			// 跟页面导入区分人员
			category.setCreateUserId(null==userId ? ResourceImportConstant.SUPER_ADMIN_HASH : userId);
			category.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
			category.setName(excelRowData.get(1));
			practiceCategoryMapper.insert(category);

			practiceTheory.setCategoryId(category.getResourceId());
		} else {
			practiceTheory.setCategoryId(categoryId);
		}

		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.SINGLE.getCode()
				|| practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.MULTIPLE
						.getCode().intValue()) {
			String answer = handleChoiceAnswer(excelRowData.get(4));
			String option = formatOption(excelRowData, 5);
			practiceTheory.setOption(option);
			practiceTheory.setAnswer(answer);
		}

		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.JUDGMENT
				.getCode().intValue()) {
			practiceValidateHandler.validateJudgementAnswer(excelRowData.get(4));
			practiceTheory.setAnswer(excelRowData.get(4));
			practiceTheory.setOption(JsonUtil.toJsonString(null));
		}

		// 参照页面导入解析填空题
		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.FILL_BLANK
				.getCode().intValue()) {
			String blankAnswers = handleFillBlank(excelRowData, 3);
			practiceTheory.setBlank(blankAnswers);
			practiceTheory.setAnswer(blankAnswers);

		}

		// 参考页面导入解析简答题
		if (practiceTheory.getTheoryType().intValue() == PracticeTheory.TheoryType.SHORT_ANSWER
				.getCode().intValue()) {
			String shortAnswer = excelRowData.get(4);
			if(StringUtils.isBlank(shortAnswer)){
				throw new BusinessException(OjBusinessErrorCode.SHORT_ANSWER_ANSWER_EMPTY_ERROR);
			}
			practiceTheory.setAnswer(shortAnswer);

		}

		practiceTheory.setScore(CommonConstant.ONE.doubleValue());
		practiceTheory.setBuiltin(CommonConstant.TRUE);
		// 跟页面导入区分人员
		practiceTheory.setCreateUserId(null==userId ? ResourceImportConstant.SUPER_ADMIN_HASH : userId);
		practiceTheory.setIsPublic(CommonConstant.TRUE);
		practiceTheory.setPracticeSetId(practiceSet.getResourceId());
		practiceTheory.setResourceId(IDGeneratorUtil.getID());

		return practiceTheory;
	}

	@Override
	public void createMpLog(String practiceId, LogContent logContent) {
		PracticeTheory practiceTheory = practiceTheoryMapper.selectById(practiceId);
		String content = LogContent.THEORY_FORMAT.format(practiceTheory.getContent(), logContent);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.THEORY,
				OperationType.EXERCISE, LogContent.THEORY.getMsg(), content);
	}

	@Override
	public ExternalTheoryCountBo typeCount(TheoryListQueryBo theoryListQueryBo, AuthContext authContext) {


		List<String> setIds = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(setIds)) {
			return new ExternalTheoryCountBo();
		}

		String searchSet = theoryListQueryBo.getPracticeSetId();
		if (StringUtils.isNotBlank(searchSet)) {
			if (setIds.contains(searchSet)) {
				setIds.clear();
				setIds.add(searchSet);
			} else {
				return new ExternalTheoryCountBo();
			}
		}

		// 设置查询条件
		TheoryListQuery listQuery = BeanUtils.copy(theoryListQueryBo, TheoryListQuery.class);
		listQuery.setPracticeSetIds(setIds);

		// 数据查询转换
		List<TheorySimpleBo> bos = practiceTheoryMapper.selectSimple(listQuery, true);
		Map<Integer, List<TheorySimpleBo>> typeMap = StreamApiUtils.groupBy(bos,
				TheorySimpleBo::getTheoryType);
		ExternalTheoryCountBo countBo = new ExternalTheoryCountBo(typeMap);
		return countBo;
	}

	private String formatOption(List<String> excelRowData, Integer start) {
		if (CollectionUtils.isEmpty(excelRowData)) {
			return CommonConstant.EMPTY_OBJECT;
		}

		practiceValidateHandler.validateChoiceAnswerLength(excelRowData, start);
		if (excelRowData.size() - CommonConstant.FIVE > 26) {
			throw new BusinessException(OjBusinessErrorCode.CHOICE_OPTION_TOO_MANY);
		}

		JSONObject options = new JSONObject();
		for (char i = 'A'; i <= 'Z'; i++) {
			int index = i - 'A' + start;
			if (index >= excelRowData.size()) {
				break;
			}

			String option = excelRowData.get(index);
			if (StringUtils.isBlank(option)) {
				break;
			}
			options.put(String.valueOf(i), option);
		}

		return JsonUtil.toJsonString(options);
	}

}
