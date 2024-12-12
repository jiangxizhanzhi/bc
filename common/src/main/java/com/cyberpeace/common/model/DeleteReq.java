package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class DeleteReq implements Serializable {

	/**
	 * id列表
	 */
	@ApiModelProperty("ID列表")
	private List<String> ids;
}
