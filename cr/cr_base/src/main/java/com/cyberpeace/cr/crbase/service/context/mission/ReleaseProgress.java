package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Data
public class ReleaseProgress implements Serializable {

	/**
	 * 总数
	 */
	@ApiModelProperty(value = "总数")
	private Integer total = 0;

	/**
	 * 已下发
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
