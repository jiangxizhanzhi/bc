package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 删除资源请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class DeleteResourceReq implements Serializable {

	/**
	 * 资源数据编号
	 */
	@ApiModelProperty("资源数据编号")
	private List<String> resourceIds;

}
