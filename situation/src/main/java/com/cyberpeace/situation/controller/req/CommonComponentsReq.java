package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增组件请求
 *
 * @Author xuxz
 * @Date 29/12/2022 上午10:03
 */
@Data
public class CommonComponentsReq implements Serializable {

	/**
	 * 资源id
	 */
	@NotBlank(groups = Update.class)
	private String resourceId;

	/**
	 * 组件名称
	 */
	@NotBlank(groups = Create.class)
	@ApiModelProperty("组件名称")
	private String name;

	/**
	 * 组件类型
	 */
	@NotNull(groups = Create.class)
	@ApiModelProperty("组件类型")
	private Integer type;

	/**
	 * 封面图
	 */
	@NotBlank(groups = Create.class)
	@ApiModelProperty("封面图")
	private String img;

	/**
	 * 实际资源url
	 */
	@NotBlank(groups = Create.class)
	@ApiModelProperty("实际资源")
	private String url;
}
