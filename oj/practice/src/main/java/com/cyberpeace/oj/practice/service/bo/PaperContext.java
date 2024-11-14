package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 试卷上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperContext implements Serializable {

	/**
	 * 资源编号 空为新增 非空则修改
	 */
	@ApiModelProperty("资源编号 空为新增 非空则修改")
    private String resourceId;

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
	 * 封面
	 */
	@ApiModelProperty("封面")
    private String logo;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
    private Boolean isPublic;

	/**
	 * 试卷类型
	 */
	@ApiModelProperty("试卷类型")
    private Integer type;

	/**
	 * 练习数据体(每个元素需要包含`resource_id`, `practice_type`, `is_option_random`三个属性)
	 */
	@ApiModelProperty("练习数据体(每个元素需要包含`resource_id`, `practice_type`, `is_option_random`三个属性)")
    private List<PaperPracticeContext> practiceList;

}
