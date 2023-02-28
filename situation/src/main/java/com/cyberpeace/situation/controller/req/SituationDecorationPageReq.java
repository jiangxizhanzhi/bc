package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 4/1/2023 上午11:47
 */

@Data
public class SituationDecorationPageReq extends PageReqBase implements Serializable {

    @ApiModelProperty("背景名称")
    private String name;

    @ApiModelProperty("类型")
    private String type;
}
