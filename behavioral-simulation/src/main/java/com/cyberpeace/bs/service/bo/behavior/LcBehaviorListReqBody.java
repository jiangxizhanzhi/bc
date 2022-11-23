package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import lombok.Data;

@Data
public class LcBehaviorListReqBody implements Serializable {

	private String flowChartName;

	private Integer page;

	private Integer size;

	private Integer netModel;
}
