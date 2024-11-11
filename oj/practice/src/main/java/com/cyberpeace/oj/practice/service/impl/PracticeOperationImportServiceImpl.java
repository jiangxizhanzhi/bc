package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationImportService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 练习实操进口Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Slf4j(topic = "resourceImport")
@Service
public class PracticeOperationImportServiceImpl implements PracticeOperationImportService {

	/**
	 * 练习实操Service
	 */
	@Autowired
	private PracticeOperationService practiceOperationService;

	/**
	 * 练习验证处理器
	 */
	@Autowired
	private PracticeValidateHandler practiceValidateHandler;

	/**
	 * 附件Service
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 练习类别Mapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 练习理论Service
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 最大内容长度
	 */
	private Integer MAX_CONTENT_LENGTH;

	/**
	 * 最大类别长度
	 */
	private Integer MAX_CATEGORY_LENGTH;

	/**
	 * 被收集
	 */
	private final Boolean IS_COLLECTED = Boolean.FALSE;

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
	 * 解析课时实操
	 *
	 * @param path         路径
	 * @param practiceTopo 练习拓扑
	 * @return {@code List<PracticeOperation> }
	 */
	@Override
	public List<PracticeOperation> parseLessonOperation(String path,
			Map<String, String> practiceTopo) {

		File rootFile = new File(path);
		if (!rootFile.exists()) {
			return Lists.newArrayList();
		}

		// 遍历习题数据处理
		List<String> ctfPracticeDirs = FileUtil.listDirs(path, false);
		if (CollectionUtils.isEmpty(ctfPracticeDirs)) {
			return Lists.newArrayList();
		}

		// 构建数据存储
		PracticeSet practiceSet = new PracticeSet();
		List<PracticeOperation> operationList = Lists.newArrayList();

		// 执行数据导入
		for (String ctfPracticeDir : ctfPracticeDirs) {
			log.info("MD Operation import start , practicePath =[{}] ", ctfPracticeDir);
			PracticeOperation operation = this.parseLessonPracticeCtf(ctfPracticeDir, practiceTopo,
					practiceSet);
			operationList.add(operation);
		}

		// 数据去重
		return StreamApiUtils.distinctByColumn(operationList, PracticeOperation::getTitle);
	}

	/**
	 * 解析课时练习CTF
	 *
	 * @param path        路径
	 * @param envReqs     env reqs
	 * @param practiceSet 练习设置
	 * @return {@code PracticeOperation }
	 */
	@Override
	public PracticeOperation parseLessonPracticeCtf(String path, Map<String, String> envReqs,
			PracticeSet practiceSet) {
		File operationFile = new File(path, ResourceImportConstant.README_NAME);
		if (!operationFile.isFile()) {
			log.error("Invalid practice file not found [{}]", operationFile.getAbsolutePath());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, operationFile.getAbsolutePath(), "文件未找到");
		}

		JSONObject operationData = MdUtil.parseJsonMarkdown(operationFile);

		JSONObject convertData = JsonUtil.filedConvert(operationData,
				ResourceImportConstant.OPERATION_PRACTICE_DATA_MAPPING);
		String envKey = new File(path).getName();
		String topoId = envReqs.get(envKey);

		String attachmentPath = new File(path, ResourceImportConstant.PRACTICE_ATTACHMENT)
				.getAbsolutePath();
		List<String> fileNames = FileUtil.listFileNamesByDir(attachmentPath, false);
		PracticeOperation operation = jsonParseOperation(convertData, practiceSet, topoId,
				fileNames, operationFile.getAbsolutePath());

		practiceValidateHandler.validateAttachmentLength(operation.getTitle(),
				operation.getDynamicFlag(), fileNames);

		// 附件处理
		List<String> attachments = FileUtil.listDirs(attachmentPath, true);
		if (CollectionUtils.isNotEmpty(attachments)) {
			List<String> attachmentIds = StreamApiUtils.map(attachments, attachment -> {
				AttachmentBo attachmentBo = attachmentService.copyFile(attachment,
						AttachmentPath.PRACTICE_ATTACHMENT.getCode(), true, true);
				return attachmentBo.getResourceId();
			});

			operation.setAttachmentId(String.join(CommonConstant.CODE_COMMA, attachmentIds));
		}

		// 题解数据处理
		File writeUpDirFile = new File(path, ResourceImportConstant.WRITEUP);
		String writeFilePath = FileUtil.getOneFile(writeUpDirFile.getAbsolutePath(),
				FileSunffixConstant.MD.getDesc());
		if (StringUtils.isNotBlank(writeFilePath)) {
			String writeup = MdUtil.parseMarkdown(writeFilePath);
			writeup = utilService.handlerMarkdownFile(writeup,
					AttachmentPath.PRACTICE_ATTACHMENT.getDesc());
			operation.setWriteup(writeup);
		}
		operation.setWriteupPublic(CommonConstant.TRUE);

