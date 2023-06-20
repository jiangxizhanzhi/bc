package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class TrainMangeReq implements Serializable {


    @ApiModelProperty("培训框架名称")
    @NotBlank(groups = {Create.class, Update.class })
    private String name;

    @ApiModelProperty("父级resourceId")
    @NotBlank(groups = {Create.class})
    private String parent;

    @ApiModelProperty("资源编号")
    @NotBlank(groups = {Update.class})
    private String resourceId;

}
