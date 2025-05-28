/**
 * 这段代码定义了一个名为 `SpaceFingerPrint` 的实体类，用于表示隔间的指纹信息。以下是代码的主要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceFingerPrint` 
 * - 包名：`com.cyberpeace.space.dao.entity` 
 * - 继承自 `SpaceBaseTable`，表明它是一个基于某种基础表的实体类。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了属性的 getter 和 setter 方法的生成。 
 * 2. **注解说明**： 
 * - `@TableName("space_finger_print")`：指定该实体类对应的数据库表名为 `space_finger_print`。 
 * - `@ApiModelProperty`：用于生成 API 文档时描述字段的含义，如 `type` 和 `content`。 
 * 3. **字段说明**： 
 * - `type`：表示隔间的类型，使用 `Integer` 类型。 
 * - `content`：表示指纹列表，使用 `String` 类型存储。 
 * 4. **方法说明**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：将当前实体对象转换为 `SpaceFingerPrintBo` 对象。通过 `BeanUtils.copy` 方法实现对象的拷贝。 
 * 5. **依赖类**： 
 * - `SpaceBaseTable`：基础表类。 
 * - `IdAndName` 和 `SpaceFingerPrintBo`：模型类，分别表示 ID 和名称的组合以及指纹业务对象。 
 * - `BeanUtils`：工具类，用于对象之间的属性拷贝。 
 * 总结：这是一个典型的 MyBatis-Plus 实体类，用于数据库操作，并且通过工具类实现了对象的转换功能。 
**/
package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceFingerPrintBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 隔间指纹
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_finger_print")
public class SpaceFingerPrint extends SpaceBaseTable {

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "指纹列表")
	private String content;

	public SpaceFingerPrintBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceFingerPrintBo bo = BeanUtils.copy(this, SpaceFingerPrintBo.class);
		return bo;
	}
}
