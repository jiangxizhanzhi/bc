package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CrTitleTrainRecordBo {
    @ApiModelProperty("部门")
    private String org;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("训练名称")
    private String trainName;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("提交时间")
    private String endTime;

    @ApiModelProperty("用户得分")
    private String userScore;

    @ApiModelProperty("训练总分")
    private String taskScore;

}
