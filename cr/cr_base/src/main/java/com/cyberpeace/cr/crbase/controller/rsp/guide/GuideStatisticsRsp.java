package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author myy
 */
@Data
public class GuideStatisticsRsp implements Serializable {


	@ApiModelProperty("导调名称")
	private String guideName;

	@ApiModelProperty("导调类型 类型未定")
	private Integer guideType;

	@ApiModelProperty("导调类型占比")
	private double percent;

	@ApiModelProperty("导调类型数量")
	private Integer count;

}
