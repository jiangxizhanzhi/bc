package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 在获取任务列表接口使用
 *
 * @author@date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SchemeListMissionRsp` 的 Java 类，用于表示从获取任务列表接口返回的任务数据。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * 该类实现了 `Serializable` 接口，通常用于序列化对象以便在网络中传输或持久化存储。它主要用于封装任务的相关信息。 
 * 2. **注解与功能**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，方便生成 API 文档。 
 * 3. **字段说明**： 
 * - `resourceId`：任务的唯一标识符。 
 * - `title`：任务的名称。 
 * - `option`：任务的选择项（可能是任务相关的选项）。 
 * - `category`：任务的类别。 
 * - `description`：任务的详细描述。 
 * - `state`：任务的状态，用整数表示。 
 * 4. **设计目的**： 
 * 这个类是专门为获取任务列表接口设计的响应模型，用于将任务信息以结构化的方式传递给客户端。 
 * 总结：这是一个简洁且功能明确的数据传输对象（DTO），适用于任务管理系统的 API 响应场景。 
**/
@Getter
@Setter
public class SchemeListMissionRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 选择
	 */
	@ApiModelProperty("选项")
	private String option;

	/**
	 * 类别
	 */
	@ApiModelProperty("任务类别")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 州
	 */
	@ApiModelProperty("任务状态")
	private Integer state;

}

