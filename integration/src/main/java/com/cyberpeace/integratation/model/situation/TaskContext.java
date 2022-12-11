package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskContext implements Serializable {

    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务状态")
    private String taskStatus;

    @ApiModelProperty(value = "任务类型")
    private String taskType;

    @ApiModelProperty(value = "任务模式")
    private String taskMode;

    @ApiModelProperty(value = "责任靶场名称")
    private String responsibleRangeName;

    @ApiModelProperty(value = "任务开始时间")
    private String time;

    @ApiModelProperty(value = "科目列表")
    private List<String> subjectList;
}
