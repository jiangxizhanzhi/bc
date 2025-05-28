/**
 * 这段代码定义了一个名为 `SpaceAptThreatDetectRule` 的实体类，用于表示空间APT（高级持续性威胁）检测规则。以下是对代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类位于 `com.cyberpeace.space.dao.entity` 包中。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动生成属性的getter和setter方法。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，将该类映射到数据库中的 `space_apt_threat_detect_rule` 表。 
 * 2. **继承关系**： 
 * - 该类继承自 `SpaceBaseTable`，表明它可能包含一些通用的字段或行为。 
 * 3. **字段说明**： 
 * - `type`: 表示检测类型，使用枚举值（如IP、DNS、URL等）。 
 * - `tool`: 应用工具名称。 
 * - `aptCode`: APT代号。 
 * - `content`: 内容详情。 
 * - `description`: 内容简介。 
 * 4. **方法说明**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`: 将当前实体对象转换为 `SpaceAptThreatDetectRuleBo` 对象，并通过 `BeanUtils.copy` 方法完成属性复制。 
 * 5. **依赖与注解**： 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个字段的用途。 
 * - 使用了 `BeanUtils` 工具类来进行对象之间的属性复制。 
 * 总体来说，这个类是一个数据传输对象（DTO），主要用于存储和操作APT威胁检测相关的规则信息，并提供了从实体到业务对象的转换方法。 
**/
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAptThreatDetectRuleBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Space APT 威胁检测规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_apt_threat_detect_rule")
public class SpaceAptThreatDetectRule extends SpaceBaseTable {

	/**
	 * 类型
	 * 1:IP,2:DNS,3:URL,4:邮箱账号,5:文件HASH,6:证书HASH
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	@ApiModelProperty(value = "应用工具")
	private String tool;

	@ApiModelProperty(value = "APT代号")
	private String aptCode;

	@ApiModelProperty(value = "内容详情")
	private String content;

	@ApiModelProperty(value = "内容简介")
	private String description;

	public SpaceAptThreatDetectRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceAptThreatDetectRuleBo bo = BeanUtils.copy(this, SpaceAptThreatDetectRuleBo.class);
		return bo;
	}
}
