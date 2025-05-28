package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 空间行动框架次序请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionFrameOrderReq {
    @ApiModelProperty("框架id")
    @NotBlank
    private String applicationId;

    @ApiModelProperty("红蓝对抗id")
    @NotBlank
    private String rvbId;

    @ApiModelProperty("框架名称")
    @NotBlank
    private String frameName;

    @ApiModelProperty("步骤")
    @NotBlank
    private String stepName;

    @ApiModelProperty("步骤id")
    @NotBlank
    private String stepId;

    @ApiModelProperty("战法id")
    @NotBlank
    private String tacticId;

    @ApiModelProperty("战法类型")
    @NotNull
    private Integer tacticType;

    @ApiModelProperty("用户id")
    @NotBlank
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("当前用户的user_id")
    @NotBlank
    private String sender;
}
