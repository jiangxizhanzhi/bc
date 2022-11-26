package com.cyberpeace.situation.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author xuxz
 * @Date 26/11/2022 下午5:09
 */
@Data
public class DynamicEffectMappingPageReq extends PageReqBase implements Serializable {

    @ApiModelProperty(value = "动效名称")
    private String name;

    @ApiModelProperty(value = "动效类型")
    private String category;

    @ApiModelProperty(value = "源节点动效名称")
    private String sourceNodeDynamicName;

    @ApiModelProperty(value = "目标节点动效名称")
    private String targetNodeDynamicName;

    @ApiModelProperty("聚焦")
    private String focus;

    @ApiModelProperty("颜色")
    private String color;

    @ApiModelProperty("线形")
    private String line;

    @ApiModelProperty("线形大小")
    private String lineSize;
}
