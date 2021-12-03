package com.cyberpeace.cr.singletrain.controller.req;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("单兵训练模板")
public class SingleTrainTemplateReqRel {
    @ApiModelProperty(value = "基础数据", required = true)
    @NotNull
    private EventDataReq eventData;

    @ApiModelProperty("任务方案")
    private JSONObject missionsScheme;
}
