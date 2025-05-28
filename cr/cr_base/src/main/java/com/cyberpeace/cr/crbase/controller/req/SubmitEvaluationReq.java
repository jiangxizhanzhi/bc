/**
 * 这段代码定义了一个名为 `SubmitEvaluationReq` 的 Java 类，用于封装提交评估请求所需的信息。以下是对其功能和结构的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于处理与提交评估相关的请求数据。 
 * - 它包含了多个字段（如 `roomId`, `submitId`, `evaluateStatus` 等），这些字段通过 Lombok 注解自动生成了 getter 和 setter 方法。 
 * 2. **字段说明**： 
 * - `roomId`: 表示房间ID。 
 * - `submitId`: 表示任务提交ID。 
 * - `evaluateStatus`: 表示评估结果的状态。 
 * - `reason`: 表示预设的原因。 
 * - `detail`: 表示裁决详情。 
 * - `score`: 表示分数改判值。 
 * 3. **方法说明**： 
 * - `setScore(String score)` 方法用于设置分数，并进行了严格的校验： 
 * - 如果传入的分数为空字符串，则将其设置为默认值 `0`。 
 * - 如果分数不是有效的非负整数，则记录错误日志并抛出异常。 
 * 4. **依赖库**： 
 * - 使用了 Lombok 库来简化代码，避免手动编写 getter 和 setter 方法。 
 * - 使用了 Apache Commons Lang 的 `StringUtils` 工具类来判断字符串是否为空或空白。 
 * - 使用了自定义的 `BusinessException` 异常类来处理业务逻辑中的错误情况。 
 * 5. **注解说明**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `@Slf4j`：提供日志记录功能。 
 * - `@ApiModelProperty`：用于生成 API 文档，描述每个字段的含义。 
 * 6. **异常处理**： 
 * - 在 `setScore` 方法中，如果输入的分数不符合要求，会抛出自定义的 `BusinessException` 异常，并使用 `CrBusinessErrorCode.INVALID_SCORE` 来标识无效分数的情况。 
 * 总体来说，这个类设计合理，通过 Lombok 和注解减少了冗余代码，同时在关键位置加入了必要的校验和异常处理机制，确保了数据的安全性和一致性。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提交评估请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@Slf4j
public class SubmitEvaluationReq {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	/**
	 * 提交id
	 */
	@ApiModelProperty(value = "任务提交id")
	private String submitId;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估结果")
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	@ApiModelProperty(value = "预设原因")
	private Integer reason;

	/**
	 * 详情
	 */
	@ApiModelProperty(value = "裁决详情")
	private String detail;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "分数改判")
	private Integer score;

	public void setScore(String score) {
		// 处理打分为空字符串的情况
		if (StringUtils.isBlank(score)) {
			this.score = 0;
			return;
		}
		// 校验打分为非零整数
		try {
			this.score = new Integer(score);
			if (this.score < 0) {
				log.error("score = [{}]", score);
				throw new RuntimeException();
			}
		} catch (Exception e) {
			log.error("Invalid score, supposed to be non-negative integer! score = [{}]", score);
			throw new BusinessException(CrBusinessErrorCode.INVALID_SCORE);
		}
	}
}
