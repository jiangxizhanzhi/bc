package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增颜色主题请求
 *
 * @Author xuxz
 * @Date 8/12/2022 上午10:22
 */
@Data
public class ColorThemePageReq extends PageReqBase implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;
}
