package com.cyberpeace.cr.evaluation.dao.entity;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AlarmPageReq extends PageReqBase {

    private String templateId;

    private String sceneId;

    private String nodeName;

    @ApiModelProperty("用来隔离数据")
    private Integer quarantine;
}
