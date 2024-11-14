package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 报告更新请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ReportUpdateReq extends ReportAddReq{

	/**
	 * 实验报告资源编号
	 */
	@ApiModelProperty("实验报告资源编号")
	@NotBlank
	private String resourceId;

}
