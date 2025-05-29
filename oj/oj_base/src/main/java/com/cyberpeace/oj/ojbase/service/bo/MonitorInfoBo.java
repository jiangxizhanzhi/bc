package com.cyberpeace.oj.ojbase.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 监控信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `MonitorInfoBo` 的 Java 类，用于表示监控信息。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.service.bo` 
 * - **类名**：`MonitorInfoBo` 
 * - **实现接口**：`Serializable` 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述各个字段。 
 * ### 属性 
 * 1. **server** 
 * - 类型：`String` 
 * - 描述：服务器名称 
 * - 注解：`@ApiModelProperty("节点名称")` 
 * 2. **userName** 
 * - 类型：`String` 
 * - 描述：用户名称 
 * - 注解：`@ApiModelProperty("用户名称")` 
 * 3. **monitorSessions** 
 * - 类型：`JSONObject` 
 * - 描述：监控会话信息 
 * - 注解：`@ApiModelProperty("会话信息")` 
 * ### 作者和版本信息 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * ### 总结 
 * - `MonitorInfoBo` 类是一个简单的数据传输对象（DTO），用于封装监控相关的服务器、用户和会话信息。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码。 
 * - 使用 Swagger 注解方便生成 API 文档。 
 * - 实现 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 这个类主要在需要传递监控信息时使用，特别是在微服务架构中，可能通过 REST API 返回这种监控信息。 
**/
@Getter
@Setter
public class MonitorInfoBo implements Serializable {

	/**
	 * 服务器
	 *
	 * @see String
	 */
	@ApiModelProperty("节点名称")
	private String server;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名称")
	private String userName;

	/**
	 * 监控会话
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("会话信息")
	private JSONObject monitorSessions;

}

