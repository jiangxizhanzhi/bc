package com.cyberpeace.cr.crbase.controller.req;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.MissionBase;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 任务模板请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 1. **功能概述** 
 * 该代码定义了一个名为 `MissionTemplateReq` 的类，用于表示任务模板的请求体。它包含任务的基本信息（如标题、描述、得分等），并提供了数据验证方法 `validate()`，确保请求参数符合业务规则。 
 * --- 
 * #### 2. **主要字段** 
 * - **必填字段**： 
 * - `title`: 任务标题。 
 * - `description`: 任务描述。 
 * - `score`: 任务得分。 
 * - `skillPoints`: 技能点列表。 
 * - `answer`: 标准答案。 
 * - `option`: 选择题选项（或 `null` 表示非选择题）。 
 * - `type`: 任务类型（如单选、填空等）。 
 * - `category`: 任务类别。 
 * - `writeUp`: 题解说明。 
 * - **注解**： 
 * 使用了 `@ApiModelProperty` 注解来描述字段的用途和是否必填，同时使用 `@Getter`, `@Setter` 和 `@Slf4j` 注解简化了字段的访问和日志记录。 
 * --- 
 * #### 3. **验证逻辑** 
 * `validate()` 方法对所有字段进行了一系列严格的校验，主要包括以下内容： 
 * 1. **名称和描述长度验证**： 
 * - 调用 `CrCommonValidator.validateNameLen()` 和 `validateDescLen()` 检查标题和描述的长度是否合规。 
 * 2. **技能点验证**： 
 * - 检查技能点列表的大小是否符合要求（通过 `CrCommonConstants.SKILL_POINTS_SIZE`）。 
 * - 遍历技能点列表，验证每个技能点是否在允许的范围（`CrCommonConstants.SKILL_POINT_MIN_VALUE` 到 `CrCommonConstants.SKILL_POINT_MAX_VALUE`）内。 
 * 3. **得分验证**： 
 * - 检查得分是否超出最大限制（`CrCommonConstants.SCORE_MAX_VALUE`）。 
 * 4. **答案验证**： 
 * - 如果任务类型需要答案，则检查答案是否为空。 
 * - 如果任务是选择题，还需验证选项是否为空，并解析选项列表，逐个校验选项内容的合法性。 
 * 5. **重复性验证**： 
 * - 对选择题的选项列表进行去重检查，确保没有重复项。 
 * 6. **日志与异常处理**： 
 * - 在发现不符合规则的数据时，记录错误日志并抛出 `BusinessException` 异常，附带具体的错误码。 
 * --- 
 * #### 4. **依赖组件** 
 * - **工具类**： 
 * - `CrCommonValidator`: 提供通用的验证方法。 
 * - `JsonUtil`: 将字符串转换为 JSON 对象数组。 
 * - `Lists`: Apache Commons Lang 提供的工具类，用于操作集合。 
 * - **常量类**： 
 * - `CrBusinessErrorCode`: 定义了各种业务错误码。 
 * - `CrCommonConstants`: 包含一些常量值，例如技能点大小、得分上限等。 
 * --- 
 * #### 5. **优点** 
 * - **模块化设计**：将验证逻辑封装到 `validate()` 方法中，便于复用和维护。 
 * - **灵活性强**：通过 `MissionBase.MissionType` 枚举判断任务类型，动态调整验证逻辑。 
 * - **清晰的日志记录**：在验证失败时记录详细的错误信息，便于排查问题。 
 * --- 
 * #### 6. **潜在改进点** 
 * - **国际化支持**：目前的错误提示直接硬编码在代码中，可以考虑引入资源文件以支持多语言。 
 * - **性能优化**：对于大量数据的验证（如技能点列表），可以进一步优化遍历逻辑。 
 * - **扩展性**：如果未来新增任务类型或字段，需要同步更新验证逻辑。 
 * --- 
 * #### 总结 
 * 该代码是一个典型的业务请求体类，通过严谨的验证机制确保输入数据的合法性，同时保持良好的可读性和扩展性。 
**/
@Getter
@Setter
@Slf4j
public class MissionTemplateReq implements Serializable {

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题", required = true)
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = true)
	private String description;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "分值", required = true)
	private Integer score;

	/**
	 * 技能点
	 */
	@ApiModelProperty(value = "技能点", required = true)
	private List<Integer> skillPoints;

	/**
	 * 答案
	 */
	@ApiModelProperty(value = "标准答案", required = true)
	private String answer;

	/**
	 * 选择
	 */
	@ApiModelProperty(value = "选择题选项，非选择题为\"null\"", required = true)
	private String option;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "任务类型（单选、填空等）", required = true)
	private Integer type;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "任务类别（密码破解、网络侦查等）", required = true)
	private String category;

	/**
	 * 写入向上
	 */
	@ApiModelProperty(value = "题解", required = true)
	private String writeUp;

	public void validate() {
		// 验证名称长度
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.INVALID_MISSION_NAME);
		// validate desc
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		//校验题解长度
		CrCommonValidator.validateTextLen(writeUp, CrBusinessErrorCode.ILLEGAL_WRITEUP_LEN);

		// validate skill points
		CrCommonValidator.validateSize(skillPoints, CrCommonConstants.SKILL_POINTS_SIZE,
				CrBusinessErrorCode.INVALID_SKILL_POINTS_SIZE);
		for (Integer skillPoint : skillPoints) {
			CrCommonValidator.validateRange(skillPoint, CrCommonConstants.SKILL_POINT_MIN_VALUE,
					CrCommonConstants.SKILL_POINT_MAX_VALUE,
					CrBusinessErrorCode.INVALID_SKILL_POINT_VALUE);
		}

		// validate score
		CrCommonValidator.validateScore(score, CrCommonConstants.SCORE_MAX_VALUE,
				CrBusinessErrorCode.SCORE_OUT_OF_RANGE);

		// validate answer
		if (MissionBase.MissionType.ANSWER_NULLABLE_TYPE_LIST.contains(type) && answer == null) {
			log.error("Answer cannot be empty!");
			throw new BusinessException(CrBusinessErrorCode.ANSWER_NOT_NULLABLE);
		}
		// validate choice
		if (MissionBase.MissionType.HAS_OPTION_TYPE_LIST.contains(type)) {
			if (answer.equals(CommonConstant.EMPTY_LIST_JSON) || answer.isEmpty()) {
				log.error("Answer cannot be empty!");
				throw new BusinessException(CrBusinessErrorCode.ANSWER_NOT_NULLABLE);
			}
			if (option.equals(CommonConstant.EMPTY_LIST_JSON) || option.isEmpty()) {
				log.error("Options cannot be empty!");
				throw new BusinessException(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE);
			}
			// validate option
			List<JSONObject> options = JsonUtil.toJsonObjectArray(option, JSONObject.class);
			List<String> realOptions = Lists.newArrayList();
			for (JSONObject option : options) {
				String optionValue = option.getString("optionValue");
				if (optionValue == null) {
					log.error("Options cannot be empty!");
					throw new BusinessException(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE);
				}
				String realOption = StringUtils.strip(optionValue);
				CrCommonValidator.validateDescLen(realOption, CrBusinessErrorCode.ILLEGAL_OPTION_LEN);
				if (realOption.isEmpty()) {
					throw new BusinessException(CrBusinessErrorCode.OPTIONS_NOT_NULLABLE);
				}
				realOptions.add(realOption);
			}
			CrCommonValidator.checkStringListDuplicate(realOptions,
					CrBusinessErrorCode.OPTIONS_DUPLICATE);
		}

	}
}

