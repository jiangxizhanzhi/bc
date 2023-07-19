package com.cyberpeace.gateway.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateTopoInfoReq implements Serializable {

    @JsonProperty("subRangeId")
    @ApiModelProperty(value = "分靶场ID")
    private String subRangeId;

    @JsonProperty("taskId")
    @ApiModelProperty(value = "任务ID")
    private String taskId;

    @JsonProperty("subjectId")
    @ApiModelProperty(value = "科目ID")
    private String subjectId;

    @JsonProperty("envId")
    @ApiModelProperty(value = "可视化方案ID")
    private String envId;

    @JsonProperty("filePath")
    @ApiModelProperty(value = "拓扑文件路径")
    private String filePath;
}
