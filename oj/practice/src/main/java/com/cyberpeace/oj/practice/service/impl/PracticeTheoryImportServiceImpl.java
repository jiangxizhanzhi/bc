package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.ResourceDumpConstant;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeTheoryImportService;
import com.cyberpeace.oj.practice.service.bo.BlankAnswer;
import com.cyberpeace.oj.practice.service.md.MdTheoryParser;
import com.cyberpeace.oj.practice.service.md.MdTheoryPractice;
import com.cyberpeace.oj.practice.service.md.TheoryBaseMd;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 练习理论进口Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Slf4j(topic = "resourceImport")
@Service
public class PracticeTheoryImportServiceImpl extends ServiceImpl<PracticeTheoryMapper, PracticeTheory> implements PracticeTheoryImportService {

	/**
	 * 练习类别Service
	 */
	@Autowired
	private PracticeCategoryService practiceCategoryService;

	/**
	 * 练习理论Mapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 练习验证处理器
	 */
	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	/**
	 * 练习类别Mapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 练习设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 最大内容长度
	 */
	private Integer MAX_CONTENT_LENGTH;

	/**
	 * 最大类别长度
	 */
	private Integer MAX_CATEGORY_LENGTH;

	/**
	 * 初始化
	 */
	@PostConstruct
	private void init() {
		MAX_CONTENT_LENGTH = Integer
				.parseInt(ConfigUtil.getContextProperty("oj.practice.content.max.length"));
		MAX_CATEGORY_LENGTH = Integer
				.parseInt(ConfigUtil.getContextProperty("oj.practice.category.max.length"));
	}

	/**
	 * 解析课时理论Markdown
	 *
	 * @param lessonFilePah    课时文件咚咚
	 * @param mdTheoryPractice Markdown理论练习
	 * @return {@code List<PracticeTheory> }
	 */
	@Override
	public List<PracticeTheory> parseLessonTheoryMd(String lessonFilePah,
			MdTheoryPractice mdTheoryPractice) {
		List<PracticeTheory> theories = mdDataToEntity(lessonFilePah,
				mdTheoryPractice.getSingleList(), PracticeTheory.TheoryType.SINGLE.getCode());
		theories.addAll(mdDataToEntity(lessonFilePah, mdTheoryPractice.getMultipleList(),
				PracticeTheory.TheoryType.MULTIPLE.getCode()));
		theories.addAll(mdDataToEntity(lessonFilePah, mdTheoryPractice.getBlankList(),
				PracticeTheory.TheoryType.FILL_BLANK.getCode()));
		theories.addAll(mdDataToEntity(lessonFilePah, mdTheoryPractice.getJudgeList(),
				PracticeTheory.TheoryType.JUDGMENT.getCode()));
		theories.addAll(mdDataToEntity(lessonFilePah, mdTheoryPractice.getAnwserList(),
				PracticeTheory.TheoryType.SHORT_ANSWER.getCode()));

		return theories;
	}

	/**
	 * 解析马币理论
	 *
	 * @param path        路径
	 * @param practiceSet 练习设置
	 * @return {@code List<PracticeTheory> }
	 */
	@Override
	public List<PracticeTheory> parseRmTheory(String path, PracticeSet practiceSet) {
		// 获取excel数据
		String oneFile = FileUtil.getOneFile(path, FileSunffixConstant.XLS.getDesc());
		if (StringUtils.isBlank(oneFile)) {
			oneFile = FileUtil.getOneFile(path, FileSunffixConstant.XLSX.getDesc());
		}
		final File file = new File(oneFile);
		if (StringUtils.isBlank(oneFile) || !file.isFile()) {
			log.error("Theory [xls, xlsx] file not found, path is [{}]", path);
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, path, "没有找到[xls,xlsx]文件");
		}
		log.info("start import theory file [{}]", oneFile);

