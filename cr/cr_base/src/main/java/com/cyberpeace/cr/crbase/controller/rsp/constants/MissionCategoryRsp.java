package com.cyberpeace.cr.crbase.controller.rsp.constants;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务类别响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MissionCategoryRsp` 的 Java 类，用于表示任务类别的响应数据。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装任务类别相关的响应信息，包含资源 ID 和名称。 
 * 2. **注解与框架**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加描述性注释，便于文档生成。 
 * 3. **字段说明**： 
 * - `resourceId`：表示任务类别的资源 ID。 
 * - `name`：表示任务类别的名称。 
 * 4. **构造方法**： 
 * - 提供了一个带参构造方法，用于初始化 `resourceId` 和 `name` 字段。 
 * 5. **设计目的**： 
 * - 该类是一个简单的数据传输对象（DTO），用于在系统中传递任务类别相关的数据。 
 * 总结：这是一个轻量级的数据传输类，通过 Lombok 和 Swagger 注解简化了代码编写和文档生成。 
**/
@Getter
@Setter
public class MissionCategoryRsp {

	/**
	 * 资源id
	 */
	@ApiModelProperty("类型id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	public MissionCategoryRsp(String resourceId, String name) {
		this.resourceId = resourceId;
		this.name = name;
	}
}

