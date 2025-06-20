package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

/**
 * 中台日志
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpLog` 的 Java 类，用于表示中台日志。以下是该类的简要分析： 
 * ### 类名和包 
 * - **包**: `com.cyberpeace.integratation.model.mp` 
 * - **类名**: `MpLog` 
 * ### 实现的接口 
 * - `Serializable`: 表示该类的对象可以序列化，以便在网络上传输或持久化到磁盘。 
 * ### 成员变量 
 * - `content`: 日志的内容。 
 * - `description`: 日志的描述。 
 * - `from`: 日志的来源。 
 * - `log_type`: 日志的类型，类型为整数。 
 * - `op_ip`: 操作人的IP地址。 
 * - `op_module`: 操作模块，类型为整数。 
 * - `op_target`: 操作对象。 
 * - `op_type`: 操作类型，类型为整数。 
 * - `title`: 日志的操作主题。 
 * - `user_id`: 用户ID。 
 * - `user_name`: 用户名称。 
 * - `user_type`: 用户类型（用户或客户端）。 
 * ### 方法 
 * - 所有成员变量都有对应的 getter 和 setter 方法，用于获取和设置它们的值。 
 * ### 总结 
 * - `MpLog` 类用于存储和操作日志信息。 
 * - 它包含多种日志相关的属性，并提供了这些属性的访问器和修改器方法。 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，便于在分布式系统中使用。 
 * 这个类可以用于记录各种操作日志，包括操作内容、描述、来源、类型等详细信息。 
**/
public class MpLog implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 内容
	 */
	private String content;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 日志来源
	 */
	private String from;

	/**
	 * 日志类型
	 */
	private Integer log_type;

	/**
	 * 操作人IP
	 */
	private String op_ip;

	/**
	 * 模块
	 */
	private Integer op_module;

	/**
	 * 操作对象
	 */
	private String op_target;

	/**
	 * 操作类型
	 */
	private Integer op_type;

	/**
	 * 操作主题
	 */
	private String title;

	/**
	 * 用户ID
	 */
	private String user_id;

	/**
	 * 用户名称
	 */
	private String user_name;

	/**
	 * 用户类型（用户，客户端）
	 */
	private String user_type;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Integer getLog_type() {
		return log_type;
	}

	public void setLogType(Integer logType) {
		this.log_type = logType;
	}

	public String getOp_ip() {
		return op_ip;
	}

	public void setOpIp(String opIp) {
		this.op_ip = opIp;
	}

	public Integer getOp_module() {
		return op_module;
	}

	public void setOpModule(Integer opModule) {
		this.op_module = opModule;
	}

	public String getOp_target() {
		return op_target;
	}

	public void setOpTarget(String opTarget) {
		this.op_target = opTarget;
	}

	public Integer getOp_type() {
		return op_type;
	}

	public void setOpType(Integer opType) {
		this.op_type = opType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUserId(String userId) {
		this.user_id = userId;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUserName(String userName) {
		this.user_name = userName;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUserType(String userType) {
		this.user_type = userType;
	}
}

