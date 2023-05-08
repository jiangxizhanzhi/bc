package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏模板分页返回
 *
 * @Author xuxz
 * @Date 11/11/2022 下午5:51
 */
@Data
public class DashboardTemplatePageRes extends PageReqBase implements Serializable {

	/**
	 * 模板资源id
	 */
	@ApiModelProperty("模板资源id")
	private String resourceId;

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

	/**
	 * 缩略图
	 */
	@ApiModelProperty("缩略图")
	private String thumbnail;

	/**
	 * 定制类型
	 */
	@ApiModelProperty("定制类型")
	private Integer customCode;

	/**
	 * 编辑状态
	 */
	@ApiModelProperty("编辑状态")
	private Boolean editStatus;
}
