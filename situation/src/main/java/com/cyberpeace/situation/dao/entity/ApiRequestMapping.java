package com.cyberpeace.situation.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 24/11/2022 上午11:39
 */

@Data
public class ApiRequestMapping implements Serializable {

    @ApiModelProperty("映射参数名")
    private String name;

    @ApiModelProperty("映射参数类型")
    private String type;

    @ApiModelProperty("映射参数值")
    private String value;

}
