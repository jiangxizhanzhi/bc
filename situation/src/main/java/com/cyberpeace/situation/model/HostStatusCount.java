package com.cyberpeace.situation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class HostStatusCount implements Serializable {

	private String status;

	private Integer times;

	public HostStatusCount(String status, Integer times) {
		this.status = status;
		this.times = times;
	}
}
