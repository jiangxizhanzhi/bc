package com.cyberpeace.auth.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author :   SN
 * @date :   2023/12/12 17:48
 * @desc :
 **/
@Data
@ApiModel
public class UserEnableReq {

    @ApiModelProperty(value = "用户id列表")
    private List<String> ids;

    @ApiModelProperty(value = "是否启用,0=禁用，1=可用")
    private Integer enable;
}
