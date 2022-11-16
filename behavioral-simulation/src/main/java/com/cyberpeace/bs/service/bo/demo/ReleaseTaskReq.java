package com.cyberpeace.bs.service.bo.demo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.Data;

@Data
public class ReleaseTaskReq implements Serializable {

	private List<String> notifySettings = Lists.newArrayList();

	private Task task;

	private List<Long> flowChartIds = Lists.newArrayList();

	private List<String> prevSteps = Lists.newArrayList();

	private String taskStrategy = "";

	public void setDeviceId(String id) {
		this.getTask().setDeviceId(id);
	}

	public void setProjectId(String id) {
		this.getTask().setProjectId(id);
	}

	public void setEnvironmentId(String id) {
		this.getTask().setEnvironmentId(id);
	}
	public void setFlowChart(String id){
		this.getFlowChartIds().add(new Long(id));
	}

	public ReleaseTaskReq(){
		this.setTask(new Task());
	}

}

@Data
class Task implements Serializable {

	private String deviceId;

	private Integer deviceSource = 2;

	private Integer execType = 1;

	private String name = "test";

	private String projectId;

	private String isRecordVideo = "0";

	private String environmentId;
}
