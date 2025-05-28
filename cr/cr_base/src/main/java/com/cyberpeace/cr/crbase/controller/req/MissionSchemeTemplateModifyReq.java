/**
 * 这段代码定义了一个名为 `MissionSchemeTemplateModifyReq` 的 Java 类，用于表示任务方案模板修改请求体。以下是对代码的简要分析： 
 * 1. **类继承与实现**： 
 * - 该类继承自 `MissionSchemeTemplateReq`，并且实现了 `Serializable` 接口，表明它可以被序列化。 
 * 2. **注解与字段**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 定义了一个 `resourceId` 字段，并通过 Swagger 的 `@ApiModelProperty` 注解标记为必填字段。 
 * 3. **方法**： 
 * - 重写了 `validate()` 方法，用于验证请求体中的数据。 
 * - 验证标题和描述的长度。 
 * - 遍历 `combatConfig` 列表中的每个 `MissionSchemeStage` 对象并调用其 `validate()` 方法。 
 * - 检查阶段名称是否重复。 
 * 4. **依赖**： 
 * - 使用了 `com.cyberpeace.cr.crbase` 包下的多个类和常量。 
 * - 使用了 Guava 的 `Lists.newArrayList()` 方法来创建列表。 
 * 5. **设计意图**： 
 * - 该类的主要目的是作为任务方案模板修改请求的数据传输对象（DTO），并确保传入的数据符合业务规则。 
 * 总体而言，这是一个典型的 DTO 类，用于封装请求参数并进行必要的校验。 
**/
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
 * 任务方案模板修改请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionSchemeTemplateModifyReq extends MissionSchemeTemplateReq implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "必须字段，其他字段不更新则非必要", required = true)
	private String resourceId;

	@Override
	public void validate() {
		// 校验名称和描述长度
		if (this.getTitle() != null) {
			CrCommonValidator.validateNameLen(this.getTitle(),
					CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		}
		if (this.getDescription() != null) {
			CrCommonValidator.validateDescLen(this.getDescription(),
					CrBusinessErrorCode.ILLEGAL_DESC_LEN);
		}
		if (this.getCombatConfig() != null) {
			List<String> stageNames = Lists.newArrayList();
			for (MissionSchemeStage schemeStage : this.getCombatConfig()) {
				schemeStage.validate();
				stageNames.add(schemeStage.getStage());
			}
			// 校验阶段重名
			CrCommonValidator.checkStringListDuplicate(stageNames,
					CrBusinessErrorCode.DUPLICATED_STAGE_NAME);
		}
	}

}
