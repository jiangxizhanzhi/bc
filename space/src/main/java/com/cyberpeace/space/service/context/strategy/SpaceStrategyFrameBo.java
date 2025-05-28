/**
 * 这段代码定义了一个名为 `SpaceStrategyFrameBo` 的 Java 类，用于表示空间策略框架的业务对象（BO）。以下是代码的简要分析： 
 * 1. **类的用途**： 
 * - 该类是空间策略框架中的一个核心数据传输对象（DTO/BO），用于存储与空间策略相关的数据结构。 
 * 2. **注解和特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为某些字段提供了额外的元信息，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `resourceId`：资源 ID，唯一标识某个资源。 
 * - `name`：资源名称。 
 * - `parent`：父级资源的 ID，表示层级关系。 
 * - `builtin`：布尔值，标记资源是否为内置资源。 
 * - `removable`：布尔值，标记资源是否可以被删除。 
 * - `children`：子级资源列表，使用泛型嵌套结构，支持多层嵌套。 
 * 4. **初始化**： 
 * - `children` 字段通过 `Lists.newArrayList()` 初始化为空列表，确保不会为 `null`。 
 * 5. **设计特点**： 
 * - 支持树形结构：通过 `children` 字段实现多级嵌套，适合表示具有层级关系的数据。 
 * - 灵活扩展：字段带有注释和元信息，便于在 API 文档中展示。 
 * 总结：这是一个典型的树形结构数据对象，用于表示空间策略中的资源及其层级关系，支持内置、删除等属性的灵活配置。 
**/
package com.cyberpeace.space.service.context.strategy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 空间策略框架Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceStrategyFrameBo {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 父母
	 *
	 * @see String
	 */
	private String parent;

	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否内置")
    private Boolean builtin;

	/**
	 * 移动
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否可删除")
	private Boolean removable;

	/**
	 * 子级
	 *
	 * @see List<SpaceStrategyFrameBo>
	 */
	private List<SpaceStrategyFrameBo> children = Lists.newArrayList();

}
