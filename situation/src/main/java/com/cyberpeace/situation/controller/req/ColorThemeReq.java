package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增颜色主题请求
 *
 * @Author xuxz
 * @Date 8/12/2022 上午9:15
 */
@Data
public class ColorThemeReq implements Serializable {

	/**
	 * 资源id
	 */
	@NotBlank(groups = { Update.class })
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@NotBlank(groups = { Create.class, Update.class })
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 颜色内容
	 */
	@ApiModelProperty("颜色内容")
	private String option;

}
