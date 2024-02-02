package com.cyberpeace.integratation.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OpsQuestionBo implements Serializable {
    private String resourceId;
    private boolean isPass;

    @ApiModelProperty("运维题唯一识别id")
    private String raceId;

    private String name;
    private String desc;
    private String direction;

    @ApiModelProperty("解题时间")
    private Date endTime;

}
