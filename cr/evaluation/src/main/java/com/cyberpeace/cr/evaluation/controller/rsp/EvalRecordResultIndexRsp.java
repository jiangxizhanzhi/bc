package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录结果编号响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordResultIndexRsp implements Serializable {
	/**
	 * ID
	 */
	@ApiModelProperty("ID")
    private String resourceId;

	/**
	 * 指标/变量项名称
	 */
	@ApiModelProperty("指标/变量项名称")
    private String name;

	/**
	 * 评估分值
	 */
	@ApiModelProperty("评估分值")
    private Integer score;

	/**
	 * 算法ID
	 */
	@ApiModelProperty("算法ID")
    private String algorithmId;

	/**
	 * 算法名称
	 */
	@ApiModelProperty("算法名称")
    private String algorithmName;

	/**
	 * 下一级指标/变量项信息
	 */
	@ApiModelProperty("下一级指标/变量项信息")
    private List<EvalRecordResultIndexRsp> children;

	/**
	 * 水平
	 */
	@JsonIgnore
    private Integer level;

	/**
	 * 下一个id列表
	 */
	@JsonIgnore
    private String nextIds;

	/**
	 * parentid
	 */
	@JsonIgnore
    private String parentId;

	/**
	 * 算法是否为层次分析法
	 */
	@ApiModelProperty("算法是否为层次分析法")
    private Integer mode;
}
