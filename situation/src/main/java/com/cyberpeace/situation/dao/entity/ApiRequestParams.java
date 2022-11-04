package com.cyberpeace.situation.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 31/10/2022 下午5:48
 */
@Data
public class ApiRequestParams implements Serializable {

    @ApiModelProperty("请求参数名")
    private String name;

    @ApiModelProperty("请求参数描述")
    private String description;

    @ApiModelProperty("请求参数value")
    private String value;

}
