package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class ReleaseTaskReq implements Serializable {

	private List<String> notifySettings = Lists.newArrayList();

	private Task task;

	private List<Long> flowChartIds = Lists.newArrayList();

	private List<String> prevSteps = Lists.newArrayList();

	private String taskStrategy = "";

	private String ip;

	private Boolean isUsingIp;

	private List<ReqVariable> variable = Lists.newArrayList();

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

	public void addVariable(String name,String value){
		ReqVariable variable = new ReqVariable(name,value);
		this.variable.add(variable);
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

@Data
class ReqVariable implements Serializable{
	private String name;
	private String value;
	private String variableType = "Text";

	public ReqVariable(String name, String value){
		this.name = name;
		this.value = value;
	}
}