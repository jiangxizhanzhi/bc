package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceWarningDictionaryBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 空间警告词典
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceWarningDictionary` 的 Java 类，用于表示“空间警告词典”实体。以下是对其的主要分析： 
 * 1. **类的功能**： 
 * - 这个类继承自 `SpaceBaseTable`，并且通过 `@TableName` 注解指定其对应的数据库表名为 `space_warning_dictionary`。 
 * - 它包含了一些与空间警告相关的字段，如 `toolType`（工具类型）、`toolLevel`（工具告警等级）、`level`（告警等级）、`description`（等级描述）等。 
 * 2. **注解的作用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - Swagger 的 `@ApiModelProperty` 注解为每个字段提供了元数据信息，便于生成 API 文档。 
 * 3. **方法功能**： 
 * - 提供了一个 `entity2Bo` 方法，该方法接收一个 `Map<String, IdAndName>` 类型的参数 `relatedMap`，并使用 `BeanUtils.copy` 将当前对象转换为 `SpaceWarningDictionaryBo` 对象返回。 
 * 4. **依赖关系**： 
 * - 依赖了多个外部类或接口，包括 `SpaceBaseTable`、`IdAndName`、`SpaceWarningDictionaryBo`、`BeanUtils` 等。 
 * - 还引用了 MyBatis-Plus 的 `@TableName` 注解以及 Swagger 的 `@ApiModelProperty` 注解。 
 * 总结来说，这个类主要是用来封装空间警告词典的相关数据，并且提供了一种方式将实体对象转换成业务对象（BO），以便于业务逻辑处理。 
**/
@Getter
@Setter
@TableName("space_warning_dictionary")
public class SpaceWarningDictionary extends SpaceBaseTable {

	@ApiModelProperty(value = "工具类型")
	private String toolType;

	@ApiModelProperty(value = "工具告警等级")
	private String toolLevel;

	@ApiModelProperty(value = "告警等级,低1,中2,高3")
	private Integer level;

	@ApiModelProperty(value = "等级描述")
	private String description;

	public SpaceWarningDictionaryBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceWarningDictionaryBo bo = BeanUtils.copy(this, SpaceWarningDictionaryBo.class);
		return bo;
	}
}

