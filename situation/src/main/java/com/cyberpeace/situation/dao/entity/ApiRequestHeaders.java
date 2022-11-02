package com.cyberpeace.situation.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 31/10/2022 上午11:42
 */
@Data
public class ApiRequestHeaders implements Serializable {

    @ApiModelProperty("请求头名称")
    private String name;

    @ApiModelProperty("请求头value")
    private String value;
}
