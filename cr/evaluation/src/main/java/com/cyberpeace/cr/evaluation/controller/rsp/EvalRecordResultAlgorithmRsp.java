package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 评估记录结果算法响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EvalRecordResultAlgorithmRsp implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("算法名称")
    private String name;

    /**
     * 描述
     *
     * @see String
     */
    @ApiModelProperty("算法描述")
    private String description;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("算法类型")
    private Integer type;

    /**
     * 算法语言
     *
     * @see Integer
     */
    @ApiModelProperty("算法使用语言类型")
    private Integer algorithmLanguage;

    /**
     * 代号
     *
     * @see String
     */
    @ApiModelProperty("算法代码")
    private String code;

    /**
     * 参数
     *
     * @see List<AlgorithmParamContext>
     */
    @ApiModelProperty("配置参数")
    private List<AlgorithmParamContext> params;
}
