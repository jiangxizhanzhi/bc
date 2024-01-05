package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("用户学习统计信息")
public class PersonAllStatsBo implements Serializable {

    @ApiModelProperty("累计学习人数")
    private Integer studyCount = 0;

    @ApiModelProperty("累计完成人数")
    private Integer completeCount = 0;

    @ApiModelProperty("累计练习人数")
    private Integer practicePersonCount = 0;

    @ApiModelProperty("累计练习次数")
    private Integer practiceCount = 0;

    @ApiModelProperty("累计练习次数/人数")
    private Integer practiceRate = 0;

    @ApiModelProperty("累计模拟次数")
    private Integer examCount = 0;

    @ApiModelProperty("累计通过人数")
    private Integer passCount = 0;

    @ApiModelProperty("累计通过次数")
    private Integer passRate = 0;


}
