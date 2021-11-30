package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("单兵训练模板")
public class SingleTemplateBo implements Serializable {

    @ApiModelProperty("基础信息")
    private EventDataBo eventData;

    @ApiModelProperty("任务方案")
    private JSONObject missionScheme;
}
