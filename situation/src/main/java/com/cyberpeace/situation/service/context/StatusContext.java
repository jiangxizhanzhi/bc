package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import com.cyberpeace.situation.constant.ElementStatus;

import lombok.Data;

@Data
public class StatusContext implements Serializable {

	/**
	 * 状态
	 */
	private String status;

	public void setStatus(Integer status) {
		this.status = status == null || status.equals(0) ? ElementStatus.OFFLINE.getCode()
				: ElementStatus.RUNNING.getCode();
	}

}
