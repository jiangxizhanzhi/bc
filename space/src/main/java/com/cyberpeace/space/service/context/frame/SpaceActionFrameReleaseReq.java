package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 空间行动框架下发请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionFrameReleaseReq {

	@ApiModelProperty(value = "框架实体id", required = true)
	@NotBlank
	private String applicationId;

	@ApiModelProperty(value = "阶段", required = true)
	@NotBlank
	private String step;

	@ApiModelProperty(value = "用户id", required = true)
	@NotBlank
	private String userId;

	@ApiModelProperty(value = "用户名", required = true)
	private String userName;
}
