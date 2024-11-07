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
 */
@Data
@ApiModel
public class MultiSelectReq implements Serializable {

	/**
	 * id列表
	 */
	@ApiModelProperty
	private List<String> ids;
}
