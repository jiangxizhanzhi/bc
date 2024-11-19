package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import com.cyberpeace.situation.constant.ElementStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 状态上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class StatusContext implements Serializable {

	/**
	 * 状态
	 *
	 * @see String
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
