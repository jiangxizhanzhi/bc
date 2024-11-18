package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class DeleteReq implements Serializable {

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("ID列表")
	private List<String> ids;
}
