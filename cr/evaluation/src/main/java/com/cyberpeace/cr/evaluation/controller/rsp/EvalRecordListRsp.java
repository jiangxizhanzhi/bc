package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 评估记录列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordListRsp` 的 Java 类，该类用于表示评估记录列表的响应数据。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **功能**: 用于表示评估记录列表响应的数据模型。 
 * - **作者**: SN 
 * - **日期**: 2024年11月8日 
 * ### 使用的库和注解 
 * - **Lombok**: 通过 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - **Swagger**: 通过 `@ApiModel` 和 `@ApiModelProperty` 注解来描述 API 模型及其属性。 
 * ### 属性 
 * - **resourceId**: 字符串类型，表示资源 ID。 
 * - **name**: 字符串类型，表示名称。 
 * - **description**: 字符串类型，表示描述。 
 * - **status**: 整数类型，表示状态。 
 * - **score**: 整数类型，表示得分。 
 * - **systemId**: 字符串类型，表示系统 ID。 
 * - **systemName**: 字符串类型，表示系统名称。 
 * - **taskId**: 字符串类型，表示任务 ID。 
 * - **message**: 字符串类型，表示消息（错误信息）。 
 * - **startTime**: `Date` 类型，表示开始时间。 
 * - **endTime**: `Date` 类型，表示结束时间。 
 * - **evalTime**: `Date` 类型，表示评估时间。 
 * ### 总结 
 * - **用途**: `EvalRecordListRsp` 类主要用于封装评估记录列表的响应数据，便于在 API 中返回结构化的数据。 
 * - **设计**: 使用了 Lombok 来简化代码，并使用 Swagger 注解来增强 API 文档的可读性。 
 * - **数据结构**: 包含多个字段，涵盖了评估记录的主要信息，如资源 ID、名称、描述、状态、得分等。 
 * 这个类的设计简洁明了，易于理解和维护，适合作为评估记录列表响应的数据模型。 
**/
@Getter
@Setter
@ApiModel("评估列表数据")
public class EvalRecordListRsp implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
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
     * 状态
     */
    @ApiModelProperty("评估状态")
    private Integer status;

    /**
     * 得分
     */
    @ApiModelProperty("评分结果")
    private Integer score;

    /**
     * 系统id
     */
    @ApiModelProperty("关联指标体系ID")
    private String systemId;

    /**
     * 系统名称
     */
    @ApiModelProperty("指标体系名称")
    private String systemName;

    /**
     * 任务id
     */
    @ApiModelProperty("关联测评任务ID")
    private String taskId;

    /**
     * 消息
     */
    @ApiModelProperty("错误信息")
    private String message;

    /**
     * 开始时间
     */
    @ApiModelProperty("评估检索探针开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("评估检索探针结束时间")
    private Date endTime;

    /**
     * 评估时间
     */
    @ApiModelProperty("评估开始时间")
    private Date evalTime;
}

