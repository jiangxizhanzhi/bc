package com.cyberpeace.cr.tool.controller.res;

import com.cyberpeace.cr.tool.dao.context.MonthCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 工具类型计数
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ToolTypeCount implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 计数
	 */
	@ApiModelProperty("数量")
	private Integer count;

	/**
	 * 计数列表
	 */
	@ApiModelProperty("变化数量")
	private List<MonthCount> countList;

}
