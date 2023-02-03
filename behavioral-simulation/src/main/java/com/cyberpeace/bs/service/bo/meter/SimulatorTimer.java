package com.cyberpeace.bs.service.bo.meter;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.Data;

@Data
public class SimulatorTimer implements Serializable {

	private String simulatorId;

	private List<TimerBo> userModels = Lists.newArrayList();

	public SimulatorTimer(String simulatorId) {
		this.simulatorId = simulatorId;
	}

	public void addUser(String userModelId, String userName) {
		TimerBo timerBo = new TimerBo();
		timerBo.setUserModelId(userModelId);
		timerBo.setUserName(userName);
		this.userModels.add(timerBo);
	}
}

@Data
class TimerBo implements Serializable {

	private String userModelId;

	private String userName;
}