		ExcelDataHandler dataHandler = new ExcelDataHandler(oneFile, CommonConstant.ZERO);
		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);

		dataHandler.closeIO();
		// 数据类型转换
		List<PracticeTheory> theoryList = StreamApiUtils.map(sheetData,
				rowData -> excelDataToEntity(rowData, practiceSet, null));

		// 数据去重
		return StreamApiUtils.distinctByColumn(theoryList,
				practiceTheory -> practiceTheory.getContent().toUpperCase(Locale.ROOT));
	}

	/**
	 * 解析课时理论胜过
	 *
	 * @param path 路径
	 * @return {@code List<PracticeTheory> }
	 */
	@Override
	public List<PracticeTheory> parseLessonTheoryExcel(String path) {

		// 获取excel数据
		ExcelDataHandler dataHandler = new ExcelDataHandler(path, CommonConstant.ZERO);
		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);
		dataHandler.closeIO();

		// 数据类型转换
		return StreamApiUtils.map(sheetData, rowData -> excelDataToLessonTheory(rowData, null));
	}

	/**
	 * 进口理论
	 *
	 * @param practiceSetId 练习设置id
	 * @param filePath      文件路径
	 * @param userId        用户id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean importTheory(String practiceSetId, String filePath, String userId) {
		// 基本数据校验
		PracticeSet practiceSet = practiceValidateHandler.validateSetType(practiceSetId,
				PracticeSet.PracticeType.THEORY.getCode());

		// 数据读取
		ExcelDataHandler dataHandler = new ExcelDataHandler(filePath, CommonConstant.ZERO);
		List<List<String>> sheetData = dataHandler.getSheetData(CommonConstant.ONE);
		List<Integer> rowsRead = Lists.newArrayList();
		List<PracticeTheory> theories = StreamApiUtils.map(sheetData, data -> {
			try {
				rowsRead.add(1);
				return excelDataToEntity(data, practiceSet, userId);
			} catch (NumberFormatException e) {
				log.error("import theory invalid ! ", e);
				throw new BusinessException(OjBusinessErrorCode.IMPORT_THEORY_INVALID, rowsRead.size());
			}
		});

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

	/**
	 * 实训理论习题资源导入excel数据转换
	 *
	 * @param excelRowData
	 * @param practiceSetId
	 * @param categoryId
	 * @param userId        用户id
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
	 * 将excel数据转换为实体类
	 *
	 * @param excelRowData
	 * @param userId
	 * @return
	 */
	private PracticeTheory excelDataToLessonTheory(List<String> excelRowData, String userId) {

		// 数据长度为零 返回空过滤掉
		if (StringUtils.isBlank(String.join("", excelRowData))) {
			return null;
		}

		PracticeTheory practiceTheory = new PracticeTheory();
		String content = excelRowData.get(0);
		CrCommonValidator.validateLength(content, CrCommonConstants.ZERO_STR_LEN,
				MAX_CONTENT_LENGTH, CrBusinessErrorCode.ILLEGAL_TITLE_LEN);

		practiceTheory.setContent(content);
		practiceTheory.setTitle(content);

		// excel数据中难度=比当前代码枚举值+1
		practiceTheory.setDifficulty(handleTheoryDifficulty(excelRowData.get(2)));
		// excel数据中题型=当前代码枚举值+1
		Integer theoryType = handleTheoryType(excelRowData.get(3));
		practiceTheory.setTheoryType(theoryType);

		// 分类编号设置 不存在创建
		String categoryName = excelRowData.get(1).trim();
		if (StringUtils.isBlank(categoryName)) {
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
			category.setCreateUserId(
					null == userId ? ResourceImportConstant.SUPER_ADMIN_HASH : userId);
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
			if (StringUtils.isBlank(shortAnswer)) {
				log.error("shortAnswer isBlank !excelRowData=[{}] ", excelRowData);
				throw new BusinessException(OjBusinessErrorCode.SHORT_ANSWER_ANSWER_EMPTY_ERROR);
			}
			practiceTheory.setAnswer(shortAnswer);
		}

		practiceTheory.setScore(CommonConstant.ONE.doubleValue());
		practiceTheory.setBuiltin(CommonConstant.TRUE);
		// 跟页面导入区分人员
		practiceTheory
				.setCreateUserId(null == userId ? ResourceImportConstant.SUPER_ADMIN_HASH : userId);
		practiceTheory.setIsPublic(CommonConstant.TRUE);
		practiceTheory.setResourceId(IDGeneratorUtil.getID());

		return practiceTheory;
	}

	/**
	 * 处理习题类型
	 *
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
	 * @param practiceSet  练习设置
	 * @param userId       用户id
	 * @return
	 */
	private PracticeTheory excelDataToEntity(List<String> excelRowData, PracticeSet practiceSet,
			String userId) {

		// 数据长度为零 返回空过滤掉
		if (StringUtils.isBlank(String.join("", excelRowData))) {
			return null;
		}

		PracticeTheory practiceTheory = new PracticeTheory();
		String content = excelRowData.get(0);
		CrCommonValidator.validateLength(content, CrCommonConstants.ZERO_STR_LEN,
				MAX_CONTENT_LENGTH, CrBusinessErrorCode.ILLEGAL_TITLE_LEN);

		practiceTheory.setContent(content);
		practiceTheory.setTitle(content);

		String practiceId = practiceTheoryMapper.selectByContent(practiceSet.getResourceId(),
				practiceTheory.getContent());
		if (StringUtils.isNotBlank(practiceId)) {
			final int min = Math.min(practiceTheory.getContent().length(), 20);
			log.info("this theory practice exists, content is [{}]", practiceTheory.getContent().substring(0, min) +"...");
			return null;
		}

		// excel数据中难度=比当前代码枚举值+1
		practiceTheory.setDifficulty(handleTheoryDifficulty(excelRowData.get(2)));
		// excel数据中题型=当前代码枚举值+1
		Integer theoryType = handleOnlyTheoryType(excelRowData.get(3));
		practiceTheory.setTheoryType(theoryType);

		// 分类编号设置 不存在创建
		String categoryName = excelRowData.get(1).trim();
		if (StringUtils.isBlank(categoryName)) {
			log.error("Practice category is empty! operation_data=[{}]",
					JsonUtil.toJsonString(excelRowData));
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_DIRECTION_EMPTY_ERROR);
		}
		// 校验方向长度
		CrCommonValidator.validateLength(categoryName, CrCommonConstants.ZERO_STR_LEN,
				MAX_CATEGORY_LENGTH, CrBusinessErrorCode.ILLEGAL_TITLE_LEN);

		String categoryId = practiceCategoryMapper
				.selectByName(PracticeCategory.PracticeType.THEORY.getCode(), categoryName);
		if (StringUtils.isBlank(categoryId)) {
			PracticeCategory category = new PracticeCategory();
			category.setResourceId(IDGeneratorUtil.getID());
			category.setCode(category.getResourceId());
			// 跟页面导入区分人员
			category.setCreateUserId(
					null == userId ? ResourceImportConstant.SUPER_ADMIN_HASH : userId);
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
			if (StringUtils.isBlank(shortAnswer)) {
				log.error("shortAnswer isBlank !excelRowData=[{}] ", excelRowData);
				throw new BusinessException(OjBusinessErrorCode.SHORT_ANSWER_ANSWER_EMPTY_ERROR);
			}
			practiceTheory.setAnswer(shortAnswer);

		}

		practiceTheory.setScore(CommonConstant.ONE.doubleValue());
		practiceTheory.setBuiltin(CommonConstant.FALSE);
		// 跟页面导入区分人员
		practiceTheory
				.setCreateUserId(null == userId ? ResourceImportConstant.SUPER_ADMIN_HASH : userId);
		practiceTheory.setIsPublic(CommonConstant.TRUE);
		practiceTheory.setPracticeSetId(practiceSet.getResourceId());
		practiceTheory.setResourceId(IDGeneratorUtil.getID());

		return practiceTheory;
	}

	/**
	 * 处理填空题答案
	 *
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
	 * 处理选项内容
	 *
	 * @param excelRowData
	 * @param start
	 * @return
	 */
	private String formatOption(List<String> excelRowData, Integer start) {
		if (CollectionUtils.isEmpty(excelRowData)) {
			return CommonConstant.EMPTY_OBJECT;
		}

		practiceValidateHandler.validateChoiceAnswerLength(excelRowData, start);
		if (excelRowData.size() - CommonConstant.FIVE > 26) {
			log.error("excelRowData.size() - CommonConstant.FIVE > 26 !excelRowData=[{}] ", JsonUtil.toJsonString(excelRowData));
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

	/**
	 * 处理选择题答案
	 *
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
	 * 处理理论习题类型
	 *
	 * @param theoryType 习题类型
	 * @return
	 */
	private Integer handleOnlyTheoryType(String theoryType) {

		Integer realType;
		try {
			Double practiceType = new Double(theoryType);
			realType = practiceType.intValue() - 1;
		} catch (Exception e) {
			log.error("Parse theory type failed! value=[{}] catch error:", theoryType, e);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_TYPE_ERROR);
		}

		practiceValidateHandler.validateOnlyTheoryType(realType);
		return realType;
	}

	/**
	 * 处理习题难度
	 *
	 * @param theoryDifficulty 习题难度
	 * @return
	 */
	private Integer handleTheoryDifficulty(String theoryDifficulty) {
		Integer realDifficulty;

		try {
			Double practiceDifficulty = new Double(theoryDifficulty);
			realDifficulty = practiceDifficulty.intValue() - 1;
		} catch (Exception e) {
			log.error("Parse theory type failed! value=[{}] catch error:", theoryDifficulty, e);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_DIFFICULTY_ERROR);
		}

		practiceValidateHandler.validateCategoryDifficulty(realDifficulty);
		return realDifficulty;

	}

	/**
	 * 将md文件内容转换为实体类
	 *
	 * @param lessonFilePath
	 * @param theoryBaseMdList
	 * @param code
	 * @return
	 */
	public List<PracticeTheory> mdDataToEntity(String lessonFilePath,
			List<TheoryBaseMd> theoryBaseMdList, Integer code) {

		List<PracticeTheory> practiceTheories = StreamApiUtils.map(theoryBaseMdList,
				theoryBaseMd -> {
					PracticeTheory practiceTheory = BeanUtils.copy(theoryBaseMd,
							PracticeTheory.class);

					practiceTheory.setCreateUserId(UserInfoContext.getUserContext().getUserId());
					practiceTheory.setTheoryType(code);
					practiceTheory.setBuiltin(CommonConstant.TRUE);
					practiceTheory.setScore(1.0);
					practiceTheory.setAnswer(theoryBaseMd.getAnswer().trim());
					practiceTheory.setIsPublic(true);
					practiceTheory.setScore(0.0);
					theoryBaseMd.setTitle(
							MdTheoryParser.setNewFIlePath(lessonFilePath, theoryBaseMd.getTitle()));
					practiceTheory.setTitle(theoryBaseMd.getTitle());
					practiceTheory.setContent(theoryBaseMd.getTitle());

					Integer difficulty = EnumUtils.getEnumCodeByDesc(theoryBaseMd.getDifficulty(),
							Difficulty.class);
					practiceTheory.setDifficulty(difficulty);

					// 分类数据
					String categoryId = practiceCategoryService.selectCategoryOrCreate(
							theoryBaseMd.getCategory(),
							PracticeCategory.PracticeType.THEORY.getCode(),
							UserInfoContext.getUserContext().getUserId());

					practiceTheory.setCategoryId(categoryId);

					// 单选需要获取选项内容
					if (PracticeTheory.TheoryType.SINGLE.getCode().equals(code)) {
						changeTitleToTheory(practiceTheory, theoryBaseMd);
					}

					// 多选需要获取选项内容,修改答案
					if (PracticeTheory.TheoryType.MULTIPLE.getCode().equals(code)) {
						changeTitleToTheory(practiceTheory, theoryBaseMd);

						String[] split = practiceTheory.getAnswer().split("");

						for (int i = 1; i < split.length; i++) {
							split[0] += "|" + split[i];
						}
						practiceTheory.setAnswer(split[0]);
					}

					// 填空需要修改答案
					if (PracticeTheory.TheoryType.FILL_BLANK.getCode().equals(code)) {

						String[] split = practiceTheory.getAnswer().split("\n");
						List<BlankAnswer> blankAnswers = Lists.newArrayList();
						int[] weights = getWeight(split);
						for (int i = 0; i < split.length; i++) {
							String contentIndex = split[i];
							Pattern r = Pattern.compile(PatternConstant.BLANK_ANWSER_STR);
							Matcher m = r.matcher(contentIndex);

							BlankAnswer blankAnswer = new BlankAnswer();

							blankAnswer.setWeight(weights[i]);

							blankAnswers.add(blankAnswer);
							if (m.matches()) {
								blankAnswer.setValue(m.group(1));
							}
						}

						practiceTheory.setAnswer(JsonUtil.toJsonString(blankAnswers));
						practiceTheory.setBlank(JsonUtil.toJsonString(blankAnswers));

					}
					// 判断需要修改答案
					if (PracticeTheory.TheoryType.JUDGMENT.getCode().equals(code)) {

						String answer = practiceTheory.getAnswer().equals("T") ? "A" : "B";

						practiceTheory.setAnswer(answer);

					}
					String practiceId = practiceTheoryMapper.selectByContent("",
							practiceTheory.getContent());
					if (StringUtils.isNotBlank(practiceId)) {
						log.error("Practice theory exist! practice_id=[{}], practice_name=[{}]",
								practiceId, practiceTheory.getContent());
						return null;
					}
					practiceTheory.setResourceId(IDGeneratorUtil.getID());

					return practiceTheory;
				});
		return practiceTheories;

	}

	/**
	 * 计算权重
	 *
	 * @param split
	 * @return
	 */
	private static int[] getWeight(String[] split) {
		int[] weights = new int[split.length];
		int m = 100 / split.length, i = 0, sum = 0;
		for (; i < split.length - 1; i++) {
			weights[i] = m;
			sum += m;
		}
		weights[i] = 100 - sum;

		return weights;
	}

	/**
	 * 改变标题自理论
	 *
	 * @param practiceTheory 练习理论
	 * @param theoryBaseMd   理论基础Markdown
	 */
	private void changeTitleToTheory(PracticeTheory practiceTheory, TheoryBaseMd theoryBaseMd) {

		String[] split = theoryBaseMd.getTitle().split("\n");
		List<String> content = Arrays.asList(split);
		Map<String, String> titleMap = Maps.newTreeMap();
		String title = "";

		// 遍历content数据
		String key = CommonConstant.BLANK_STR;
		Integer startIndex = CommonConstant.NEGATIVE_ONE;

		for (int index = 0; index < content.size(); index++) {

			String contentIndex = content.get(index);
			Pattern r = Pattern.compile(PatternConstant.THEORY_CHOICE_ANSWER);
			Matcher m = r.matcher(contentIndex);

			if (m.matches()) {
				// 标题数据获取
				if (CommonConstant.BLANK_STR.equals(key)) {
					startIndex = 0;
					title = ListUtil.joinLineSeparatorList(content, startIndex, index).trim();

				} else if (startIndex != CommonConstant.NEGATIVE_ONE) {
					String value = ListUtil.joinLineSeparatorList(content, startIndex, index);

					titleMap.put(key, value.trim().substring(3));
				}

				// 数据校验
				key = m.group(1);
				startIndex = index;
			}

			if (index + 1 == content.size()) {
				if (startIndex == -1) {
					log.error("Pattern not match", content.toString());
					startIndex = 0;
				}
				String value = ListUtil.joinLineSeparatorList(content, startIndex, content.size());
				titleMap.put(key, value.trim().substring(3));
			}
		}

		practiceTheory.setTitle(title);
		practiceTheory.setContent(title);

		practiceTheory.setOption(JsonUtil.toJsonString(titleMap));

	}
}
