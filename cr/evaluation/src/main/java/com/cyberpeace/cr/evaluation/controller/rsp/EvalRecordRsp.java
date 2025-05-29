package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评估记录响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalRecordRsp` 的 Java 类，用于表示评估记录的响应信息。以下是该类的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **导入**： 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalSystemIndexesContext`: 表示指标体系上下文对象。 
 * - `com.fasterxml.jackson.annotation.JsonInclude`: 用于控制 JSON 序列化时的属性包含策略。 
 * - `io.swagger.annotations.ApiModelProperty`: 用于 Swagger 文档生成，标记响应属性。 
 * - `lombok.Getter`, `lombok.Setter`: Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - **类名**：`EvalRecordRsp` 
 * - **实现接口**：`Serializable` 
 * - **注解**： 
 * - `@Getter`, `@Setter`: 自动生成属性的 getter 和 setter 方法。 
 * - `@JsonInclude(JsonInclude.Include.NON_NULL)`: 指定只有非空值才会被序列化为 JSON。 
 * ### 属性 
 * - `resourceId` (String): 资源 ID。 
 * - `name` (String): 名称。 
 * - `description` (String): 描述。 
 * - `roomId` (String): 房间 ID。 
 * - `systemId` (String): 系统 ID。 
 * - `startTime` (Date): 开始时间。 
 * - `endTime` (Date): 结束时间。 
 * - `evalTime` (Date): 评估时间。 
 * - `runStatus` (Integer): 运行状态（1-未评估，2-计算中，3-再次启动）。 
 * - `indexes` (List<EvalSystemIndexesContext>): 指标体系结构列表。 
 * ### 总结 
 * - `EvalRecordRsp` 类用于封装评估记录的响应信息，适用于 RESTful API 返回的数据格式。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码。 
 * - 属性通过 Swagger 注解进行了文档化，便于前端开发人员理解每个字段的含义。 
 * - 该类实现了 `Serializable` 接口，意味着它可以被序列化为字节流，方便在网络上传输或持久化存储。 
**/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvalRecordRsp implements Serializable {
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
     * 房间id
     */
    @ApiModelProperty("靶场测评实例ID")
    private String roomId;

    /**
     * 系统id
     */
    @ApiModelProperty("使用指标体系ID")
    private String systemId;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间（评估采集数据时间范围）")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间（评估采集数据时间范围）")
    private Date endTime;

    /**
     * 评估时间
     */
    @ApiModelProperty("评估开始时间")
    private Date evalTime;

    /**
     * 跑状态
     */
    @ApiModelProperty("启动状态 1-未评估 2-计算中 3-再次启动")
    private Integer runStatus;

    /**
     * 指标
     */
    @ApiModelProperty("指标体系结构")
    private List<EvalSystemIndexesContext> indexes;
}

