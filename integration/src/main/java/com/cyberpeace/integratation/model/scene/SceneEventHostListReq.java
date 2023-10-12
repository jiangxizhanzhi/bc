package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SceneEventHostListReq implements Serializable {

	private String sceneId;

	private Date startTime;

	private Date endTime;

	private Integer page;

	private Integer pageSize;

	public SceneEventHostListReq(String sceneId) {
		this.sceneId = sceneId;
	}
}
