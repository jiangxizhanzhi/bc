package com.cyberpeace.oj.practice.controller.req;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 纸新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("试卷新增/更新")
public class PaperAddReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 图标
     *
     * @see String
     */
    @ApiModelProperty("封面")
    private String logo;

    /**
     * 是否公开
     *
     * @see Boolean
     */
    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    /**
     * 执业名单
     *
     * @see List<SinglePracticeReq>
     */
    @ApiModelProperty("练习数据体(每个元素需要包含`resource_id`, `practice_type`, `is_option_random`三个属性)")
    private List<SinglePracticeReq> practiceList;

}
