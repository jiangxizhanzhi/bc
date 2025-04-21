package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceActionFrameOrderReq {
    @ApiModelProperty("框架id")
    private String applicationId;

    @ApiModelProperty("红蓝对抗id")
    private String rvbId;

    @ApiModelProperty("框架名称")
    private String frameName;

    @ApiModelProperty("步骤")
    private String step;

    @ApiModelProperty("战法id")
    private String tacticId;

    @ApiModelProperty("战法类型")
    private Integer tacticType;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户名称")
    private String userName;
}
