package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 方案id列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SchemeIdListReq implements Serializable {

	/**
	 * 方案id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("方案id列表")
	private List<String> schemeIds = Lists.newArrayList();

}
