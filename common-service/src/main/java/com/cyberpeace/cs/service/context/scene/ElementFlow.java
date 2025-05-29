package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 元素流
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 功能概述 
 * `ElementFlow` 是一个 Java 类，用于表示某种元素流对象。它包含了多个字段来描述元素流的各种属性，如 ID、名称、描述、类型、状态等，并提供了一些枚举类（`TaskStatusEnum` 和 `CommonStatusEnum`）来规范化状态值。 
 * #### 主要特性 
 * 1. **字段说明**： 
 * - `id`: 元素流的唯一标识符。 
 * - `name`: 元素流的名称。 
 * - `desc`: 对元素流的详细描述。 
 * - `type`: 元素流的类型。 
 * - `category`: 分类，整数值。 
 * - `interactiveType`: 交互式类型的整数值。 
 * - `commonStatus`: 表示元素流的常见状态，默认为 0（空闲）。 
 * - `status`: 当前状态。 
 * - `statusText`: 状态的文字描述。 
 * - `taskId`: 关联的任务 ID。 
 * - `count`: 执行次数。 
 * - `sourceIp`: 来源 IP 地址。 
 * 2. **枚举类**： 
 * - `TaskStatusEnum`: 定义了任务的状态，包括未定义、初始化、运行中、停止中、已停止、未启动过等。 
 * - `CommonStatusEnum`: 定义了元素流的常见状态，包括空闲和活动。 
 * 3. **方法**： 
 * - `setCommonStatus`: 根据传入的任务状态调整元素流的常见状态。如果任务状态是“已停止”或“未启动过”，则将常见状态设置为“空闲”；否则设置为“活动”。 
 * #### 设计特点 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法，减少冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，方便生成 API 文档。 
 * - 提供了两个枚举类来规范状态值，避免硬编码问题。 
 * - 提供了一个实用的方法 `setCommonStatus`，可以根据任务状态动态更新元素流的常见状态。 
 * #### 总结 
 * 该类设计合理，字段齐全，功能明确，适合用作描述元素流的对象模型。通过使用枚举类和方法逻辑，确保了状态值的一致性和可维护性。 
**/
@Getter
@Setter
public class ElementFlow implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty("ID")
	private String id;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String desc;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	private String type;

	/**
	 * 类别
	 */
	@ApiModelProperty("分类")
	private Integer category;

	/**
	 * 交互式类型
	 */
	@ApiModelProperty("交互类型")
	private Integer interactiveType;

	/**
	 * 常见状态
	 */
	@ApiModelProperty("通常状态")
	private Integer commonStatus = 0;

	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private Integer status;

	/**
	 * 状态发短信
	 */
	@ApiModelProperty("状态信息")
	private String statusText;

	/**
	 * 任务id
	 */
	@ApiModelProperty("关联测评任务ID")
	private String taskId;

	/**
	 * 计数
	 */
	@ApiModelProperty("执行数")
	private Integer count;

	/**
	 * 源ip地址
	 */
	@ApiModelProperty("来源IP")
	private String sourceIp;

	public enum TaskStatusEnum {

		UNDEFINED(-1,"未定义"),
		INIT(0, "初始化"),
		RUNNING(1, "运行中"),
		STOPPING(2, "停止中"),
		STOPPED(3, "已停止"),
		ZERO(4, "未启动过");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		TaskStatusEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

	public enum CommonStatusEnum {

		FREE(0, "空闲"),
		ACTIVE(1, "活动");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		CommonStatusEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

	public void setCommonStatus(Integer taskStatus) {

		Integer commonStatus = CommonStatusEnum.ACTIVE.getCode();

		if (taskStatus.equals(TaskStatusEnum.STOPPED.getCode())
				|| taskStatus.equals(TaskStatusEnum.ZERO.getCode())) {
			commonStatus = CommonStatusEnum.FREE.getCode();
		}
		this.commonStatus = commonStatus;
	}

}

