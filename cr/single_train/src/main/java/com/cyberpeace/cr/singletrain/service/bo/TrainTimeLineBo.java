package com.cyberpeace.cr.singletrain.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("训练时间线")
public class TrainTimeLineBo {
    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("预计结束时间")
    private Date endTime;

    @ApiModelProperty("训练任务记录")
    private List<JSONObject> data;

    @ApiModelProperty("当前时间")
    private Date currentTime;

}
