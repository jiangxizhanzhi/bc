package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 训练记录
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRecord implements Serializable {

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
