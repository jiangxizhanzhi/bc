package com.cyberpeace.cr.crbase.controller.req.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneOpsReq` 的 Java 类，用于表示场景操作请求体。以下是代码的简要分析： 
 * 1. **类名与功能**： 
 * - 类名为 `SceneOpsReq`，表明它是一个与场景操作相关的请求体。 
 * - 注释说明该类用于“场景老年退休金计划请求体”。 
 * 2. **继承与实现**： 
 * - 类实现了 `Serializable` 接口，这意味着它可以被序列化，通常用于在网络传输或持久化时保存对象状态。 
 * 3. **注解与框架**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，减少了样板代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供元数据，其中 `roomId` 字段标记为必填（`required = true`）。 
 * 4. **字段**： 
 * - `roomId`：表示房间ID，通过 Swagger 注解标明其用途为“业务实例id”，且是必需的。 
 * 5. **作者与日期**： 
 * - 注释中指明了作者为“SN”以及创建日期为“2024/11/08”。 
 * 总结：这是一个简单的 POJO（Plain Old Java Object）类，主要用于接收场景操作的请求参数，支持序列化，并通过注解增强了可读性和功能性。 
**/
@Getter
@Setter
public class SceneOpsReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "业务实例id", required = true)
	private String roomId;
}

