package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author myy
 */
@Data
public class GuideTimeDistributionRsp implements Serializable {

	@ApiModelProperty("时间")
	private List<String> timeList;

	@ApiModelProperty("数量")
	private List<Integer> countList;

}
