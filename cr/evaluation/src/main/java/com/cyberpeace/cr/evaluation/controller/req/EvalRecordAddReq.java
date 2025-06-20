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
 * 评估记录新增请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordAddReq` 的 Java 类，用于表示评估记录新增请求的数据结构。该类实现了 `Serializable` 接口，使其可以序列化以便在网络上传输或持久化存储。以下是对该类的简要分析： 
 * ### 主要功能 
 * - **数据传输对象**：`EvalRecordAddReq` 类是一个数据传输对象（DTO），用于在不同层之间传递数据。 
 * - **验证方法**：该类包含一个 `validate()` 方法，用于验证某些字段的合法性。 
 * ### 字段说明 
 * - `resourceId`：资源ID，字符串类型。 
 * - `name`：名称，字符串类型，必填。 
 * - `description`：描述，字符串类型。 
 * - `taskId`：任务ID，字符串类型，必填。 
 * - `systemId`：系统ID，字符串类型，必填。 
 * - `startTime`：开始时间，日期类型，必填。 
 * - `endTime`：结束时间，日期类型，必填。 
 * - `indexes`：指标列表，`EvalRecordIndexContext` 类型的列表，必填。 
 * ### 关键方法 
 * - `validate()`：用于验证名称长度和描述长度是否符合要求。如果不符合，则会抛出相应的错误码。 
 * ### 使用的外部依赖 
 * - `ApiModelProperty`：用于生成 Swagger 文档，描述 API 参数。 
 * - `Getter` 和 `Setter` 注解：用于自动创建 getter 和 setter 方法。 
 * - `CrCommonValidator`：用于验证名称和描述的合法性。 
 * - `CrBusinessErrorCode`：用于定义业务错误码。 
 * - `List`：Java 集合框架中的接口，用于存储指标列表。 
 * - `Date`：Java 中的日期类。 
 * ### 总结 
 * - **目的**：该类主要用于接收前端或其他系统的评估记录新增请求，并确保请求数据的有效性。 
 * - **特点**：该类实现了数据校验逻辑，确保了传入数据的合法性。 
 * - **应用场景**：通常在后端服务中使用，作为接收和处理评估记录新增请求的一部分。 
**/
@Getter
@Setter
public class EvalRecordAddReq implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
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
    @ApiModelProperty(value = "全量三级指标项", required = true)
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

