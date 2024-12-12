package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 训练记录
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRecord {

	/**
	 * 时间
	 */
	@ApiModelProperty(value = "时间")
	private Date time;

	/**
	 * 记录
	 */
	@ApiModelProperty(value = "记录")
	private String record;
}
