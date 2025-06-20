package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 帮助状态博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `HelpStatusBo` 的 Java 类，用于表示某种帮助状态。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`HelpStatusBo` 
 * - **实现接口**：`Serializable` 
 * ### 注解 
 * - 使用了 `@Getter` 和 `@Setter` 注解，表明所有属性都有对应的 getter 和 setter 方法。 
 * - 使用了 `@ApiModelProperty` 注解，这是 Swagger 注解，用于描述 API 中该字段的用途。 
 * ### 属性 
 * - `userId`：表示用户的唯一标识符，类型为 `String`。 
 * - `lessonId`：表示课程的唯一标识符，类型为 `String`。 
 * - `nodeId`：表示某个特定节点的唯一标识符，类型为 `String`。 
 * - `status`：表示当前的帮助状态，类型为 `Integer`。可能的状态值有： 
 * - `0`：未请求协助 
 * - `1`：请求协助 
 * - `2`：协助中 
 * - `3`：协助已结束 
 * ### 作者信息 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * ### 总结 
 * `HelpStatusBo` 类是一个简单的数据传输对象（DTO），用于在系统内部或与外部系统通信时传递帮助状态的信息。它包含四个属性，分别对应用户、课程、节点和状态。这个类主要用于记录和管理用户在学习过程中请求和接受帮助的状态。 
**/
@Getter
@Setter
public class HelpStatusBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点编号")
	private String nodeId;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("状态 0=未请求协助 1=请求协助 2=协助中 3=协助已结束")
	private Integer status;

}

