package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
@AllArgsConstructor
public class LogoUploadRes implements Serializable {

    @ApiModelProperty("可访问路径")
    private String url;

}
