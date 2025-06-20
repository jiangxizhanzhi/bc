package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务下发进度
 *
 * @author SN
 * @date 2023/05/15
 */
@Getter
@Setter
public class ReleaseProgress implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 总
	 */
	@ApiModelProperty(value = "总数")
	private Integer total = 0;

	/**
	 * 释放
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
