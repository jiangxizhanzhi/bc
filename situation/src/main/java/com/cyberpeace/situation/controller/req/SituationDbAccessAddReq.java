package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SituationDbAccessAddReq implements Serializable {

    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    @ApiModelProperty(value = "操作时间")
    private String operationTime;

    @ApiModelProperty(value = "表名称")
    private String tableName;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "操作类型")
    private String operationDetail;

}
