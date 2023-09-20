package com.cyberpeace.cr.crbase.controller.req.guide;

import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExecGuideHisRep extends GuideHistoryRsp {

	@ApiModelProperty("房间id")
	private String roomId;

	@ApiModelProperty("训练类型")
	private Integer businessType;
}
