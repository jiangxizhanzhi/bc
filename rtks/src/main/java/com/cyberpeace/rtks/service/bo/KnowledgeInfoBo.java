package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("知识点信息基本")
public class KnowledgeInfoBo implements Serializable {

    @ApiModelProperty("知识点编号")
    private String resourceId;

    @ApiModelProperty("知识点分类编号")
    private String categoryId;

    @ApiModelProperty("知识点内容")
    private String content;

}
