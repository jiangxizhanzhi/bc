package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练记录
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TrainingRecord {

	@ApiModelProperty(value = "时间")
	private Date time;

	@ApiModelProperty(value = "记录")
	private String record;
}
