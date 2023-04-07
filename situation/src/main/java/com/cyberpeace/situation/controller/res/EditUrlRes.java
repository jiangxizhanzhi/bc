package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.common.util.SystemPropUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditUrlRes implements Serializable {

	@ApiModelProperty("可视化编辑链接")
	private String url;

	public EditUrlRes(String modelId) {
		this.url = "http://" + SystemPropUtil.getProp("OUTER_HOST") + ":"
				+ SystemPropUtil.getProp("OUTER_PORT") + "/page/yw/model/edit/";
	}

}
