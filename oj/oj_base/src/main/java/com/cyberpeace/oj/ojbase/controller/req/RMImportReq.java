package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel("资管数据导入基础请求类")
public class RMImportReq implements Serializable {

    @ApiModelProperty("资源地址绝对路径")
    @NotBlank
    private String path;

}
