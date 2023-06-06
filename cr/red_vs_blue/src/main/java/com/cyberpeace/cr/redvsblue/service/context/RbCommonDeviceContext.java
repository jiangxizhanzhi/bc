package com.cyberpeace.cr.redvsblue.service.context;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbCommonDeviceContext extends RbCommonDevice {
    @ApiModelProperty("是否占用:1=已占用；0=未占用")
    private Integer occupyFlag = 0;
    @ApiModelProperty("设备信息")
    private JSONObject deviceInfo;
}
