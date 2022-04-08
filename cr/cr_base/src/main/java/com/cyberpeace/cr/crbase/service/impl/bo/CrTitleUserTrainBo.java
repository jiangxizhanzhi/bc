package com.cyberpeace.cr.crbase.service.impl.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CrTitleUserTrainBo implements Serializable {
    @ApiModelProperty("训练模板ID")
    private String businessTemplateId;

    @ApiModelProperty("称号ID")
    private String titleId;

    @ApiModelProperty("训练及格分")
    private Integer score;

    @ApiModelProperty("用户得分")
    private Integer userScore;

    @ApiModelProperty("训练是否通过")
    private Boolean isFinish;

}
