package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SceneRsp implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 创建用户
	 */
	private String createUser;

	/**
	 * 创建用户名
	 */
	private String createUsername;

	/**
	 * 角色
	 */
	private List roles;

	/**
	 * 类型
	 */
	private Integer type;

	/**
	 * 参考类型
	 */
	private Integer referenceType;

	/**
	 * 引用元数据
	 */
	private String referenceMetaData;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 场景配置
	 */
	private Object sceneConfig;

	/**
	 * 测试场景
	 */
	private String testScenes;

	/**
	 * 应用场景
	 */
	private String appScenes;
}
