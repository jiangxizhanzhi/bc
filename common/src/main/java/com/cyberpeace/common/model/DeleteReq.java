package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@ApiModel
public class DeleteReq implements Serializable {

	@ApiModelProperty("ID列表")
	private List<String> ids;
}
