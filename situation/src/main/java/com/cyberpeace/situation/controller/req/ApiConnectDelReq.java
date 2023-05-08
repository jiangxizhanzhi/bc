package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 删除API连接请求
 *
 * @Author xuxz
 * @Date 31/10/2022 上午10:29
 */
@Data
public class ApiConnectDelReq implements Serializable {

	/**
	 * API连接ID列表
	 */
	@ApiModelProperty("资源编号")
	@NotEmpty
	private List<String> apiConnectIdList;
}
