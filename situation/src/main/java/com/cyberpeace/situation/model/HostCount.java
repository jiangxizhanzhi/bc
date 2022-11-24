package com.cyberpeace.situation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostCount implements Serializable {

	private String hostName;

	private Integer times;

	public HostCount(String hostName, Integer times) {
		this.hostName = hostName;
		this.times = times;
	}

	public HostCount() {
	}
}
