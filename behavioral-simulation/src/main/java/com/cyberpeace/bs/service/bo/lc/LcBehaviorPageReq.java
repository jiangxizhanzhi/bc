package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;

import lombok.Data;

@Data
public class LcBehaviorPageReq implements Serializable {

	private Integer page;

	private Integer size;

	private Integer deviceType;

	private Integer networkType;

	private String name;
}
