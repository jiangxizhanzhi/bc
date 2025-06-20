package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 实操认证信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `OperationAuthInfo` 的 Java 类，用于表示实操认证信息。以下是对其主要特性和功能的简要分析： 
 * ### 类概述 
 * - **包声明**：该类位于 `com.cyberpeace.oj.ojbase.controller.res` 包中。 
 * - **注解**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - **实现接口**：实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * ### 属性 
 * - **operationAuthType**： 
 * - 类型：`Integer` 
 * - 注释说明：授权操作类型，具体为共享（值为1）或自定义（值为2）。 
 * - Swagger 注解：通过 `@ApiModelProperty` 描述了该字段的用途。 
 * - **operationAuthIds**： 
 * - 类型：`List<OperationUserInfo>` 
 * - 注释说明：授权操作用户编号的列表。 
 * - Swagger 注解：通过 `@ApiModelProperty` 描述了该字段的用途。 
 * ### 主要特性 
 * - **数据传输对象 (DTO)**：这个类很可能用作数据传输对象，用于在不同层之间传递数据，例如从控制器层到服务层。 
 * - **可序列化**：由于实现了 `Serializable` 接口，该类的对象可以在需要时进行序列化和反序列化，便于存储或网络传输。 
 * - **Swagger 文档**：通过 `@ApiModelProperty` 注解，该类可以自动地与 Swagger 集成，生成相关的 API 文档。 
 * ### 使用场景 
 * - **API 设计**：在设计 RESTful API 时，这个类可以作为返回给客户端的数据结构的一部分。 
 * - **权限管理**：根据 `operationAuthType` 和 `operationAuthIds`，可以实现不同的权限控制逻辑，比如基于角色的访问控制（RBAC）。 
 * 总之，这段代码定义了一个简洁且功能明确的数据传输对象，适用于实操认证信息的处理。 
**/
@Getter
@Setter
public class OperationAuthInfo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 实操认证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

	/**
	 * 实操认证id列表
	 *
	 * @see List<OperationUserInfo>
	 */
	@ApiModelProperty("授权操作用户编号")
	private List<OperationUserInfo> operationAuthIds;

}

