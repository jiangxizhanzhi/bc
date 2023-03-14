package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SituationComponentsChartDetailsRes implements Serializable {

	@ApiModelProperty()
	private String resourceId;

	@ApiModelProperty("组件名称")
	private String chartName;

	@ApiModelProperty("组件类型名称")
	private String chartCategoryName;

}
