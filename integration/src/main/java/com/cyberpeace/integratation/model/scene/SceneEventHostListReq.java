package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 场景事件主机列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SceneEventHostListReq implements Serializable {

	/**
	 * 场景id
	 */
	private String sceneId;

	/**
	 * 启动时间
	 */
	private Date startTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 分页
	 */
	private Integer page;

	/**
	 * 分页大小
	 */
	private Integer pageSize;

	public SceneEventHostListReq(String sceneId) {
		this.sceneId = sceneId;
	}
}
