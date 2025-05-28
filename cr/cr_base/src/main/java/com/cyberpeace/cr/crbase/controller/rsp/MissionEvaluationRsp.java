/**
 * 这段代码定义了一个名为 `MissionEvaluationRsp` 的 Java 类，用于表示任务评估的响应数据结构。以下是对其的简要分析： 
 * ### 主要功能 
 * 该类主要用于封装任务评估的相关信息，包括评估的基本信息、评估状态、评分细节以及相关的提交信息等。 
 * ### 关键特性 
 * 1. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解为每个字段提供描述性信息，便于生成 API 文档。 
 * 2. **嵌套类**： 
 * - 内部类 `SubmitInfo` 用于封装提交信息，包括提交时间和用户答案。 
 * 3. **字段说明**： 
 * - 包含多个字段，如 `resourceId`, `evaluateTime`, `roomId`, `submitUserName` 等，分别对应不同的评估相关信息。 
 * - 引用了外部类 `Mission` 和内部类 `SubmitInfo`。 
 * 4. **设计目的**： 
 * - 该类的设计目的是为了标准化任务评估的响应格式，方便在前后端交互中传递数据。 
 * ### 总结 
 * 这是一个典型的 DTO（Data Transfer Object）类，用于在服务端和客户端之间传输数据。通过使用 Lombok 和 Swagger 注解，提高了代码的可读性和开发效率。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.Date;

import com.cyberpeace.cr.crbase.dao.entity.Mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务评估响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionEvaluationRsp {

	/**
	 * submit_log_hash
	 */
	@ApiModelProperty(value = "评估编号")
	private String resourceId;

	/**
	 * 评估时间
	 */
	@ApiModelProperty(value = "评估时间")
	private Date evaluateTime;

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 提交用户名称
	 */
	@ApiModelProperty(value = "提交人名称")
	private String submitUserName;

	/**
	 * 提交用户岗位
	 */
	@ApiModelProperty(value = "提交人角色")
	private String submitUserRole;

	/**
	 * 提交用户岗位资源id
	 */
	@ApiModelProperty(value = "提交人角色id")
	private String submitUserRoleResourceId;

	/**
	 * 提交用户
	 */
	@ApiModelProperty(value = "提交人id")
	private String submitUser;

	/**
	 * 计算器名称
	 */
	@ApiModelProperty(value = "评估人名称")
	private String evaluatorName;

	/**
	 * 计算器
	 */
	@ApiModelProperty(value = "评估人id")
	private String evaluator;

	/**
	 * 任务哈希
	 */
	@ApiModelProperty(value = "任务id")
	private String missionHash;

	/**
	 * 提交日志哈希
	 */
	@ApiModelProperty(value = "提交记录id")
	private String submitLogHash;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估状态")
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	@ApiModelProperty(value = "评估理由")
	private Integer reason;

	/**
	 * 详情
	 */
	@ApiModelProperty(value = "评估细节")
	private String detail;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;

	/**
	 * 总分
	 */
	@ApiModelProperty(value = "满分")
	private Integer totalScore;

	/**
	 * 任务数据
	 */
	@ApiModelProperty(value = "任务数据")
	private Mission missionData;

	/**
	 * 提交信息
	 */
	@ApiModelProperty(value = "提交信息")
	private SubmitInfo submitInfo;

	@Data
	public class SubmitInfo {

		@ApiModelProperty(value = "提交时间")
		private Date createTime;

		@ApiModelProperty(value = "提交答案")
		private String userAnswer;
	}
}
