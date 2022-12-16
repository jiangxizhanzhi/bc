package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 15/12/2022 上午11:18
 */
@Data
public class SituationIconPageReq extends PageReqBase implements Serializable {

    @ApiModelProperty("图标名称")
    private String name;

    @ApiModelProperty("类型")
    private String type;
}
