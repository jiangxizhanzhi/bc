/**
 * 这段代码定义了一个名为 `SpaceTacticDefenseSystemBo` 的类，它是 `SpaceBoBase` 的子类。以下是代码的主要特点和功能： 
 * 1. **类的功能**： 
 * - `SpaceTacticDefenseSystemBo` 是一个业务对象（BO），用于表示空间战法防御系统的数据模型。 
 * - 它继承了 `SpaceBoBase`，并添加了一些特定的字段来描述防御系统的属性。 
 * 2. **字段说明**： 
 * - `resourceId`：资源ID，通过 `@ApiModelProperty` 注解标明其描述为“资源id”。 
 * - `name`：系统名称，描述为“名称”。 
 * - `stage`：阶段，类型为整数，描述为“阶段，type=48”。 
 * - `definition`：技战法定义，描述为“技战法定义”。 
 * 3. **方法**： 
 * - `bo2Entity()`：将当前 BO 对象转换为实体对象 `SpaceTacticDefenseSystem`。使用了 `BeanUtils.copy` 方法来进行对象之间的属性复制。 
 * 4. **注解**： 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供元信息，便于文档生成和接口测试。 
 * 5. **继承与扩展**： 
 * - 继承自 `SpaceBoBase`，可能包含一些通用的字段或方法，具体需要查看 `SpaceBoBase` 的实现。 
 * 总结：该类是一个专门用于表示空间战法防御系统的业务对象，提供了字段定义、转换方法以及注解支持，便于在业务逻辑中使用和文档化。 
**/

package com.cyberpeace.space.service.context.algorithm;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceTacticDefenseSystem;
import com.cyberpeace.space.model.SpaceBoBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间战法防御系统Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Data
public class SpaceTacticDefenseSystemBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "阶段，type=48")
	private Integer stage;

	@ApiModelProperty(value = "技战法定义")
	private String definition;

	public SpaceTacticDefenseSystem bo2Entity() {
		SpaceTacticDefenseSystem entity = BeanUtils.copy(this, SpaceTacticDefenseSystem.class);
		return entity;
	}

}
