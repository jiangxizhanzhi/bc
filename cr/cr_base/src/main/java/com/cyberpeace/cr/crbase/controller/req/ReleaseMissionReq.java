package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 释放任务请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `ReleaseMissionReq` 的 Java 类，用于表示“释放任务请求体”的请求参数。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装释放任务请求所需的数据结构。 
 * 2. **注解与框架支持**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，并标明是否必填。 
 * 3. **字段说明**： 
 * - `roleResourceId`：表示角色 ID，必填。 
 * - `missionResourceId`：表示单个任务 ID，必填。 
 * - `missionResourceIds`：表示任务 ID 列表，必填。 
 * 4. **设计特点**： 
 * - 字段设计清晰，包含单值（如 `roleResourceId` 和 `missionResourceId`）和多值（如 `missionResourceIds`）。 
 * - 使用注解提升了代码可读性和开发效率。 
 * 总结：这是一个简洁且功能明确的 POJO 类，适用于接收释放任务请求的相关数据。 
**/
@Getter
@Setter
public class ReleaseMissionReq {

	/**
	 * 岗位资源id
	 */
	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	/**
	 * 任务资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	/**
	 * 任务资源id列表
	 */
	@ApiModelProperty(value = "任务ids", required = true)
	private List<String> missionResourceIds;
}