		return operation;
	}

	/**
	 * 解析 rm ctf
	 *
	 * @param path        路径
	 * @param envReqs     env reqs
	 * @param practiceSet 练习设置
	 * @return {@code PracticeOperation }
	 */
	@Override
	public PracticeOperation parseRmCtf(String path, Map<String, String> envReqs,
			PracticeSet practiceSet) {
		File operationFile = new File(path, ResourceImportConstant.README_NAME);
		if (!operationFile.isFile()) {
			log.error("ctf practice README.md file not found, file is [{}]", operationFile.getAbsolutePath());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, operationFile.getAbsolutePath(), "文件没有找到");
		}
		JSONObject operationData = MdUtil.parseJsonMarkdown(operationFile);

		JSONObject convertData = JsonUtil.filedConvert(operationData,
				ResourceImportConstant.OPERATION_PRACTICE_DATA_MAPPING);
		String envKey = new File(path).getName();
		String topoId = envReqs.get(envKey);

		String attachmentPath = new File(path, ResourceImportConstant.PRACTICE_ATTACHMENT)
				.getAbsolutePath();
		List<String> fileNames = FileUtil.listFileNamesByDir(attachmentPath, false);
		PracticeOperation operation = jsonParseOperation(convertData, practiceSet, topoId,
				fileNames, operationFile.getAbsolutePath());

		// 校验实操习题标题
		practiceValidateHandler.validateOperationTitleExist(practiceSet.getResourceId(),
				operation.getTitle(), operationFile.getAbsolutePath());

		practiceValidateHandler.validateAttachmentLength(operation.getTitle(),
				operation.getDynamicFlag(), fileNames, operationFile.getAbsolutePath());

		// 附件处理
		List<String> attachments = FileUtil.listDirs(attachmentPath, true);
		if (CollectionUtils.isNotEmpty(attachments)) {
			List<String> attachmentIds = StreamApiUtils.map(attachments, attachment -> {
				AttachmentBo attachmentBo = attachmentService.copyFile(attachment,
						AttachmentPath.PRACTICE_ATTACHMENT.getCode(), true, true);
				return attachmentBo.getResourceId();
			});

			operation.setAttachmentId(String.join(CommonConstant.CODE_COMMA, attachmentIds));
		}

		// 题解数据处理
		File writeUpDirFile = new File(path, ResourceImportConstant.WRITEUP);
		String writeFilePath = FileUtil.getOneFile(writeUpDirFile.getAbsolutePath(),
				FileSunffixConstant.MD.getDesc());
		if (StringUtils.isNotBlank(writeFilePath)) {
			String writeup = MdUtil.parseMarkdown(writeFilePath);
			writeup = utilService.handlerMarkdownFile(writeup,
					AttachmentPath.PRACTICE_ATTACHMENT.getDesc());
			operation.setWriteup(writeup);
		}
		operation.setWriteupPublic(CommonConstant.TRUE);

		return operation;
	}

	/**
	 * 格式化实操习题
	 *
	 * @param operationData
	 * @param practiceSet
	 * @param topoId            拓扑id
	 * @param attachments       附件
	 * @param operationFilePath 实操文件路径
	 * @return
	 */
	private PracticeOperation jsonParseOperation(JSONObject operationData, PracticeSet practiceSet,
			String topoId, List<String> attachments, String operationFilePath) {

		PracticeOperation operation = new PracticeOperation();
		// 分类数据处理
		String categoryName = ((String) operationData.remove("category_id")).trim();
		if (StringUtils.isBlank(categoryName)) {
			log.error("Practice category is empty! filePath is[{}],  operation_data=[{}]",
					operationFilePath, JsonUtil.toJsonString(operationData));
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, operationFilePath, "方向不能为空");
		}
		String categoryId = practiceCategoryMapper
				.selectByName(PracticeCategory.PracticeType.OPERATION.getCode(), categoryName);
		if (StringUtils.isBlank(categoryId)) {
			PracticeCategory practiceCategory = new PracticeCategory();
			practiceCategory.setResourceId(IDGeneratorUtil.getID());
			practiceCategory.setName(categoryName);
			practiceCategory.setPracticeType(PracticeCategory.PracticeType.OPERATION.getCode());
			practiceCategory.setCode(practiceCategory.getResourceId());
			practiceCategory.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
			practiceCategoryMapper.insert(practiceCategory);

			operation.setCategoryId(practiceCategory.getResourceId());
		} else {
			operation.setCategoryId(categoryId);
		}

		// 知识点数据处理
		String knowledgeStr = (String) operationData.remove("knowledge");
		operation.setKnowledge(handlerMultiContent(knowledgeStr));

		// 场景flag相关操作
		String flag = (String) operationData.remove("answer");

		// 场景flag类型
		Integer flagType = flagType(attachments, flag);
		String answerStr = handlerMultiContent(flag);
		operation.setAnswer(handlerAnswer(flagType, attachments, answerStr));
		operation.setDynamicFlag(flagType);

		// 设置场景共享类型默认值
		operation.setDynamicEnv(StringUtils.isNotBlank(topoId));
		String sceneShareMode = (String) operationData.remove("scene_share_mode");
		operation.setSceneShareMode(sceneShareMode(sceneShareMode));

		if (StringUtils.isNotBlank(topoId)) {
			operation.setTopoId(topoId);
		}
		// 销毁时间默认设置
		operation.setDestroyDelay(CommonConstant.TWO);

		// 难度处理
		String difficultyStr = (String) operationData.remove("difficulty");
		Integer difficulty = EnumUtils.getEnumCodeByDesc(difficultyStr, Difficulty.class);
		if (difficulty == null) {
			log.error("Invalid practice difficulty!, difficulty is [{}]", difficultyStr);
			final List<String> listDesc = EnumUtils.getListDesc(Difficulty.class);
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, operationFilePath, "难度不在范围" + listDesc.toString());
		}
		operation.setDifficulty(difficulty);

		operation.setScore(CommonConstant.HUNDRED.doubleValue());
		operation.setResourceId(IDGeneratorUtil.getID());
		operation.setCreateUserId(ResourceImportConstant.SUPER_ADMIN_HASH);
		operation.setBuiltin(CommonConstant.TRUE);
		operation.setPracticeSetId(practiceSet.getResourceId());
		operation.setIsPublic(CommonConstant.TRUE);

		PracticeOperation practiceOperation = operationData.toJavaObject(PracticeOperation.class);
		operation.setTitle(practiceOperation.getTitle());
		operation.setContent(practiceOperation.getContent());

		return operation;

	}

	/**
	 * Handler 多内容
	 *
	 * @param filedContent 归档内容
	 * @return {@code String }
	 */
	private String handlerMultiContent(String filedContent) {

		if (StringUtils.isBlank(filedContent)
				|| filedContent.equals(CommonConstant.SHORT_TRANSVERSE_LINE)) {
			return JsonUtil.arrayToString(Lists.newArrayList());
		}

		String[] multiContent = filedContent.split(PatternConstant.KNOWLEDGE_SPLIT_STR);
		List<String> contents = StreamApiUtils.map(Arrays.asList(multiContent),
				content -> content.trim());
		List<String> result = StreamApiUtils.filter(contents,
				content -> StringUtils.isNotBlank(content));

		return JsonUtil.arrayToString(result);
	}

	/**
	 * 处理实操题答案
	 *
	 * @param flagType    标志类型
	 * @param attachments 附件
	 * @param answers     答案
	 * @return {@code String }
	 */
	private String handlerAnswer(Integer flagType, List<String> attachments, String answers) {

		if (flagType.intValue() != PracticeOperation.DynamicFlag.RANDOM.getCode()) {
			return answers;
		}

		List<String> answer = JsonUtil.toJsonObjectArray(answers, String.class);
		List<String> flags = StreamApiUtils.map(attachments, attachment -> {
			int indexOf = answer.indexOf(attachment);
			return answer.get(indexOf + CommonConstant.ONE);
		});

		return JsonUtil.arrayToString(flags);
	}

	/**
	 * 计算flag类型
	 *
	 * @param attachments 附件
	 * @param flag        flag
	 * @return {@code Integer }
	 */
	private Integer flagType(List<String> attachments, String flag) {

		if (StringUtils.isBlank(flag)) {
			return PracticeOperation.DynamicFlag.DYNAMIC.getCode();
		}

		if (CollectionUtils.isNotEmpty(attachments)
				&& flag.contains(attachments.get(CommonConstant.ZERO))) {
			return PracticeOperation.DynamicFlag.RANDOM.getCode();
		}

		return PracticeOperation.DynamicFlag.STATIC.getCode();
	}

	/**
	 * 场景共享模式
	 *
	 * @param sceneShareMode 场景共享模式
	 * @return {@code Integer }
	 */
	private Integer sceneShareMode(String sceneShareMode) {
		return StringUtils.isNotBlank(sceneShareMode) && sceneShareMode.equals(CommonConstant.RIGHT)
				? SceneShare.SHARED.getCode()
				: SceneShare.PRIVATE.getCode();
	}
}
