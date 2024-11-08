package com.cyberpeace.cr.tool.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class DeleteReq implements Serializable {

	/**
	 * id列表
	 */
	@ApiModelProperty("删除ID列表")
	private List<String> ids;
}
