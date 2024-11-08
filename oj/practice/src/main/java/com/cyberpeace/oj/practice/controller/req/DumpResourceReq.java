package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 转 储资源请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class DumpResourceReq implements Serializable {

	/**
	 * 资源id列表
	 */
	@ApiModelProperty("习题集编号")
	private List<String> resourceIds;

}
