/**
 * 这段代码定义了一个名为 `ShareReq` 的 Java 类，位于包 `com.cyberpeace.cr.crbase.controller.req` 中。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `ShareReq` 是一个用于共享请求的实体类，通常用于接收前端传递的共享相关参数。 
 * 2. **依赖与注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 `javax.validation.constraints.NotNull` 注解，确保字段不能为空。 
 * - 使用了 `io.swagger.annotations.ApiModelProperty` 注解，为字段添加元数据，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `ids`: 模板 ID 列表，用于标识需要共享的资源。 
 * - `shareMode`: 共享模式，表示授权类别。 
 * - `shareTeachers`: 授权人员 ID 列表，用于指定共享的目标用户。 
 * 4. **适用场景**： 
 * - 该类适用于需要处理共享或授权逻辑的场景，例如教育资源的共享、权限管理等。 
 * 总体而言，这是一个简单的数据传输对象（DTO），用于封装共享请求的相关信息。 
**/
package com.cyberpeace.cr.crbase.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 共享请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ShareReq {

	/**
	 * id列表
	 */
	@ApiModelProperty(value = "模板id")
	@NotNull
	private List<String> ids;

	/**
	 * 共享模式
	 */
	@ApiModelProperty(value = "授权类别")
	@NotNull
	private Integer shareMode;

	/**
	 * 分享教师
	 */
	@ApiModelProperty(value = "授权人员id")
	@NotNull
	private List<String> shareTeachers;

}
