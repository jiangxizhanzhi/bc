package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class TrainCourseReq implements Serializable {


    @ApiModelProperty("培训框架编号")
    @NotBlank(groups = {Update.class})
    private String resourceId;

    @ApiModelProperty("课程编号")
    @NotBlank(groups = {Create.class})
    private List<String> courseIdList;

}
