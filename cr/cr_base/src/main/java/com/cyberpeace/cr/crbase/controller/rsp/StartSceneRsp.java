/**
 * 该代码定义了一个名为 `StartSceneRsp` 的 Java 类，用于表示“开始场景”的响应数据结构。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个 DTO（Data Transfer Object），主要用于传输“开始场景”操作的响应数据。 
 * - 它包含一个字段 `mpSceneId`，表示创建的中台场景 ID。 
 * 2. **继承与实现**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于网络传输或持久化。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段 `mpSceneId` 提供了描述性注释。 
 * 4. **构造方法**： 
 * - 提供了一个带参构造方法，用于初始化 `mpSceneId` 字段。 
 * 5. **用途**： 
 * - 该类通常在后端服务中返回给前端或其他系统，用于表示“开始场景”操作的结果。 
 * 总结：这是一个简单的 DTO 类，用于封装“开始场景”操作的响应数据，支持序列化并提供了清晰的字段说明。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 开始场景响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class StartSceneRsp implements Serializable {

	/**
	 * 中台场景id
	 */
	@ApiModelProperty(value = "创建的中台场景id")
	private String mpSceneId;

	public StartSceneRsp(String mpSceneId) {
		this.mpSceneId = mpSceneId;
	}
}
