package com.cyberpeace.integratation.model.situation.flow;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.SeriesDataContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowTrendContext implements Serializable {

	@ApiModelProperty("时间轴")
	private List<String> time;

	@ApiModelProperty("数据")
	private List<SeriesDataContext> series;
}
