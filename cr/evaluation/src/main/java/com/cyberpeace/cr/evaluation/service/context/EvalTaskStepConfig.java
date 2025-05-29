package com.cyberpeace.cr.evaluation.service.context;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * eval 任务步骤配置
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskStepConfig` 的 Java 类，该类用于表示评估任务中的步骤配置。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.service.context` 
 * - **导入的包**： 
 * - `com.cyberpeace.common.exception.BusinessException`：用于处理业务异常。 
 * - `com.cyberpeace.common.util.JsonUtil`：用于 JSON 字符串转换。 
 * - `com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode`：包含业务错误码。 
 * - `com.cyberpeace.cr.crbase.utils.CrCommonValidator`：提供通用验证方法。 
 * - `io.swagger.annotations.ApiModelProperty`：用于 Swagger 注解，生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `lombok.extern.slf4j.Slf4j`：Lombok 注解，自动生成日志记录器。 
 * ### 类属性 
 * - `stage`：步骤阶段，类型为 `Integer`。 
 * - `title`：步骤标题，类型为 `String`。 
 * - `process`：步骤过程，类型为 `String`。 
 * ### 方法 
 * - `validate()`：用于验证步骤配置的有效性。 
 * - 如果 `stage`、`title` 或 `process` 为空，则抛出 `BusinessException` 异常，并记录错误日志。 
 * - 使用 `CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.ILLEGAL_TITLE_LEN)` 验证 `title` 的长度是否合法。 
 * ### 注释 
 * - 类和属性都有详细的 Javadoc 注释，说明了每个字段的作用。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码。 
 * ### 总结 
 * - `EvalTaskStepConfig` 类主要用于表示评估任务中的步骤配置。 
 * - 包含验证方法确保配置数据的有效性。 
 * - 使用 Lombok 注解减少样板代码，提高可读性和维护性。 
**/
@Getter
@Setter
@Slf4j
public class EvalTaskStepConfig {

	/**
	 * 阶段
	 */
	@ApiModelProperty("步骤阶段")
	private Integer stage;

	/**
	 * 标题
	 */
	@ApiModelProperty("步骤标题")
	private String title;

	/**
	 * 过程
	 */
	@ApiModelProperty("步骤过程")
	private String process;

	public void validate() {
		if (stage == null || title == null || process == null) {
			log.error("The configuration of the evaluation step is incorrect! EvalTaskStepConfig = [{}]", JsonUtil.toJsonString(this));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_ILLEGAL);
		}
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.ILLEGAL_TITLE_LEN);
	}

}

