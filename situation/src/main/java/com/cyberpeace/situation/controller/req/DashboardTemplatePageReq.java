package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏模板分页请求
 *
 * @Author xuxz
 * @Date 11/11/2022 下午4:57
 */
@Data
public class DashboardTemplatePageReq extends PageReqBase implements Serializable {

	/**
	 * 模板名称
	 */
	@ApiModelProperty("模板名称")
	private String name;

	/**
	 * 模板类型
	 */
	@ApiModelProperty("模板类型")
	private String type;

	/**
	 * 模板级别
	 */
	@ApiModelProperty("模板级别")
	private Integer level;

}
