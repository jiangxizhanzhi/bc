package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceThreatImageRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间威胁图像规则
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceThreatImageRule` 的实体类，用于表示空间威胁图像规则。以下是代码的简要分析： 
 * 1. **类与注解**： 
 * - 类 `SpaceThreatImageRule` 继承自 `SpaceBaseTable`，并使用了 `@TableName` 注解指定数据库表名为 `space_threat_image_rule`。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成属性的 getter 和 setter 方法。 
 * 2. **属性**： 
 * - `type`：威胁类型。 
 * - `level`：威胁等级。 
 * - `assetType`：受害资产类型。 
 * - `information`：威胁情报。 
 * - `result`：结果。 
 * - `tag`：标签。 
 * 3. **方法**： 
 * - `entity2Bo`：将当前实体对象转换为 `SpaceThreatImageRuleBo` 对象，并使用 `BeanUtils.copy` 方法进行属性复制。 
 * 4. **依赖**： 
 * - 使用了 `com.baomidou.mybatisplus.annotation.TableName` 进行数据库表映射。 
 * - 使用了 `com.cyberpeace.common.util.BeanUtils` 进行对象属性复制。 
 * - 引用了 `com.cyberpeace.space.model.IdAndName` 和 `com.cyberpeace.space.model.SpaceBaseTable` 模型类。 
 * - 使用了 `io.swagger.annotations.ApiModelProperty` 注解来描述 API 参数。 
 * 总结：该类是一个数据传输对象（DTO），用于处理空间威胁图像相关的业务逻辑，支持从实体到业务对象的转换。 
**/
@Getter
@Setter
@TableName("space_threat_image_rule")
public class SpaceThreatImageRule extends SpaceBaseTable {

	@ApiModelProperty(value = "威胁类型")
	private Integer type;

	@ApiModelProperty(value = "威胁等级")
	private Integer level;

	@ApiModelProperty(value = "受害资产类型")
	private Integer assetType;

	@ApiModelProperty(value = "威胁情报")
	private String information;

	@ApiModelProperty(value = "结果")
	private Integer result;

	@ApiModelProperty(value = "标签")
	private String tag;


	public SpaceThreatImageRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceThreatImageRuleBo bo = BeanUtils.copy(this, SpaceThreatImageRuleBo.class);
		return bo;
	}
}

