package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务修改请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `MissionModifyReq` 的 Java 类，用于表示任务修改请求的参数。以下是代码的主要分析： 
 * ### 功能概述： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化。 
 * - 它包含多个字段，每个字段都有对应的注解，用于描述其用途和是否必填。 
 * ### 主要字段及注解： 
 * 1. **title**：任务标题，使用 `@ApiModelProperty` 注解标记为必填。 
 * 2. **resourceId**：任务ID，同样标记为必填。 
 * 3. **score**：任务得分，标记为必填，并且类型为整数。 
 * 4. **skillPoints**：技能点，标记为字符串形式，但内部逻辑将其解析为整数列表。 
 * 5. **description**：任务描述，标记为必填。 
 * ### 方法： 
 * - **validate()**：这是一个验证方法，用于检查各个字段的有效性： 
 * - 检查 `title` 的长度是否符合要求。 
 * - 将 `skillPoints` 字符串解析为整数列表，并逐一验证每个技能点是否在允许范围内。 
 * - 验证 `score` 是否超过最大值。 
 * ### 总结： 
 * 这个类主要用于接收任务修改请求的数据，并提供了基本的输入验证机制。通过使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了属性访问器的生成，同时利用 Swagger 的 `@ApiModelProperty` 提供了清晰的文档说明。此外，通过调用 `CrCommonValidator` 类的方法来确保数据的合法性。 
**/
@Getter
@Setter
public class MissionModifyReq implements Serializable {

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "标题", required = true)
	private String title;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "分值", required = true)
	private Integer score;

	/**
	 * 技能点
	 */
	@ApiModelProperty(value = "技能点", required = true)
	private String skillPoints;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = true)
	private String description;


	public void validate() {
		// 验证名称长度
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.INVALID_MISSION_NAME);

		// validate skill points
		List<Integer> points = JSON.parseArray(skillPoints, Integer.class);
		for (Integer skillPoint : points) {
			CrCommonValidator.validateRange(skillPoint, CrCommonConstants.SKILL_POINT_MIN_VALUE,
					CrCommonConstants.SKILL_POINT_MAX_VALUE,
					CrBusinessErrorCode.INVALID_SKILL_POINT_VALUE);
		}
		// validate score
		CrCommonValidator.validateScore(score, CrCommonConstants.SCORE_MAX_VALUE,
				CrBusinessErrorCode.SCORE_OUT_OF_RANGE);
	}

}
