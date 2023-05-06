package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.dao.entity.ApiRequestHeaders;
import com.cyberpeace.situation.dao.entity.ApiRequestParams;
import com.cyberpeace.situation.dao.entity.ApiResponseParams;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增API连接请求
 *
 * @Author xuxz
 * @Date 31/10/2022 上午10:12
 */
@Data
public class ApiConnectReq implements Serializable {

	/**
	 * 资源ID
	 */
	@NotBlank(groups = { Update.class })
	private String resourceId;

	/**
	 * api名称
	 */
	@ApiModelProperty("api名称")
	private String name;

	/**
	 * api描述
	 */
	@ApiModelProperty("api描述")
	private String description;

	/**
	 * 请求url
	 */
	@NotBlank(groups = { Create.class })
	@ApiModelProperty("url")
	private String requestUrl;

	/**
	 * 请求方法
	 */
	@NotBlank(groups = { Create.class })
	@ApiModelProperty("请求方法")
	private String requestMethod;

	/**
	 * 连接状态
	 */
	@ApiModelProperty("连接状态")
	private Boolean connectStatus;

	/**
	 * 请求参数
	 */
	@ApiModelProperty("请求参数")
	private List<ApiRequestParams> requestParams;

	/**
	 * 请求头
	 */
	@ApiModelProperty("请求头")
	private List<ApiRequestHeaders> requestHeaders;

	/**
	 * 返回值所属key值
	 */
	@ApiModelProperty("返回值所属key值")
	private String responseKey;

	/**
	 * 返回参数
	 */
	@ApiModelProperty("返回参数")
	private List<ApiResponseParams> responseParams;
}
