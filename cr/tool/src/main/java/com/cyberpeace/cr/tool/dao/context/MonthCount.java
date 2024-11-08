package com.cyberpeace.cr.tool.dao.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 月计数
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthCount implements Serializable {

	/**
	 * 月
	 */
	@ApiModelProperty("月份")
	private String month;

	/**
	 * 计数
	 */
	@ApiModelProperty("新增的数量")
	private Integer count;
}
