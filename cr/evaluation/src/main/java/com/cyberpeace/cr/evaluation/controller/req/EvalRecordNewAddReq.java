package com.cyberpeace.cr.evaluation.controller.req;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.service.context.EvalRecordIndexContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评估记录新增功能新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordNewAddReq` 的 Java 类，该类用于处理评估记录新增功能的请求体。以下是对此代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.req` 
 * - **类名**: `EvalRecordNewAddReq` 
 * - **继承**: 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * ### 类属性 
 * - `resourceId`: 字符串类型，表示资源 ID。 
 * - `name`: 字符串类型，表示名称，标记为必填。 
 * - `description`: 字符串类型，表示描述。 
 * - `taskId`: 字符串类型，表示任务 ID，标记为必填。 
 * - `systemId`: 字符串类型，表示系统 ID，标记为必填。 
 * - `startTime`: `Date` 类型，表示开始时间，标记为必填。 
 * - `endTime`: `Date` 类型，表示结束时间，标记为必填。 
 * - `indexes`: `List<EvalRecordIndexContext>` 类型，表示指标列表，标记为必填。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个字段的用途和是否必填。 
 * ### 方法 
 * - `validate()`: 验证方法，检查 `name` 和 `description` 是否符合特定长度要求，并抛出相应的错误码 (`CrBusinessErrorCode`)。 
 * ### 主要功能 
 * - 该类主要用于接收评估记录新增请求的数据，并提供验证方法确保请求数据的有效性。 
 * ### 总结 
 * - `EvalRecordNewAddReq` 类是一个数据传输对象（DTO），用于封装评估记录新增请求所需的数据。 
 * - 它包含了多个字段，每个字段都有明确的用途和约束条件。 
 * - 提供了 `validate()` 方法以确保传入的数据符合业务规则。 
 * 这段代码设计清晰、结构合理，符合面向对象编程的基本原则。 
**/
@Getter
@Setter
public class EvalRecordNewAddReq implements Serializable {
    /**
     * 资源id
     */
    @ApiModelProperty("ID")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 任务id
     */
    @ApiModelProperty(value = "靶场测评实例ID", required = true)
    private String taskId;

    /**
     * 系统id
     */
    @ApiModelProperty(value = "使用指标体系ID", required = true)
    private String systemId;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间（评估采集数据时间范围）", required = true)
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间（评估采集数据时间范围）", required = true)
    private Date endTime;

    /**
     * 指标
     */
    @ApiModelProperty(value = "全量体系指标项", required = true)
    private List<EvalRecordIndexContext> indexes;

    public void validate() {
        if (name != null) {
            CrCommonValidator.validateNameLen(name, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
        if (StringUtils.isNotEmpty(description)) {
            CrCommonValidator.validateDescLen(description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
        }
    }
}

