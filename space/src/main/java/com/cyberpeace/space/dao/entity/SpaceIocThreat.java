/**
 * 这段代码定义了一个名为 `SpaceIocThreat` 的实体类，用于表示空间 IOC（Indicator of Compromise）威胁信息。以下是代码的主要分析： 
 * 1. **类定义**： 
 * - 类 `SpaceIocThreat` 继承自 `SpaceBaseTable`，并使用了 MyBatis-Plus 的 `@TableName` 注解，指定其对应的数据库表名为 `space_ioc_threat`。 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 2. **字段说明**： 
 * - `type`：表示类型的整数字段，通过 Swagger 注解 `@ApiModelProperty` 描述为“类型”。 
 * - `ioc`：表示 IOC 列表的字符串字段，描述为“IOC列表”。 
 * 3. **方法**： 
 * - `entity2Bo`：将当前实体对象转换为 `SpaceIocThreatBo` 对象的方法。使用了工具类 `BeanUtils.copy` 来实现对象之间的属性复制。 
 * 4. **依赖与上下文**： 
 * - 该类依赖于多个外部类和接口，包括 `IdAndName`、`SpaceBaseTable`、`SpaceIocThreatBo` 和 `BeanUtils` 等。 
 * - 这些依赖表明该类是业务逻辑的一部分，主要用于数据的持久化和转换。 
 * 总结：`SpaceIocThreat` 是一个数据模型类，用于存储和操作 IOC 威胁数据，并提供了一种将实体对象转换为业务对象的方法。 
**/
package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceIocThreatBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space IoC 威胁
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_ioc_threat")
public class SpaceIocThreat extends SpaceBaseTable {
	@ApiModelProperty(value = "类型")
	private Integer type;
	@ApiModelProperty(value = "IOC列表")
	private String ioc;


	public SpaceIocThreatBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceIocThreatBo bo = BeanUtils.copy(this, SpaceIocThreatBo.class);
		return bo;
	}
}
