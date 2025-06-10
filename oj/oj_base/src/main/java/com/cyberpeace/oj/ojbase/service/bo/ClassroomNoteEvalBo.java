package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ClassroomNoteEvalBo implements Serializable {

    @ApiModelProperty("得分")
    private Integer score;

    @ApiModelProperty("是否通过")
    private Boolean passed;

    @ApiModelProperty("实验报告id")
    private String nodeId;

    @ApiModelProperty("是否提交")
    private Boolean isSubmit;

    @ApiModelProperty("业务id")
    private String businessId;

    @ApiModelProperty("用户id")
    private String userId;
}
