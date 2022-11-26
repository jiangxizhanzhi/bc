package com.cyberpeace.situation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class SceneTrend implements Serializable {

	private Integer dataSize;

	private String time;

	public SceneTrend() {
	}

	public SceneTrend(Integer dataSize, String time) {
		this.dataSize = dataSize;
		this.time = time;
	}
}
