package com.cyberpeace.bs.service.bo.lc;

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

	public ReleaseTaskReq() {
		this.setTask(new Task());
	}

	public void setDeviceId(String id) {
		this.getTask().setDeviceId(id);
	}

	public void setDeviceIp(String ip) {
		this.getTask().setDeviceIp(ip);
	}

	public void setFlowChart(String id) {
		this.getFlowChartIds().add(new Long(id));
	}
}

@Data
class Task implements Serializable {

	private String deviceId;

	private String deviceIp;

	private Integer deviceSource = 2;

	private Integer execType = 1;

	private String name = "test";

	private String isRecordVideo = "0";

}