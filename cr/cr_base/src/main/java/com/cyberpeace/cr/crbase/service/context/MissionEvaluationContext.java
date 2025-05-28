/**
 * 这段代码定义了一个名为 `MissionEvaluationContext` 的 Java 类，属于任务评估模型。以下是简要分析： 
 * ### 功能描述 
 * 该类主要用于存储与任务评估相关的上下文信息，包括任务的基本信息、评估状态、评估者信息、得分等。 
 * ### 主要属性 
 * - **基本信息**：如 `resourceId`（资源ID）、`evaluateTime`（评估时间）、`roomId`（房间ID）、`taskId`（任务ID）。 
 * - **提交信息**：如 `submitUser`（提交用户）、`submitUserRole`（提交用户岗位）、`submitUserRoleId`（提交用户岗位ID）。 
 * - **评估信息**：如 `evaluatorId`（评估者ID）、`missionHash`（任务哈希）、`evaluateStatus`（评估状态）、`reason`（原因）、`detail`（详情）、`score`（得分）。 
 * - **任务相关**：如 `mission`（任务实体）、`answer`（答案）、`answerExtension`（答案外延）、`missionScore`（任务分数）。 
 * - **时间戳**：如 `createTime`（创建时间）。 
 * ### 特点 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成 getter 和 setter 方法。 
 * - 属性命名清晰，涵盖了任务评估的主要维度，适合用于任务评估场景。 
 * ### 总结 
 * 这是一个典型的 DTO（Data Transfer Object），用于在系统中传递任务评估的相关数据。 
**/
package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.Mission;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务评估模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionEvaluationContext {

	/**
	 * submit_log_hash
	 */
	private String resourceId;

	/**
	 * 评估时间
	 */
	private Date evaluateTime;

	/**
	 * 房间id
	 */
	private String roomId;

	/**
	 * 提交用户
	 */
	private String submitUser;

	/**
	 * 提交用户岗位
	 */
	private String submitUserRole;

	/**
	 * 提交用户岗位id
	 */
	private String submitUserRoleId;

	/**
	 * evaluator
	 */
	private String evaluatorId;

	/**
	 * 任务哈希
	 */
	private String missionHash;

	/**
	 * 评估状态
	 */
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	private Integer reason;

	/**
	 * 详情
	 */
	private String detail;

	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 任务
	 */
	private Mission mission;

	/**
	 * 任务id
	 */
	private String taskId;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 答案外延
	 */
	private String answerExtension;

	/**
	 * 任务分数
	 */
	private Integer missionScore;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
