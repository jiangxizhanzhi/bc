package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务流列表查询
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskFlowListQuery` 的 Java 类，用于表示评估任务流列表查询的参数。以下是详细的分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.context` 
 * - **类名**: `EvalTaskFlowListQuery` 
 * - **继承关系**: 继承自 `PageReqBase` 类，并实现了 `Serializable` 接口 
 * ### 主要功能 
 * 该类主要用于封装评估任务流列表查询所需的参数，包括交互式类型、任务ID 和节点ID。 
 * ### 类属性 
 * - **interactiveType**: 一个整数类型的属性，表示交互类型。可能的值为： 
 * - 0: 背景流量 
 * - 1: 攻击流量 
 * - 2: 全流量 
 * - **taskId**: 一个字符串类型的属性，表示测评靶场任务ID。 
 * - **nodeId**: 一个字符串类型的属性，表示节点ID。 
 * ### 注解 
 * - 使用了 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个属性的用途和名称。 
 * ### 方法 
 * - 提供了三个 setter 方法，分别对应 `interactiveType`、`taskId` 和 `nodeId` 属性。 
 * ### 总结 
 * 这个类是一个数据传输对象（DTO），用于在服务层之间传递评估任务流列表查询的参数。它继承了 `PageReqBase` 类，可能包含了分页相关的属性和方法。通过使用 Lombok 的注解，减少了样板代码，提高了代码的可读性和维护性。 
**/
@Getter
@Setter
public class EvalTaskFlowListQuery extends PageReqBase implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 交互式类型
	 */
	@ApiModelProperty(name = "interactive_type", value = "交互类型 0:背景流量， 1：攻击流量，2：全流量")
	private Integer interactiveType;

	/**
	 * 任务id
	 */
	@ApiModelProperty(name = "task_id", value = "测评靶场任务id")
	private String taskId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(name = "node_id", value = "节点id")
	private String nodeId;

	public void setInteractive_type(Integer interactiveType) {
		this.interactiveType = interactiveType;
	}

	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	public void setNode_id(String nodeId) {
		this.nodeId = nodeId;
	}

}

