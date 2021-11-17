package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@ApiModel
@Data
public class ClassroomListReq {
    @ApiModelProperty("开始日期")
    @NotNull
    private Date startDate;

    @ApiModelProperty("结束日期")
    @NotNull
    private Date endDate;

    @ApiModelProperty("组织")
    String organization;

    @ApiModelProperty("创建者")
    String createUser;
}
