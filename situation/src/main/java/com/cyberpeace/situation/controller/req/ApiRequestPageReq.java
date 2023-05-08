package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * API请求分页查询请求
 *
 * @Author xuxz
 * @Date 1/11/2022 下午4:57
 */
@Data
public class ApiRequestPageReq extends PageReqBase implements Serializable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
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
	 * url
	 */
	@ApiModelProperty("url")
	private String requestUrl;

	/**
	 * 请求方法
	 */
	@ApiModelProperty("请求方法")
	private String requestMethod;

	/**
	 * 创建用户
	 */
	private String creatUserId;
}
