package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录结果算法响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordResultAlgorithmRsp {
	/**
	 * ID
	 */
	@ApiModelProperty("ID")
    private String resourceId;

	/**
	 * 算法名称
	 */
	@ApiModelProperty("算法名称")
    private String name;

	/**
	 * 算法描述
	 */
	@ApiModelProperty("算法描述")
    private String description;

	/**
	 * 算法类型
	 */
	@ApiModelProperty("算法类型")
    private Integer type;

	/**
	 * 算法使用语言类型
	 */
	@ApiModelProperty("算法使用语言类型")
    private Integer algorithmLanguage;

	/**
	 * 算法代码
	 */
	@ApiModelProperty("算法代码")
    private String code;

	/**
	 * 配置参数
	 */
	@ApiModelProperty("配置参数")
    private List<AlgorithmParamContext> params;
}
