package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 纸张上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号 空为新增 非空则修改")
    private String resourceId;

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
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("试卷类型")
    private Integer type;

    /**
     * 执业名单
     *
     * @see List<PaperPracticeContext>
     */
    @ApiModelProperty("练习数据体(每个元素需要包含`resource_id`, `practice_type`, `is_option_random`三个属性)")
    private List<PaperPracticeContext> practiceList;

}
