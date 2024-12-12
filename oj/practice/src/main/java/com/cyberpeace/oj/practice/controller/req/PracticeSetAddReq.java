package com.cyberpeace.oj.practice.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 实践设置新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class PracticeSetAddReq implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotBlank(groups = { Create.class })
	private String name;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "封面地址")
	private String logo;

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型 0=理论基础 2=实践操作", allowableValues = "0,2", required = true)
	@NotNull(groups = { Create.class })
	private Integer practiceType;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "公开", required = true)
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

}
