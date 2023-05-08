package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.common.util.SystemPropUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 可视化编辑链接返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EditUrlRes implements Serializable {

	/**
	 * url
	 */
	@ApiModelProperty("可视化编辑链接")
	private String url;

	/**
	 * 构造函数
	 *
	 * @param modelId 模型id
	 */
	public EditUrlRes(String modelId) {
		this.url = "http://" + SystemPropUtil.getProp("OUTER_HOST") + ":"
				+ SystemPropUtil.getProp("OUTER_PORT") + "/page/yw/model/edit/";
	}

}
