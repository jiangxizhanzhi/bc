package com.cyberpeace.cr.tool.dao.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthCount implements Serializable {

	@ApiModelProperty("月份")
	private String month;

	@ApiModelProperty("新增的数量")
	private Integer count;
}
