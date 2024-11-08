package com.cyberpeace.oj.practice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.SystemConfigService;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实践验证处理器
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class PracticeValidateHandler {

	/**
	 * 实践设置Mapper
	 */
	@Autowired
	private PracticeSetMapper practiceSetMapper;

	/**
	 * 实践类别Mapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 实践实操Mapper
	 */
	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	/**
	 * 实践理论Mapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * OJ当地场景Mapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 系统配置Service
	 */
	@Autowired
	private SystemConfigService systemConfigService;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 校验导入时选择题答案长度
	 *
	 * @param excelRowData
	 * @param index        指数
	 */
	public void validateChoiceAnswerLength(List<String> excelRowData, int index) {
		if (CollectionUtils.isEmpty(excelRowData) || excelRowData.size() - 1 <= index) {
			log.error("choice option too little!excelRowData=[{}] ", JsonUtil.toJsonString(excelRowData));
			throw new BusinessException(OjBusinessErrorCode.CHOICE_OPTION_TOO_LITTLE);
		}

		if (StringUtils.isBlank(excelRowData.get(index))
				|| StringUtils.isBlank(excelRowData.get(index + 1))) {
			log.error("choice option too little!excelRowData=[{}] ", JsonUtil.toJsonString(excelRowData));
			throw new BusinessException(OjBusinessErrorCode.CHOICE_OPTION_TOO_LITTLE);
		}

	}

	/**
	 * 校验导入时填空题答案数量
	 *
	 * @param excelRowData Excel 行数据
	 * @param index        指数
	 */
	public void validateFillBlankAnswerLength(List<String> excelRowData, int index) {
		if (CollectionUtils.isEmpty(excelRowData) || excelRowData.size() - 1 < index) {
			log.error("fill blank too little!excelRowData=[{}] ", JsonUtil.toJsonString(excelRowData));
			throw new BusinessException(OjBusinessErrorCode.FILL_BLANK_TOO_LITTLE);
		}
		if (StringUtils.isBlank(excelRowData.get(index + 1))) {
			log.error("fill blank too little!excelRowData=[{}] ", JsonUtil.toJsonString(excelRowData));
			throw new BusinessException(OjBusinessErrorCode.FILL_BLANK_TOO_LITTLE);
		}
	}

	/**
	 * 校验导入时选择题
	 *
	 * @param answer 答案
	 */
	public void validateChoiceAnswer(String answer) {
		if (StringUtils.isBlank(answer) || !(answer.matches(PatternConstant.CHOICE_ANSWER))) {
			log.error("The format of the answer:[{}] is wrong", answer);
			throw new BusinessException(OjBusinessErrorCode.CHOICE_ANSWER_ERROR);
		}
	}

	/**
	 * 校验导入时判断题
	 *
	 * @param answer 答案
	 */
	public void validateJudgementAnswer(String answer) {
		if (StringUtils.isBlank(answer) || !(answer.matches(PatternConstant.JUDGEMENT_ANSWER))) {
			log.error("The format of the answer:[{}] is wrong", answer);
			throw new BusinessException(OjBusinessErrorCode.JUDGEMENT_ANSWER_ERROR);
		}
	}

	/**
	 * 校验习题难度
	 *
	 * @param difficulty 习题难度
	 */
	public void validateCategoryDifficulty(Integer difficulty) {
		boolean right = false;
		for (Difficulty difficulty1 : Difficulty.values()) {
			right |= difficulty1.getCode().intValue() == difficulty;
		}

		if (!right) {
			log.error("Theory difficulty error! difficulty=[{}]", difficulty);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_DIFFICULTY_ERROR);
		}

	}

	/**
	 * 校验习题类型
	 *
	 * @param type 习题类型值
	 */
	public void validateTheoryType(int type) {

		boolean right = false;
		for (PracticeTheory.TheoryType theoryType : PracticeTheory.TheoryType.values()) {
			right |= theoryType.getCode().intValue() == type;
		}

		if (!right) {
			log.error("Theory type error! type=[{}]", type);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_TYPE_ERROR);
		}
	}

	/**
	 * 校验习题类型
	 *
	 * @param type 习题类型值
	 */
	public void validateOnlyTheoryType(int type) {

		boolean right = false;
		PracticeTheory.TheoryType[] values = PracticeTheory.TheoryType.values();
		List<PracticeTheory.TheoryType> collect = Arrays.stream(values).filter(
				item -> !item.getCode().equals(PracticeTheory.TheoryType.OPERATION.getCode()))
				.collect(Collectors.toList());
		for (PracticeTheory.TheoryType theoryType : collect) {
			right |= theoryType.getCode().intValue() == type;
		}

		if (!right) {
			log.error("Theory type error! type=[{}]", type);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_TYPE_ERROR);
		}
	}

	/**
	 * 字段数据长度校验
	 *
	 * @param fieldContent
	 * @param maxLength    最大长度
	 */
	public void validateFieldLength(String fieldContent, Integer maxLength) {
		if (fieldContent.length() > maxLength) {
			log.error("field length too long!");
			throw new BusinessException(OjBusinessErrorCode.CONTENT_FILED_DATA_TOO_LONG);
		}
	}

	/**
	 * 校验操作题是否存在
	 *
	 * @param practiceOperationId
	 * @return
	 */
	public PracticeOperation validateOperationExist(String practiceOperationId) {
		PracticeOperation operation = practiceOperationMapper.selectById(practiceOperationId);

		if (null == operation) {
			log.error("Practice Operation not found! practice_operation_id=[{}]",
					practiceOperationId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return operation;
	}

	/**
	 * 校验习题分类类型
	 *
	 * @param categoryId
	 * @param practiceType
	 * @return
	 */
	public PracticeCategory validateCategoryType(String categoryId, Integer practiceType) {
		PracticeCategory practiceCategory = validateCategoryExist(categoryId);

		if (practiceCategory.getPracticeType().intValue() != practiceType.intValue()) {
			log.error(
					"Practice Category type error! practice_category_id=[{}], category_type=[{}], practice_type=[{}]",
					categoryId, practiceCategory.getPracticeType(), practiceType);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_CATEGORY_TYPE_ERROR);
		}

		return practiceCategory;
	}

	/**
	 * 校验分类是否存在
	 *
	 * @param categoryId
	 * @return
	 */
	public PracticeCategory validateCategoryExist(String categoryId) {
		PracticeCategory category = practiceCategoryMapper.selectById(categoryId);
		if (null == category) {
			log.error("Practice category not found! practice_category_id=[{}]", categoryId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return category;
	}

	/**
	 * 校验习题集类型
	 *
	 * @param practiceSetId
	 * @param practiceType
	 * @return
	 */
	public PracticeSet validateSetType(String practiceSetId, Integer practiceType) {

		PracticeSet practiceSet = validatePracticeSetExist(practiceSetId);

		if (practiceSet.getPracticeType().intValue() != practiceType.intValue()) {
			log.error(
					"Practice set type error! practice_set_id=[{}], set_practice_type=[{}], practice_type=[{}]",
					practiceSetId, practiceSet.getPracticeType(), practiceType);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_TYPE_ERROR);
		}

		return practiceSet;
	}

	/**
	 * 校验习题集是否存在
	 *
	 * @param practiceSetId 实践设置id
	 * @return
	 */
	public PracticeSet validatePracticeSetExist(String practiceSetId) {
		PracticeSet practiceSet = practiceSetMapper.selectById(practiceSetId);
		if (null == practiceSet) {
			log.error("PracticeSet not found! practice_id=[{}]", practiceSetId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return practiceSet;
	}

	/**
	 * 习题集权限校验
	 *
	 * @param practiceSetId
	 * @param authContext   auth 上下文
	 */
	public void validatePracticeSetPermission(String practiceSetId, AuthContext authContext) {

		List<String> permissionSets = practiceSetMapper.selectByAuthContext(authContext);
		if (CollectionUtils.isEmpty(permissionSets) || !permissionSets.contains(practiceSetId)) {
			log.error("Practice set not permission! practice_set_id=[{}]", practiceSetId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}
	}

	/**
	 * 校验习题名称
	 *
	 * @param practiceSetId
	 * @param title         标题
	 */
	public void validateOperationTitleExist(String practiceSetId, String title) {
		String practiceId = practiceOperationMapper.selectByTitle("", title);
		if (StringUtils.isNotBlank(practiceId)) {
			log.error(
					"Practice operation title exist! practice_set_id=[{}], practice_id=[{}], title=[{}]",
					practiceSetId, practiceId, title);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 验证实操标题存在
	 *
	 * @param practiceSetId 实践设置id
	 * @param title         标题
	 * @param practicePath  实践路径
	 */
	public void validateOperationTitleExist(String practiceSetId, String title, String practicePath) {
		String practiceId = practiceOperationMapper.selectByTitle(practiceSetId, title);
		if (StringUtils.isNotBlank(practiceId)) {
			log.error(
					"Practice operation title exist! practice_set_id=[{}], practice_id=[{}], title=[{}]",
					practiceSetId, practiceId, title);
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, practicePath, "名称已存在" + title);
		}
	}

	/**
	 * 校验flag长度 5.3.6系统flag长度仅对动态flag长度做限制 随机与静态flag长度为1-255
	 *
	 * @param flags 标志
	 */
	public void validateFlagLength(String flags) {

		List<String> answers;
		if (StringUtils.isBlank(flags) || CollectionUtils
				.isEmpty(answers = JsonUtil.toJsonObjectArray(flags, String.class))) {
			log.error("flags is illegal , flags = [{}]", flags);
			throw new BusinessException(OjBusinessErrorCode.ANSWER_NOT_NULL);
		}

		for (String answer : answers) {
			if (answer.length() < CommonConstant.ONE
					|| answer.length() > CommonConstant.TWO_HUNDRED_AND_FIFTY_FIVE) {
				log.error("answer is illegal , answer = [{}]", answer);
				throw new BusinessException(OjBusinessErrorCode.ANSWER_TOO_LONG, CommonConstant.ONE,
						CommonConstant.TWO_HUNDRED_AND_FIFTY_FIVE);
			}
		}

	}

	/**
	 * 校验习题内容是否存在
	 *
	 * @param practiceSetId
	 * @param content       内容
	 */
	public void validateTheoryContentExist(String practiceSetId, String content) {
		String practiceId = practiceTheoryMapper.selectByContent("", content);
		if (StringUtils.isNotBlank(practiceId)) {
			log.error("Practice theory content exist! practice_set_id=[{}], practice_id=[{}]",
					practiceSetId, practiceId);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 校验实操习题修改title已存在问题
	 *
	 * @param operation
	 * @param newTitle  新标题
	 */
	public void validateOperationUpdateTitleExist(PracticeOperation operation, String newTitle) {
		String existTitleId = practiceOperationMapper.selectByTitle("",
				newTitle);
		if (StringUtils.isNotBlank(existTitleId)
				&& !operation.getResourceId().equals(existTitleId)) {
			log.error("name is exist!PracticeSetId=[{}] ", operation.getPracticeSetId());
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 修改时校验场景是否存在
	 *
	 * @param operation 实操
	 * @param topoData  拓扑数据
	 */
	public void validateOperationUpdateSceneExist(PracticeOperation operation,
												  JSONObject topoData) {

		// 场景数据查询
		List<OjLocalScene> adminScenes = ojLocalSceneMapper
				.selectByBusiness(operation.getResourceId(), false);
		List<OjLocalScene> webScenes = ojLocalSceneMapper
				.selectByBusiness(operation.getResourceId(), true);
		if (CollectionUtils.isEmpty(adminScenes) && CollectionUtils.isEmpty(webScenes)) {
			return;
		}

		// topo结构一致性校验
		if (!remoteTopoService.equalTopo(topoData, operation.getTopoId())) {
			log.error("scene is running!topoData=[{}],topoId=[{}] ", JsonUtil.toJsonString(topoData),operation.getTopoId());
			throw new BusinessException(OjBusinessErrorCode.SCENE_IS_RUNNING);
		}
	}

	/**
	 * 验证附件长度
	 *
	 * @param practiceName 实践名称
	 * @param dynamicFlag  动态标志
	 * @param attachments  附件
	 */
	public void validateAttachmentLength(String practiceName, Integer dynamicFlag,
			List<String> attachments) {
		if (dynamicFlag.intValue() != PracticeOperation.DynamicFlag.RANDOM.getCode()
				&& attachments.size() > CommonConstant.ONE) {
			log.error("practice attachment is too many!practiceName=[{}],attachments.size()=[{}] ", practiceName, attachments.size());
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_ATTACHMENT_IS_TOO_MANY,
					practiceName);
		}
	}

	/**
	 * 验证附件长度
	 *
	 * @param practiceName 实践名称
	 * @param dynamicFlag  动态标志
	 * @param attachments  附件
	 * @param practicePath 实践路径
	 */
	public void validateAttachmentLength(String practiceName, Integer dynamicFlag,
										 List<String> attachments, String practicePath) {
		if (dynamicFlag.intValue() != PracticeOperation.DynamicFlag.RANDOM.getCode()
				&& attachments.size() > CommonConstant.ONE) {
			log.error("practice attachment is too many!practiceName=[{}],attachments.size()=[{}] ", practiceName,  attachments.size());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, practicePath, "不支持多个附件");
		}
	}

	/**
	 * 验证理论内容存在排除本身
	 *
	 * @param practiceSetId  实践设置id
	 * @param content        内容
	 * @param practiceTheory 实践理论
	 */
	public void validateTheoryContentExistExcludeItself(String practiceSetId, String content,
			PracticeTheory practiceTheory) {
		String practiceId = practiceTheoryMapper.selectByContent("", content);
		if (StringUtils.isNotBlank(practiceId)
				&& !practiceId.equals(practiceTheory.getResourceId())) {
			log.error("Practice theory content exist! practice_set_id=[{}], practice_id=[{}]",
					practiceSetId, practiceId);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 验证填补空白关键词
	 *
	 * @param theoryType 理论类型
	 * @param keyword    关键词
	 */
	public void validateFillBlankKeyword(Integer theoryType, String keyword) {
		if (theoryType == PracticeTheory.TheoryType.SHORT_ANSWER.getCode().intValue()) {
			JSONArray objects = JSON.parseArray(keyword);
			if (objects!=null) {
				for (Object obj : objects) {
					if (StringUtils.isBlank(obj.toString())) {
						log.error("keyword empty error!theoryType=[{}],keyword.size()=[{}] ",theoryType,keyword);
						throw new BusinessException(OjBusinessErrorCode.KEYWORD_EMPTY_ERROR);
					}
				}
			}
		}
	}

	/**
	 * 校验习题集是否有权限访问，无权限抛出名称重复异常
	 *
	 * @param practiceSet
	 * @param userId      用户id
	 */
	public void validatePracticeSetPermission(PracticeSet practiceSet, String userId) {
		if (practiceSet.getCreateUserId().equals(userId)) {
			return;
		}

		if (practiceSet.getOperationAuthType()
				.equals(AuthTable.AuthType.ALL_SHARE_MODE.getCode())) {
			return;
		}

		List<String> authIds = JsonUtil.toJsonObjectArray(practiceSet.getOperationAuthIds(),
				String.class);
		if (!authIds.contains(userId)) {
			log.error("practice set is exist!userId=[{}]",userId);
			throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_IS_EXIST);
		}
	}


	/**
	 * 更新隔离模式时校验场景是否存在
	 *
	 * @param operation 实操
	 */
	public void validateOperationUpdateShareModeSceneExist(PracticeOperation operation) {

		// 场景数据查询
		List<OjLocalScene> adminScenes = ojLocalSceneMapper
				.selectByBusiness(operation.getResourceId(), false);
		List<OjLocalScene> webScenes = ojLocalSceneMapper
				.selectByBusiness(operation.getResourceId(), true);
		if (CollectionUtils.isEmpty(adminScenes) && CollectionUtils.isEmpty(webScenes)) {
			return;
		} else {
			log.error("scene is running!adminScenes=[{}]，webScenes=[{}]",adminScenes,webScenes);
			throw new BusinessException(OjBusinessErrorCode.SCENE_IS_RUNNING);
		}
	}

	/**
	 * 校验是否为内置资源
	 *
	 * @param practiceSet 实践设置
	 */
	public void validateBuiltin(PracticeSet practiceSet) {
		if (practiceSet.isBuiltin()) {
			log.error("unable to export builtin resource!practiceSet=[{}]",JsonUtil.toJsonString(practiceSet));
			throw new BusinessException(OjBusinessErrorCode.UNABLE_TO_EXPORT_BUILTIN_RESOURCE, practiceSet.getName());
		}
		if (practiceSet.getPracticeType().equals(PracticeSet.PracticeType.THEORY.getCode())) {
			String practiceTheoryName = practiceTheoryMapper.searchBuiltinBySetIds(Arrays.asList(practiceSet.getResourceId()));
			if (practiceTheoryName != null) {
				log.error("practiceTheoryName is not null!practiceTheoryName=[{}]",practiceTheoryName);
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_CONTAIN_BUILTIN_PRACTICE, practiceSet.getName(), practiceTheoryName);
			}
		}else if (practiceSet.getPracticeType().equals(PracticeSet.PracticeType.OPERATION.getCode())) {
			String practiceOperationName = practiceOperationMapper.searchBuiltinBySetIds(Arrays.asList(practiceSet.getResourceId()));
			if (practiceOperationName != null) {
				throw new BusinessException(OjBusinessErrorCode.PRACTICE_SET_CONTAIN_BUILTIN_PRACTICE, practiceSet.getName(), practiceOperationName);
			}
		}
	}
}
