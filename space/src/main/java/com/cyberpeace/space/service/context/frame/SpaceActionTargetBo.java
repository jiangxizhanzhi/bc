package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 空间行动目标Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceFrameBoBase
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **类的基本信息** 
 * - 类名为 `SpaceActionTargetBo`，位于包 `com.cyberpeace.space.service.context.frame` 下。 
 * - 它继承自 `SpaceFrameBoBase`，并使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 类的注释表明它是一个用于表示“空间行动目标”的业务对象（BO），主要用于封装数据。 
 * #### 2. **字段说明** 
 * 该类定义了一些与“空间行动目标”相关的字段： 
 * - `resourceId`: 资源 ID，通过 Swagger 注解标明其描述为“资源id”。 
 * - `name`: 名称，描述为“名称”。 
 * - `description`: 描述，描述为“描述”。 
 * - `frame`: 所属行动框架，类型为 `List<IdAndName>`，描述为“所属行动框架”。 
 * - `createTime`: 创建时间，描述为“创建时间，前端不要传”，类型为 `Date`。 
 * #### 3. **方法说明** 
 * - `bo2Entity()`: 将当前 BO 对象转换为对应的实体类对象 `SpaceActionTarget`。实现方式是通过 `BeanUtils.copy` 方法将当前 BO 对象的属性值复制到实体类中。 
 * #### 4. **依赖关系** 
 * - 依赖于以下外部类或工具： 
 * - `SpaceActionTarget`: 实体类，用于持久化存储。 
 * - `IdAndName`: 模型类，通常用于表示包含 ID 和名称的对象。 
 * - `BeanUtils`: 工具类，提供对象之间的属性复制功能。 
 * - `SpaceFrameBoBase`: 父类，可能提供一些基础字段或方法。 
 * #### 5. **用途** 
 * - 该类主要用于在服务层和控制层之间传递数据，是一种典型的 DTO（Data Transfer Object）模式的实现。 
 * - 提供了从 BO 到实体类的转换方法，便于后续数据持久化操作。 
 * #### 6. **优点** 
 * - 使用了 Lombok 的注解，减少了样板代码的编写。 
 * - 提供了清晰的字段注释和类注释，便于理解其用途。 
 * - 封装了 BO 到实体类的转换逻辑，提高了代码的复用性。 
 * #### 7. **潜在改进点** 
 * - 如果 `BeanUtils.copy` 方法存在性能问题，可以考虑使用其他更高效的工具（如 MapStruct）。 
 * - 可以增加更多的校验逻辑（如注解验证）以确保数据的正确性。 
 * #### 总结 
 * `SpaceActionTargetBo` 是一个封装“空间行动目标”数据的业务对象类，提供了基本的字段、getter/setter 方法以及 BO 到实体类的转换方法。代码结构清晰，符合常见的 DTO 设计规范，但可以根据实际需求进一步优化性能和安全性。 
**/
@Getter
@Setter
public class SpaceActionTargetBo extends SpaceFrameBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "所属行动框架")
	private List<IdAndName> frame;

	@ApiModelProperty(value = "创建时间，前端不要传")
	private Date createTime;

	public SpaceActionTarget bo2Entity() {
		SpaceActionTarget entity = BeanUtils.copy(this, SpaceActionTarget.class);
		return entity;
	}

}

