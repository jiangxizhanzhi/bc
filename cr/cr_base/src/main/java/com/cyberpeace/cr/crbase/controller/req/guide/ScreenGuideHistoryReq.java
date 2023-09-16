package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
public class ScreenGuideHistoryReq implements Serializable {

	@ApiModelProperty("训练id")
	@NotBlank
	private String roomId;

	@ApiModelProperty("事件类型")
	private List<Integer> eventSubTypeList;

	@ApiModelProperty("攻击筛选条件")
	private Screen attackScreen;

	@ApiModelProperty("防御筛选条件")
	private Screen defenseScreen;

	@ApiModelProperty("阻断筛选条件")
	private Screen blockingScreen;

	@ApiModelProperty("归属筛选条件")
	private Screen belongingScreen;

}
