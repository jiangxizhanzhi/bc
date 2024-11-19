package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 场景事件主机列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneEventHostListReq implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	private String sceneId;

	/**
	 * 启动时间
	 *
	 * @see Date
	 */
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	private Date endTime;

	/**
	 * 分页
	 *
	 * @see Integer
	 */
	private Integer page;

	/**
	 * 分页大小
	 *
	 * @see Integer
	 */
	private Integer pageSize;

	public SceneEventHostListReq(String sceneId) {
		this.sceneId = sceneId;
	}
}
