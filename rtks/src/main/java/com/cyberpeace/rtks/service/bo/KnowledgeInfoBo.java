package com.cyberpeace.rtks.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识信息 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("知识点信息基本")
public class KnowledgeInfoBo implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty("知识点编号")
    private String resourceId;

    /**
     * 类别id
     */
    @ApiModelProperty("知识点分类编号")
    private String categoryId;

    /**
     * 内容
     */
    @ApiModelProperty("知识点内容")
    private String content;

}
