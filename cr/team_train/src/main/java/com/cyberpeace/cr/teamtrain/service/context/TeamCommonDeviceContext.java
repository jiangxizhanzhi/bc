package com.cyberpeace.cr.teamtrain.service.context;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamCommonDeviceContext extends TeamCommonDevice {
    @ApiModelProperty("是否占用")
    private Integer occupyFlag;
    @ApiModelProperty("设备信息")
    private JSONObject deviceInfo;
}
