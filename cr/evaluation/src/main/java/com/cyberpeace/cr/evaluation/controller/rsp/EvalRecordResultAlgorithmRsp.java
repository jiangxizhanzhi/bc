package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录结果算法响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalRecordResultAlgorithmRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty("算法名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("算法描述")
    private String description;

    /**
     * 类型
     */
    @ApiModelProperty("算法类型")
    private Integer type;

    /**
     * 算法语言
     */
    @ApiModelProperty("算法使用语言类型")
    private Integer algorithmLanguage;

    /**
     * 代号
     */
    @ApiModelProperty("算法代码")
    private String code;

    /**
     * 参数
     */
    @ApiModelProperty("配置参数")
    private List<AlgorithmParamContext> params;
}
