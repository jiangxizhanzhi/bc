/**
 * 这段代码定义了一个名为 `MissionTemplateModifyReq` 的 Java 类，它是 `MissionTemplateReq` 的子类。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是用于任务模板修改请求的实体类。 
 * - 它继承自 `MissionTemplateReq`，可能包含一些通用的任务模板字段。 
 * 2. **主要字段**： 
 * - `resourceId`：表示资源 ID（任务 ID），通过 `@ApiModelProperty` 注解标记为必填项。 
 * 3. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化代码。 
 * - Swagger 的 `@ApiModelProperty` 注解用于描述字段在 API 文档中的信息。 
 * 4. **设计目的**： 
 * - 该类主要用于接收任务模板修改请求的数据，便于前后端交互。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装任务模板修改所需的信息，并支持 Swagger 文档生成和 Lombok 自动化代码生成。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板修改请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionTemplateModifyReq extends MissionTemplateReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String resourceId;

}
