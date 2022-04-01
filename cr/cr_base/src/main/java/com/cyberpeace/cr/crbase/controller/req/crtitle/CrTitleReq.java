package com.cyberpeace.cr.crbase.controller.req.crtitle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CrTitleReq implements Serializable {

    @ApiModelProperty("资源ID标识")
    private String resourceId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("及格标准")
    private Integer score;

    @ApiModelProperty("关联训练ids")
    private List<String> businessTemplateIds;

}
