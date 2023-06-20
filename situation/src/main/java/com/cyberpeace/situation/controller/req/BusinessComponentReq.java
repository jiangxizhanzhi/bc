package com.cyberpeace.situation.controller.req;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 业务组件请求
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
public class BusinessComponentReq {

	/**
	 * 业务组件ID
	 */
	@ApiModelProperty("业务组件ID")
	@NotBlank(groups = Update.class, message = ErrorCode.METHOD_ARG_NOT_VALID)
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	@NotBlank(groups = { Update.class, Create.class })
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("类型")
	@NotBlank(groups = { Update.class, Create.class })
	private String type;

	/**
	 * 缩略图
	 */
	@ApiModelProperty("缩略图")
	@NotBlank(groups = { Update.class, Create.class })
	private String thumbnail;

	/**
	 * 配置信息
	 */
	@ApiModelProperty("配置")
	@NotBlank(groups = { Update.class, Create.class })
	private String config;

	/**
	 * 图层信息
	 */
	@ApiModelProperty("图层信息")
	private List<LayerShowReq> layerShow;

	/**
	 * 组件配置信息
	 */
	@ApiModelProperty("组件配置信息")
	private List<BusinessComponentsSettingReq> businessComponentsSettings;

}
