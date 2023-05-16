package com.cyberpeace.cr.crbase.service.context.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 训练统计总体情况
 */
@Data
public class TrainingRecordRsp implements Serializable {

	@ApiModelProperty(value = "全部训练")
	private List<Integer> all;

	@ApiModelProperty(value = "个人训练")
	private List<Integer> person;

	@ApiModelProperty(value = "日期")
	private List<String> date;
}
