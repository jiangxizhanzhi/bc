package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import com.cyberpeace.situation.constant.topo.ElementStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatusContext implements Serializable {

	/**
	 * 状态
	 */
	@ApiModelProperty("状态")
	private String status;

	public void setStatus(Integer status) {

		if (status == null) {
			return;
		}

		this.status = status.equals(0) ? ElementStatus.OFFLINE.getCode()
				: ElementStatus.RUNNING.getCode();
	}

}
