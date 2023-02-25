package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 24/2/2023 上午9:26
 */

@Data
@ApiModel("海康摄像头请求体")
public class HikVisionReq implements Serializable {

    @ApiModelProperty("设备IP")
    private String deviceIp;

    @ApiModelProperty("设备端口")
    private String devicePort;

    @ApiModelProperty("设备用户名")
    private String username;

    @ApiModelProperty("设备密码")
    private String password;

    @ApiModelProperty("录像起始时间")
    private String startTime;

    @ApiModelProperty("录像结束时间")
    private String endTime;
}
