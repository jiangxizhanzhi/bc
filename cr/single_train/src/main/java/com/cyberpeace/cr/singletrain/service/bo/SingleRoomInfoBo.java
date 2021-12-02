package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("单兵训练房间信息")
public class SingleRoomInfoBo implements Serializable {
    @ApiModelProperty("训练开始时间")
    private Date startTime;

    @ApiModelProperty("训练结束时间")
    private Date endTime;

    @ApiModelProperty("训练记录")
    private JSONObject training_record_data;
}
