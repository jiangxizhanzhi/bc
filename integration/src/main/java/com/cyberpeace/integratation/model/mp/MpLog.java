package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

/**
 * 中台日志
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see Serializable
 * @since 1.0.0
 */
public class MpLog implements Serializable {

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 日志来源
	 *
	 * @see String
	 */
	private String from;

	/**
	 * 日志类型
	 *
	 * @see Integer
	 */
	private Integer log_type;

	/**
	 * 操作人IP
	 *
	 * @see String
	 */
	private String op_ip;

	/**
	 * 模块
	 *
	 * @see Integer
	 */
	private Integer op_module;

	/**
	 * 操作对象
	 *
	 * @see String
	 */
	private String op_target;

	/**
	 * 操作类型
	 *
	 * @see Integer
	 */
	private Integer op_type;

	/**
	 * 操作主题
	 *
	 * @see String
	 */
	private String title;

	/**
	 * 用户ID
	 *
	 * @see String
	 */
	private String user_id;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	private String user_name;

	/**
	 * 用户类型（用户，客户端）
	 *
	 * @see String
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
