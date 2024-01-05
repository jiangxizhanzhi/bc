package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexReq implements Serializable {
    @ApiModelProperty("职业名称")
    private String jobName;
    @ApiModelProperty("等级")
    private String level;
    @ApiModelProperty("职业定义")
    private String jobDefine;
    @ApiModelProperty("国家标准文件路径")
    private String standardUrl;
    @ApiModelProperty("覆盖度")
    private Integer coverage;

    private String standardInfo;
    private List<IndexCategoryReq> categoryList;
}
