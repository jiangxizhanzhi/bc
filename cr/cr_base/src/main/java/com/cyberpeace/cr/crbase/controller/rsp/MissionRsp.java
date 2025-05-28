/**
 * 这段代码定义了一个名为 `MissionRsp` 的 Java 类，用于表示任务相关的响应数据。以下是简要分析： 
 * ### 主要功能： 
 * - **类用途**：该类是一个任务响应的数据传输对象（DTO），用于封装任务的相关信息。 
 * - **属性**：类中包含多个字段，每个字段都有对应的注释说明其含义。 
 * - **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供元数据，便于生成 API 文档。 
 * ### 关键点： 
 * 1. **字段说明**： 
 * - `resourceId`、`title`、`option`、`type`、`category`、`description`、`writeUp`、`showWriteup`、`userAnswer` 和 `remark` 分别表示任务的不同属性。 
 * 2. **序列化支持**：实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 3. **可读性**：通过注释清晰地描述了每个字段的用途，便于开发者理解。 
 * ### 总结： 
 * 这是一个简单的 DTO 类，主要用于任务相关的信息传递和展示，设计上注重清晰性和可维护性。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionRsp implements Serializable {

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
	 * 类型
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 类别
	 */
	@ApiModelProperty("任务类别id")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 写入向上
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 显示 Writeup
	 */
	@ApiModelProperty("是否展示任务题解")
	private Boolean showWriteup;

	/**
	 * 用户答案
	 */
	@ApiModelProperty("用户答案")
	private String userAnswer;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;
}
