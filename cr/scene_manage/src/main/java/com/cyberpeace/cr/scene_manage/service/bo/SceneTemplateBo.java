package com.cyberpeace.cr.scene_manage.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("场景管理模板")
public class SceneTemplateBo implements Serializable {

    @ApiModelProperty("业务模板id")
    private String resourceId;

    @ApiModelProperty("基础信息")
    private EventDataBo eventData;

    @ApiModelProperty("任务方案")
    private JSONObject missionsScheme;
}
