/**
 * 这段代码定义了一个名为 `SpaceBaseAlgorithm` 的 Java 类，它是 `SpaceBaseTable` 的子类。这个类的主要功能是表示一个空间基础算法的相关信息，并提供将实体对象转换为业务对象的方法。 
 * ### 主要特点： 
 * 1. **继承与注解**： 
 * - `SpaceBaseAlgorithm` 继承自 `SpaceBaseTable`。 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来简化属性的getter和setter方法的生成。 
 * - 使用了 MyBatis-Plus 的 `@TableName` 注解，表明该类对应的数据库表名为 `space_base_algorithm`。 
 * 2. **字段说明**： 
 * - `description`: 简述算法的描述信息。 
 * - `type`: 表示算法的类型。 
 * - `codeType`: 表示代码的语言类型。 
 * - `code`: 存储具体的算法代码。 
 * - `parameter`: 存储算法的参数，以字符串形式存储。 
 * 3. **方法说明**： 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`：这是一个重要的方法，用于将 `SpaceBaseAlgorithm` 实体对象转换为 `SpaceBaseAlgorithmBo` 业务对象。在转换过程中： 
 * - 使用 `BeanUtils.copy` 方法复制基本属性。 
 * - 使用 `JsonUtil.toJsonObjectArray` 方法将 `parameter` 字段（JSON 格式的字符串）解析为 `SpaceAlgorithmParameter` 对象数组。 
 * 4. **依赖工具类**： 
 * - `BeanUtils.copy`: 用于复制对象属性。 
 * - `JsonUtil.toJsonObjectArray`: 用于将 JSON 字符串解析为指定类型的对象数组。 
 * ### 总结： 
 * 这个类的设计目的是为了封装空间基础算法的相关信息，并提供一种方式将数据库中的实体对象转换为适合业务逻辑处理的业务对象。通过使用 Lombok 和 MyBatis-Plus 等工具，代码简洁且易于维护。 
**/
package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAlgorithmParameter;
import com.cyberpeace.space.service.context.rule.SpaceBaseAlgorithmBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space Base 算法
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("space_base_algorithm")
public class SpaceBaseAlgorithm extends SpaceBaseTable {

	@ApiModelProperty(value = "简述")
	private String description;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "代码语言类型")
	private Integer codeType;

	@ApiModelProperty(value = "代码")
	private String code;

	@ApiModelProperty(value = "参数")
	private String parameter;


	public SpaceBaseAlgorithmBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceBaseAlgorithmBo bo = BeanUtils.copy(this, SpaceBaseAlgorithmBo.class);
		bo.setParameter(JsonUtil.toJsonObjectArray(this.parameter, SpaceAlgorithmParameter.class));
		return bo;
	}
}
