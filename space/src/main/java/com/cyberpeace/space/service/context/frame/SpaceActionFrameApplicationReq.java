package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间行动框架行为依赖请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionFrameApplicationReq {

	@ApiModelProperty(value = "红蓝对抗业务id")
	private String rvbId;

	@ApiModelProperty(value = "框架id")
	private String frameId;

	@ApiModelProperty(value = "红蓝对抗业务名称")
	private String rvbName;
}
