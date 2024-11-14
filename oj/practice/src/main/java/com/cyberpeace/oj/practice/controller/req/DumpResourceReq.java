package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 导出资源请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class DumpResourceReq implements Serializable {

	/**
	 * 习题集编号
	 */
	@ApiModelProperty("习题集编号")
	private List<String> resourceIds;

}
