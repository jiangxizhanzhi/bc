package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 报告更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ReportAddReq
 * @since 1.0.0
 */
@Data
public class ReportUpdateReq extends ReportAddReq{

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告资源编号")
	@NotBlank
	private String resourceId;

}
