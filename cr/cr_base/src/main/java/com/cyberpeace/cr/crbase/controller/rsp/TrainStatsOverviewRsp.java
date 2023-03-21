package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TrainStatsOverviewRsp implements Serializable {
    @ApiModelProperty("训练实例id")
    private String roomId;

    @ApiModelProperty("训练模板id")
    private String businessTemplateId;

    @ApiModelProperty("训练名称")
    private String name;

    @ApiModelProperty("训练业务类型")
    private String businessType;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("时长")
    private Integer duration;

    @ApiModelProperty("开始事件")
    private Date startTime;

    @ApiModelProperty("参与用户数量")
    private Integer userCount;

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("场景实例id")
    private String sceneId;

}
