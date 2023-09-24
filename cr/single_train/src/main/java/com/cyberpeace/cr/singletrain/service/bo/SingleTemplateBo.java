package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练模板")
public class SingleTemplateBo implements Serializable {

    @ApiModelProperty("业务模板id")
    private String resourceId;

    @ApiModelProperty("基础信息")
    private EventDataBo eventData;

    @ApiModelProperty("任务方案")
    private JSONObject missionsScheme;

    @ApiModelProperty("拓扑id")
    private String copiedTopoId;

}
