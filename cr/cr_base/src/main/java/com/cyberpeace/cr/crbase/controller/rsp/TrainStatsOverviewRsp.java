package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrainStatsOverviewRsp implements Serializable {
    @ApiModelProperty("训练实例id")
    private String roomId;

    @ApiModelProperty("训练名称")
    private String name;

    @ApiModelProperty("训练业务类型")
    private Integer businessType;

    @ApiModelProperty("训练业务类型名称")
    private String businessTypeName;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("时长")
    private Integer durationTime;

    @ApiModelProperty("开始事件")
    private Date startTime;

    @ApiModelProperty("参与用户数量")
    private Integer userCount;

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("用户角色")
    private Integer roleGroup;

    @ApiModelProperty("分钟数")
    private Integer minute;

}
