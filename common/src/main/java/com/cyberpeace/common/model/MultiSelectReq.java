package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 多查询请求体
 *
 * @author SN
 * @date 2024/11/07
 * @version 1.0.0
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class MultiSelectReq implements Serializable {

	/**
	 * id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty
	private List<String> ids;
}
