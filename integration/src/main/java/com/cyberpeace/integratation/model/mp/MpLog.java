package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

/**
 * 中台日志
 *
 * @author SN
 * @date 2024/11/08
 */
public class MpLog implements Serializable {

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
