package com.cyberpeace.gateway.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DashboardTitleRes implements Serializable {

    @ApiModelProperty("大屏标题")
    private String title;

    @ApiModelProperty("大屏副标题")
    private String subTitle;

}
