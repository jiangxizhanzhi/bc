package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("评估实时消息数据")
public class EvalRoleEventActivityListRsp implements Serializable {

    private String resourceId;

    private List<Date> creatTime;

    @ApiModelProperty("事件数量")
    private List<Integer> redCount;

    @ApiModelProperty("事件数量")
    private List<Integer> blueCount;
}
