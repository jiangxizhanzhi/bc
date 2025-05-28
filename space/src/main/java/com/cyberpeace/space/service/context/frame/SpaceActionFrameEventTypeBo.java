/**
 * 这段代码定义了一个名为 `SpaceActionFrameEventTypeBo` 的 Java 类，它是 `SpaceBoBase` 的子类。以下是代码的简要分析： 
 * 1. **类定义**: 
 * - `SpaceActionFrameEventTypeBo` 是一个 Lombok 注解 `@Data` 标记的类，这意味着它自动生成了 getter、setter、toString、equals 和 hashCode 方法。 
 * - 它继承自 `SpaceBoBase`。 
 * 2. **字段**: 
 * - `resourceId`: 用于存储资源 ID，带有 Swagger 注解 `@ApiModelProperty` 描述其用途。 
 * - `name`: 用于存储名称，同样带有 Swagger 注解描述。 
 * 3. **方法**: 
 * - `bo2Entity()`: 将当前对象转换为 `SpaceActionFrameEventType` 实体对象。使用了 `BeanUtils.copy` 方法来复制字段。 
 * 4. **注解**: 
 * - 使用了 Lombok 的 `@Data` 注解简化了代码编写。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述字段的用途，便于 API 文档生成。 
 * 总体来说，这个类的主要功能是作为业务对象（BO）与实体对象（Entity）之间的桥梁，提供了一种将 BO 转换为 Entity 的便捷方式。 
**/

package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameEventType;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间行动框架事件类型Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Data
public class SpaceActionFrameEventTypeBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	public SpaceActionFrameEventType bo2Entity() {
		SpaceActionFrameEventType entity = BeanUtils.copy(this, SpaceActionFrameEventType.class);
		return entity;
	}

}
    