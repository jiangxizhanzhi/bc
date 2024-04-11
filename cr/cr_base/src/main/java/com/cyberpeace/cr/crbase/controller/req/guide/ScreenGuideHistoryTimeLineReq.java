package com.cyberpeace.cr.crbase.controller.req.guide;

import com.cyberpeace.cr.crbase.service.context.guide.ScreenBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class ScreenGuideHistoryTimeLineReq implements Serializable {

	@ApiModelProperty(value = "训练id", required = true)
	@NotBlank
	private String roomId;

	@ApiModelProperty(value = "训练类型(3：团队，4：红蓝，5:试验任务)", required = true)
	@NotNull
	private Integer businessType;

	@ApiModelProperty("事件类型")
	private List<Integer> eventSubTypeList;

	@ApiModelProperty("攻击筛选条件")
	private ScreenBo attackScreenBo;

	@ApiModelProperty("防御筛选条件")
	private ScreenBo defenseScreenBo;

	@ApiModelProperty("阻断筛选条件")
	private ScreenBo blockingScreenBo;

	@ApiModelProperty("归属筛选条件")
	private ScreenBo belongingScreenBo;
}
