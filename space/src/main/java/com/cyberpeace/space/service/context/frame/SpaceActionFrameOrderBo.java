/**
 * 这段代码定义了一个名为 `SpaceActionFrameOrderBo` 的 Java 类，用于表示“空间行动框架次序”的业务对象（BO）。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 这是一个数据传输对象（DTO），主要用于封装与“空间行动框架次序”相关的业务数据。 
 * - 它包含了多个字段，每个字段都通过 `@ApiModelProperty` 注解描述了其用途。 
 * 2. **字段说明**： 
 * - `resourceId`, `applicationId`, `frameName`, `stepName`, `stepId`, `tacticId`, `tacticType`, `tacticName`, `userId`, `userName`, `sender`, `senderName`, `status`, `released`, `createTime`：这些字段分别表示不同的业务属性，如资源ID、框架名称、步骤信息、战法信息、用户信息、状态等。 
 * 3. **注解**： 
 * - `@Data`：Lombok 注解，自动生成 getter、setter、toString 等方法。 
 * - `@ApiModelProperty`：Swagger 注解，用于生成 API 文档时描述字段含义。 
 * 4. **方法**： 
 * - `bo2Entity()`：将当前 BO 对象转换为实体对象 `SpaceActionFrameOrder`，使用工具类 `BeanUtils.copy` 实现。 
 * 5. **设计意图**： 
 * - 该类作为业务逻辑层和数据库层之间的桥梁，负责数据的传递和转换。 
 * - 提供了清晰的数据结构定义，并支持从 BO 到实体的映射。 
 * 总结：这是一个典型的业务对象类，专注于封装与“空间行动框架次序”相关的数据，并提供了便捷的方法进行数据转换。 
**/

package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 空间行动框架次序Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Data
public class SpaceActionFrameOrderBo {

	@ApiModelProperty("id")
	private String resourceId;

	@ApiModelProperty("框架id")
	private String applicationId;

	@ApiModelProperty("框架名称")
	private String frameName;

	@ApiModelProperty("步骤")
	private String stepName;

	@ApiModelProperty("步骤id")
	private String stepId;

	@ApiModelProperty("战法id")
	private String tacticId;

	@ApiModelProperty("战法类型")
	private Integer tacticType;

	@ApiModelProperty("战法名称")
	private String tacticName;

	@ApiModelProperty("用户id")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("下发人")
	private String sender;

	@ApiModelProperty("下发人名称")
	private String senderName;

	@ApiModelProperty("状态，1完成2未完成 ")
	private Integer status;

	@ApiModelProperty("是否下发")
	private Boolean released = false;

	private Date createTime;

	public SpaceActionFrameOrder bo2Entity() {
		SpaceActionFrameOrder entity = BeanUtils.copy(this, SpaceActionFrameOrder.class);
		return entity;
	}

}
