/**
 * 这段代码定义了一个名为 `SpaceActionFrameTargetTypeBo` 的 Java 类，它是 `SpaceBoBase` 的子类。以下是代码的主要内容和功能分析： 
 * 1. **类的继承与注解**： 
 * - `@Data` 注解来自 Lombok 库，它自动生成了该类的 getter、setter、toString、equals 和 hashCode 方法。 
 * - `SpaceActionFrameTargetTypeBo` 继承自 `SpaceBoBase`，表明它是一个业务对象（BO），通常用于封装业务逻辑的数据。 
 * 2. **字段说明**： 
 * - `resourceId`：表示资源的唯一标识符。 
 * - `name`：表示某个名称。 
 * 3. **方法**： 
 * - `bo2Entity()`：将当前 BO 对象转换为实体类 `SpaceActionFrameTargetType`。使用了 `BeanUtils.copy` 方法来实现属性的复制。 
 * 4. **注释与文档**： 
 * - 类上有详细的 Javadoc，描述了类的功能、作者、版本等信息，并指出了它与 `SpaceBoBase` 的关系。 
 * 总结来说，这个类主要用于在业务层和数据访问层之间传递数据，提供了一个方便的方法将 BO 转换为实体对象。 
**/

package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameTargetType;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间行动框架目标类型Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Data
public class SpaceActionFrameTargetTypeBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	public SpaceActionFrameTargetType bo2Entity() {
		SpaceActionFrameTargetType entity = BeanUtils.copy(this, SpaceActionFrameTargetType.class);
		return entity;
	}

}
    