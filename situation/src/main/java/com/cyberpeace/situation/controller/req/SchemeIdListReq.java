package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 方案id列表请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SchemeIdListReq implements Serializable {

	/**
	 * 方案id列表
	 */
	@ApiModelProperty("方案id列表")
	private List<String> schemeIds = Lists.newArrayList();

}
