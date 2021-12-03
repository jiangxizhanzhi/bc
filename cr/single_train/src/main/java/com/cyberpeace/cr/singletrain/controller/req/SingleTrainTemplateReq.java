package com.cyberpeace.cr.singletrain.controller.req;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练模板")
public class SingleTrainTemplateReq extends BasicTemplate {

    @ApiModelProperty("任务方案")
    private JSONObject missionsScheme;


}
