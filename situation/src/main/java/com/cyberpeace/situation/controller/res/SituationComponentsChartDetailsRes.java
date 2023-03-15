package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.controller.req.SituationComponentsAnimationAddReq;
import com.cyberpeace.situation.dao.entity.TwoDimensionalComponents;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class SituationComponentsChartDetailsRes extends TwoDimensionalComponents {

	@ApiModelProperty("组件类型名称")
	private String chartCategoryName;

}
