package com.cyberpeace.cr.tool.dao.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ToolListQuery implements Serializable {

    private String pattern;

    @ApiModelProperty("分类")
    private String category;

    @ApiModelProperty("平台")
    private String platforms;

    @ApiModelProperty("授权")
    private Boolean cracked;

}
