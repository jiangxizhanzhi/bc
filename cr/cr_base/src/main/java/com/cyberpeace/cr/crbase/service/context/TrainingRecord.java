package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 训练记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingRecord {

	@ApiModelProperty(value = "时间")
	private Date time;

	@ApiModelProperty(value = "记录")
	private String record;
}
