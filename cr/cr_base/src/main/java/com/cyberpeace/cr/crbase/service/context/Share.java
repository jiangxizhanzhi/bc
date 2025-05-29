package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 授权
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `Share` 的 Java 类，位于 `com.cyberpeace.cr.crbase.service.context` 包中。以下是对其功能和结构的简要分析： 
 * 1. **类的功能**： 
 * 该类用于表示“共享”或“授权”的相关数据模型，包含共享模式和授权用户列表。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供元信息（如描述字段含义）。 
 * 3. **字段说明**： 
 * - `shareMode`：表示共享模式，类型为整数 (`Integer`)，用于标识授权的操作类型。 
 * - `teachers`：表示被授权的教师列表，类型为 `List<ShareTeacher>`，其中 `ShareTeacher` 是一个外部类或接口。 
 * 4. **用途**： 
 * 该类可能用于描述某种共享场景下的授权信息，例如某个系统中的资源授权或角色分配。 
 * 总结：这是一个简单的数据传输对象（DTO），主要用于封装与授权相关的共享模式和用户信息。 
**/
@Getter
@Setter
public class Share {

	/**
	 * 共享模式
	 */
	@ApiModelProperty(value = "授权操作类型")
	private Integer shareMode;

	/**
	 * 教师
	 */
	@ApiModelProperty(value = "授权操作用户列表")
	private List<ShareTeacher> teachers;
}

