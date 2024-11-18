package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务下发进度
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/15
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ReleaseProgress implements Serializable {

	/**
	 * 总
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总数")
	private Integer total = 0;

	/**
	 * 释放
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "已下发数")
	private Integer released = 0;

	public void addTotal(Integer num) {
		this.total += num;
	}

	public void addReleased(Integer num) {
		this.released += num;
	}
}
