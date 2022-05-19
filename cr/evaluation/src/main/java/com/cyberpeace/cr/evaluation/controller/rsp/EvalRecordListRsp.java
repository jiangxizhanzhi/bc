package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EvalRecordListRsp implements Serializable {
    private String resourceId;

    private String name;

    private String description;

    private Integer status;

    private Integer score;

    private String systemId;

    private String systemName;

    private String taskId;

    private String message;

    private Date startTime;

    private Date endTime;

    private Date evalTime;
}
