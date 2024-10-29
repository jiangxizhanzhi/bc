package com.cyberpeace.cr.tool.controller.res;

import com.cyberpeace.cr.tool.dao.context.MonthCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ToolTypeCount implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("数量")
	private Integer count;

	@ApiModelProperty("变化数量")
	private List<MonthCount> countList;

}
