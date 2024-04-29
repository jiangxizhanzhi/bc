package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

@Data
public class MissionStartContext implements Serializable {

	private String command;

	/**
	 * 场景上下文
	 */
	private CsceneContextBO context;

	/**
	 * 命令执行人
	 */
	private String exec_user;

	/**
	 * 场景id
	 */
	private String id;

	/**
	 * 指定镜像
	 */
	private String image;

	/**
	 * 节点id
	 */
	private String node_id;



}
