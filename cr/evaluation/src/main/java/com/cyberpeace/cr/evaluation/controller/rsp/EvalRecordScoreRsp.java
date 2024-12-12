package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 评估记录得分响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordScoreRsp  {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * 水平
	 */
	@ApiModelProperty(value = "级别")
	private Integer level;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "3级指标项评分类型")
	private Integer type;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "当前指标获取得分")
	private Integer score;

	/**
	 * 第三级类型
	 */
	@ApiModelProperty(value = "3级指标项评分选项")
	private Integer thirdLevelType;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalRecordScoreRsp> children;

}