package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导调时间分配响应
 *
 * @author myy@date 2024/11/12
 */
@Data
public class GuideTimeDistributionRsp implements Serializable {

	/**
	 * 时间列表
	 */
	@ApiModelProperty("时间")
	private List<String> timeList;

	/**
	 * 计数列表
	 */
	@ApiModelProperty("数量")
	private List<Integer> countList;

}
