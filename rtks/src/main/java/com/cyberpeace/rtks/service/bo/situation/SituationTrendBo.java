package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationTrendBo implements Serializable {

	@ApiModelProperty("日期")
	private List<String> xDates = Lists.newArrayList();

	@ApiModelProperty("人-岗数")
	private List<Integer> yNums = Lists.newArrayList();
}
