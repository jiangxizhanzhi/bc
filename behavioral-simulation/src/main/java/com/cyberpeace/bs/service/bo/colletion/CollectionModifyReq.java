package com.cyberpeace.bs.service.bo.colletion;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为集合修改请求体
 *
 * @author SN
 * @date 2023/07/27
 */
@Data
public class CollectionModifyReq extends CollectionAddReq implements Serializable {

	@ApiModelProperty(value = "集合id")
	private String resourceId;
}
