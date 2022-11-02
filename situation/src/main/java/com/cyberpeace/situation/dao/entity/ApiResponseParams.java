package com.cyberpeace.situation.dao.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 1/11/2022 下午3:32
 */
@Data
public class ApiResponseParams implements Serializable {

    @ApiModelProperty("返回参数名")
    private String name;

    @ApiModelProperty("返回参数类型")
    private String type;

    @ApiModelProperty("返回参数描述")
    private Object description;
}
