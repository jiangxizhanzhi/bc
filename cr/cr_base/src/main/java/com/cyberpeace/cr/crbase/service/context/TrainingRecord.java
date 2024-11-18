package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 训练记录
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRecord {

	/**
	 * 时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "时间")
	private Date time;

	/**
	 * 记录
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "记录")
	private String record;
}
