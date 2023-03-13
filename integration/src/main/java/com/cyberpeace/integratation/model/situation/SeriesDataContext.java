package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SeriesDataContext implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("数据")
	private List<Integer> data;

}
