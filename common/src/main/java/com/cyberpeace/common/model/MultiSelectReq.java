package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 多选择要求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@ApiModel
public class MultiSelectReq implements Serializable {

	@ApiModelProperty
	private List<String> ids;
}
