package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.ApiRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * API请求信息返回
 *
 * @Author xuxz
 * @Date 31/10/2022 上午10:22
 */
@Data
public class ApiRequestInfoRes extends ApiRequest implements Serializable {

	/**
	 * 请求返回参数
	 */
	@ApiModelProperty("请求返回参数")
	private String response;
}
