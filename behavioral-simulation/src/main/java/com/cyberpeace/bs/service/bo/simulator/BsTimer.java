package com.cyberpeace.bs.service.bo.simulator;

import java.util.Timer;

/**
 * 定时任务，增加一个运行状态
 * @author SN
 */
public class BsTimer extends Timer {

	private Boolean isRunning = true;

	private Integer index;

	public void activate() {
		this.isRunning = true;
	}

	public void deactivate() {
		this.isRunning = false;
	}

	public Boolean ifActive() {
		return this.isRunning;
	}

}
