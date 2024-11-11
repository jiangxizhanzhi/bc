package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试卷新增请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("试卷新增/更新")
public class PaperAddReq implements Serializable {

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 图标
     */
    @ApiModelProperty("封面")
    private String logo;

    /**
     * 是否公开
     */
    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    /**
     * 练习名单
     */
    @ApiModelProperty("练习数据体(每个元素需要包含`resource_id`, `practice_type`, `is_option_random`三个属性)")
    private List<SinglePracticeReq> practiceList;

}
