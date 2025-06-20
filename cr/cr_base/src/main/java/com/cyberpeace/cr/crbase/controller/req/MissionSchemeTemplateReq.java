package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务方案模板请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MissionSchemeTemplateReq` 的 Java 类，用于表示任务方案模板的请求体。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化。 
 * - 它包含了任务方案模板的基本信息，如标题、描述、模板类型、颜色以及战斗配置。 
 * 2. **字段说明**： 
 * - `title`: 任务标题，必须提供。 
 * - `description`: 任务描述，必须提供。 
 * - `template`: 模板类型，必须提供。 
 * - `color`: 模板颜色，必须提供。 
 * - `combatConfig`: 任务编排列表，包含多个 `MissionSchemeStage` 对象。 
 * 3. **方法说明**： 
 * - `validate()`: 验证方法，确保输入数据符合要求。它调用了 `CrCommonValidator` 来验证标题和描述的长度，并检查战斗配置中的阶段名称是否重复。 
 * 4. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了元数据，便于生成 API 文档。 
 * 5. **依赖与工具**： 
 * - 依赖于 `com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode` 和 `com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage`。 
 * - 使用了 Guava 的 `Lists.newArrayList()` 方法来创建字符串列表。 
 * 6. **设计意图**： 
 * - 该类的设计目的是作为一个请求对象，用于接收前端传来的任务方案模板数据，并在后端进行必要的校验。 
 * 总结来说，这个类是一个典型的请求对象类，主要用于封装任务方案模板的数据，并提供数据验证功能。 
**/
@Getter
@Setter
public class MissionSchemeTemplateReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
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
	 * 模板
	 */
	@ApiModelProperty(value = "模板类型", required = true)
	private Integer template;

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "模板颜色", required = true)
	private Integer color;

	/**
	 * 战斗配置
	 */
	@ApiModelProperty(value = "任务编排", required = true)
	private List<MissionSchemeStage> combatConfig;

	public void validate() {
		// 校验名称和描述长度
		CrCommonValidator.validateNameLen(title, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		List<String> stageNames = Lists.newArrayList();
		for (MissionSchemeStage schemeStage : combatConfig) {
			schemeStage.validate();
			stageNames.add(schemeStage.getStage());
		}
		// 校验阶段重名
		CrCommonValidator.checkStringListDuplicate(stageNames,
				CrBusinessErrorCode.DUPLICATED_STAGE_NAME);
	}
}

